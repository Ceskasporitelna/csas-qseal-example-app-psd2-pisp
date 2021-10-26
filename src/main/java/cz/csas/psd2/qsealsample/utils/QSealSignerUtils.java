package cz.csas.psd2.qsealsample.utils;

import org.apache.commons.lang3.StringUtils;
import cz.csas.psd2.qsealsample.constant.QSealSampleConstants;
import cz.csas.psd2.qsealsample.model.DigestAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class QSealSignerUtils {

    /**
     *  Create Digest header
     */
    public String createDigest(String sign, DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest
                .getInstance(digestAlgorithm.getDigestAlgorithm())
                .digest(sign.getBytes());

        String base64 = Base64.getEncoder().encodeToString(hash);
        return digestAlgorithm.getDigestAlgorithm() + "=" + base64;
    }

    /**
     * Get base64 encoded QSeal public certificate
     */
    public String getTppSignatureCertificate() throws CertificateEncodingException, KeyStoreException {
        final X509Certificate x509Certificate = (X509Certificate) KeyStoreUtils.getKeyStore(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_PATH_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.JKS_PASSWORD_PROP_KEY)).getCertificate(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_ALIAS_PROP_KEY));
        return Base64.getEncoder().encodeToString(x509Certificate.getEncoded());
    }

    /**
     *  Create Signature header
     */
    public String createSignature(Map<String, String> headers) throws KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        String keyId = createKeyId();
        String algorithm = getSigningAlgorithm(); // "SHA256withRSA";
        String headersPart = StringUtils.join(headers.keySet(), " ");
        String signingString = headers.keySet().stream().map((String header) -> createHeaderInLine(header, headers)).collect(Collectors.joining("\n"));
        byte[] bytes = signingString.getBytes(StandardCharsets.UTF_8);
        PrivateKey privateKey = (PrivateKey) KeyStoreUtils.getKeyStore(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_PATH_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.JKS_PASSWORD_PROP_KEY)).getKey(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_ALIAS_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_PASSWORD_PROP_KEY).toCharArray());
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(bytes);
        byte[] signatureBytes = signature.sign();
        String signatureOutput = Base64.getEncoder().encodeToString(signatureBytes);

        return MessageFormat.format(
                "keyId=\"{0}\",algorithm=\"{1}\",headers=\"{2}\",signature=\"{3}\"",
                keyId,
                algorithm,
                headersPart,
                signatureOutput);
    }

    public String createKeyId() throws KeyStoreException {
        String serialNumber = getPublicCertificate().getSerialNumber().toString(16);
        String issuerDn = getPublicCertificate().getIssuerDN().toString();

        return MessageFormat.format("SN={0},CA={1}", serialNumber, issuerDn);
    }

    public String getSigningAlgorithm() throws KeyStoreException {
        return getPublicCertificate().getSigAlgName();
    }

    private X509Certificate getPublicCertificate() throws KeyStoreException {
        return (X509Certificate) KeyStoreUtils.getKeyStore(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_PATH_PROP_KEY), ConfigurationProperties.getProperty(QSealSampleConstants.JKS_PASSWORD_PROP_KEY)).getCertificate(ConfigurationProperties.getProperty(QSealSampleConstants.QSEAL_JKS_ALIAS_PROP_KEY));
    }

    private String createHeaderInLine(String headerName, Map<String, String> headers) {
        return headerName.toLowerCase(Locale.US) + ": " + headers.get(headerName);
    }
}
