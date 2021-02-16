package com.apsout.electronictestimony.api.util.x509;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class KeyUtil {

    private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

    private KeyUtil() {
        throw new IllegalStateException("Key utility class");
    }

    public static KeyManagerFactory generateKeyManager(String certPath, String certPassword) {
        KeyStore keyStore = CertificateUtil.generateKeystore(new File(certPath), certPassword);
        KeyManagerFactory keyManagerFactory = null;
        try {
            keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Obtaining an instance of a keyManagerFactory", e);
        }
        try {
            keyManagerFactory.init(keyStore, certPassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RuntimeException("Initializing an instance of a keyManagerFactory", e);
        }
        return keyManagerFactory;
    }
}
