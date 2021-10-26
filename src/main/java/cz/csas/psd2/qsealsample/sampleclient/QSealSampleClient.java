package cz.csas.psd2.qsealsample.sampleclient;

import com.google.gson.Gson;
import cz.csas.psd2.qsealsample.constant.QSealSampleConstants;
import cz.csas.psd2.qsealsample.exception.UnSupportedSettingsException;
import cz.csas.psd2.qsealsample.model.DigestAlgorithm;
import cz.csas.psd2.qsealsample.report.ClientLog;
import cz.csas.psd2.qsealsample.report.ClientLogRequest;
import cz.csas.psd2.qsealsample.report.ClientLogResponse;
import cz.csas.psd2.qsealsample.utils.ConfigurationProperties;
import cz.csas.psd2.qsealsample.utils.KeyStoreUtils;
import cz.csas.psd2.qsealsample.utils.QSealSampleUtils;
import cz.csas.psd2.qsealsample.utils.QSealSignerUtils;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContexts;

public class QSealSampleClient<T> {

    private final Gson gson = new Gson();
    private final Map<String, String> headers = new TreeMap<>();
    private final Set<String> headersToSign;

    public QSealSampleClient(String webApiKey, String authorization) {
        this.headersToSign = setHeadersToSign();
        addHeader(QSealSampleConstants.WEB_API_KEY_HEADER_NAME, webApiKey);
        addHeader(QSealSampleConstants.AUTHORIZATION_HEADER_NAME, authorization);
        addHeader(QSealSampleConstants.CONTENT_TYPE_HEADER_NAME, ContentType.APPLICATION_JSON.getMimeType());
    }

    public ClientLog doSampleRequest(String URI, T data) {
        ClientLogResponse logResponse;
        ClientLogRequest logRequest;
        ClientLog requestResponse = null;

        HttpClientBuilder clientBuilder = get2WaySslHttpClient();

        if (Boolean.parseBoolean(ConfigurationProperties.getProperty(QSealSampleConstants.PROXY_ENABLED))) {
            addProxy(clientBuilder);
        }

        try (CloseableHttpClient client = clientBuilder.build()) {
            signMessage(data);
            HttpPost postRequest = new HttpPost(URI);
            HttpEntity stringEntity = new StringEntity(gson.toJson(data));
            postRequest.setEntity(stringEntity);

            for (String headerKey : headers.keySet()) {
                postRequest.setHeader(headerKey, headers.get(headerKey));
            }

            logRequest = new ClientLogRequest(URI, gson.toJson(data), QSealSampleUtils.transformHeaders(postRequest.getAllHeaders()));
            CloseableHttpResponse response = client.execute(postRequest);
            String responseBody = QSealSampleUtils.transformResponseBody(response.getEntity().getContent());
            Map<String, String> responseHeaders = QSealSampleUtils.transformHeaders(response.getAllHeaders());
            logResponse = new ClientLogResponse(response.getStatusLine().getStatusCode(), responseBody, responseHeaders);
            requestResponse = new ClientLog(logRequest, logResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestResponse;
    }

    private void signMessage(T data) throws NoSuchAlgorithmException, KeyStoreException, CertificateEncodingException, UnrecoverableKeyException, InvalidKeyException, SignatureException, UnSupportedSettingsException {
        QSealSignerUtils signer = new QSealSignerUtils();

        String digest = signer.createDigest(gson.toJson(data), DigestAlgorithm.SHA256);
        addHeader(QSealSampleConstants.DIGEST_HEADER_NAME, digest);

        String signature = signer.createSignature(selectHeadersToSign());
        addHeader(QSealSampleConstants.SIGNATURE_HEADER_NAME, signature);

        String tppSignatureCertificate = signer.getTppSignatureCertificate();
        addHeader(QSealSampleConstants.CERTIFICATE_HEADER_NAME, tppSignatureCertificate);
    }

    private void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    private Map<String, String> selectHeadersToSign() throws UnSupportedSettingsException {
        Map<String, String> headersToSign = new TreeMap<>();
        for (String headerToSign : this.headersToSign) {
            if (this.headers.get(headerToSign) == null) {
                throw new UnSupportedSettingsException(QSealSampleConstants.MISSING_HEADER_FOR_SIGN + headerToSign);
            }
            headersToSign.put(headerToSign, this.headers.get(headerToSign));
        }
        return headersToSign;
    }

    private Set<String> setHeadersToSign() {
        Set<String> headersToSign = new HashSet<>(10);
        headersToSign.add(QSealSampleConstants.DIGEST_HEADER_NAME);
        headersToSign.add(QSealSampleConstants.WEB_API_KEY_HEADER_NAME);
        headersToSign.add(QSealSampleConstants.AUTHORIZATION_HEADER_NAME);
        return headersToSign;
    }

    private void addProxy(HttpClientBuilder clientBuilder) {
        HttpHost proxyHost = new HttpHost(ConfigurationProperties.getProperty(
                QSealSampleConstants.PROXY_HOST_PROP_KEY),
                Integer.parseInt(ConfigurationProperties.getProperty(QSealSampleConstants.PROXY_PORT_PROP_KEY)
                ));
        HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);
        clientBuilder.setRoutePlanner(routePlanner);
    }

    private HttpClientBuilder get2WaySslHttpClient() {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        KeyStore qwacCert = KeyStoreUtils.getKeyStore(ConfigurationProperties.getProperty(QSealSampleConstants.QWAC_JKS_PATH_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.JKS_PASSWORD_PROP_KEY));
        KeyStore truststore = KeyStoreUtils.getKeyStore(ConfigurationProperties.getProperty(QSealSampleConstants.TRUSTSTORE_JKS_PATH_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.JKS_PASSWORD_PROP_KEY));

        try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(qwacCert, ConfigurationProperties.getProperty(QSealSampleConstants.QWAC_PASSWORD_PROP_KEY).toCharArray(), (aliases, socket) -> ConfigurationProperties.getProperty(QSealSampleConstants.QWAC_JKS_ALIAS_PROP_KEY))
                    .loadTrustMaterial(truststore, null)
                    .build();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1.2", "TLSv1.1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            RequestConfig.Builder requestBuilder = RequestConfig.custom();
            requestBuilder = requestBuilder.setConnectionRequestTimeout(10000);
            requestBuilder = requestBuilder.setConnectTimeout(1000);
            requestBuilder = requestBuilder.setSocketTimeout(30000);

            clientBuilder.setSSLSocketFactory(socketFactory);
            clientBuilder.setDefaultRequestConfig(requestBuilder.build());

            return clientBuilder;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Problem creating sslContext", e);
        }
    }
}
