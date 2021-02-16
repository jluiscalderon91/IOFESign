package com.apsout.electronictestimony.api.util.dealer;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class RestUploader {
    private static Logger logger = LogManager.getLogger(RestUploader.class);

    public static CloseableHttpResponse post(String requestURL, String JSONBodyString) {
        logger.info("Starting sending to " + requestURL);
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().setSSLSocketFactory(
                    new SSLConnectionSocketFactory(SSLContexts.custom()
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .build(), NoopHostnameVerifier.INSTANCE)
            ).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            logger.error("Preparing to send signed file", e);
        }
        HttpPost httpPost = new HttpPost(requestURL);
        HttpEntity reqEntity = new StringEntity(JSONBodyString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(reqEntity);
        try {
            final CloseableHttpResponse execute = httpClient.execute(httpPost);
            logger.info(String.format("Send to %s finished", requestURL));
            return execute;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Calling to request URL: %s", requestURL), e);
        }
    }
}
