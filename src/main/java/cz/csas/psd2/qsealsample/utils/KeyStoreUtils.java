package cz.csas.psd2.qsealsample.utils;

import cz.csas.psd2.qsealsample.constant.QSealSampleConstants;
import cz.csas.psd2.qsealsample.sampleclient.QSealSampleClient;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreUtils {

    public static KeyStore getKeyStore(String identityURI, String identityPassword) {
        InputStream identityStream = QSealSampleClient.class.getResourceAsStream(identityURI);
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(QSealSampleConstants.JKS_INSTANCE_TYPE);
            keyStore.load(identityStream, identityPassword.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return keyStore;
    }
}
