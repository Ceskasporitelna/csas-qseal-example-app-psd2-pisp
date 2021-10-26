package cz.csas.psd2.qsealsample.constant;

public class QSealSampleConstants {

    /* Identification section */
    public static final String WEB_API_KEY_HEADER_NAME = "web-api-key";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String CONTENT_TYPE_HEADER_NAME = "content-type";

    /* QSEAL Section */
    public static final String DIGEST_HEADER_NAME = "Digest";
    public static final String SIGNATURE_HEADER_NAME = "Signature";
    public static final String CERTIFICATE_HEADER_NAME = "TPP-Signature-Certificate";

    /* JKS Section */
    public static final String JKS_INSTANCE_TYPE = "JKS";

    /* ERROR Messages */
    public static final String MISSING_HEADER_FOR_SIGN = "The following header could not be used to sign, because it has not been set. Please set this header: ";

    /* Property keys */
    public static final String PROXY_ENABLED = "proxy.enabled";
    public static final String PROXY_HOST_PROP_KEY = "proxy.host";
    public static final String PROXY_PORT_PROP_KEY = "proxy.port";
    public static final String QSEAL_JKS_ALIAS_PROP_KEY = "qseal.alias";
    public static final String QWAC_JKS_ALIAS_PROP_KEY = "qwac.alias";
    public static final String JKS_PASSWORD_PROP_KEY = "keystore.password";
    public static final String QWAC_PASSWORD_PROP_KEY = "qwac.password";
    public static final String QSEAL_PASSWORD_PROP_KEY = "qseal.password";
    public static final String QWAC_JKS_PATH_PROP_KEY = "qwac.keystore.location";
    public static final String QSEAL_JKS_PATH_PROP_KEY = "qseal.keystore.location";
    public static final String TRUSTSTORE_JKS_PATH_PROP_KEY = "truststore.location";
}
