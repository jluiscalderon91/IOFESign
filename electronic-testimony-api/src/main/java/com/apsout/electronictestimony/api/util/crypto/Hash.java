package com.apsout.electronictestimony.api.util.crypto;

import com.apsout.electronictestimony.api.serviceimpl.DocumentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class Hash {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private Hash() {
    }

    public static String sha256(String originalString) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Inexistent instance ", e);
        }
        byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int index = 0; index < hash.length; index++) {
            String hex = Integer.toHexString(0xff & hash[index]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String Crc(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        return Long.toHexString(crc32.getValue());
    }

    public static String Crc(int data) {
        String data2 = String.valueOf(data);
        return Crc(data2);
    }
}
