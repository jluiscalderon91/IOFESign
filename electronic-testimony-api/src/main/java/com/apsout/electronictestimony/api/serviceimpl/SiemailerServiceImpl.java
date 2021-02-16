package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.service.SiecertificateService;
import com.apsout.electronictestimony.api.service.SiecredentialService;
import com.apsout.electronictestimony.api.service.SiemailerService;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.x509.CertificateUtil;
import com.apsout.electronictestimony.api.util.x509.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

@Service
public class SiemailerServiceImpl implements SiemailerService {
    private static final Logger logger = LoggerFactory.getLogger(SiemailerServiceImpl.class);

    @Autowired
    private SiecredentialService siecredentialService;
    @Autowired
    private SiecertificateService siecertificateService;

    private String sieUsername;
    private String siePassword;
    private String certPath;
    private String certPassword;
    private String sieDomain;
    private MimeMessage message;
    private Session session;
    private Transport transport;

    public void send(Enterprise enterprise, InternetAddress from, InternetAddress[] to, String subject, String body, List<File> attachments) throws RuntimeException {
        loadInitialParametersBy(enterprise);
        stablish2ServerConnection();
        construcMessage(from, to, subject, body, attachments);
        sendMessage(to);
    }

    private void loadInitialParametersBy(Enterprise enterprise) {
        Siecredential siecredential = siecredentialService.getLastActiveBy(enterprise);
        Siecertificate siecertificate = siecertificateService.getBy(siecredential);
        sieUsername = siecredential.getLocalpart();
        siePassword = siecredential.getPassword();
        File file = CertificateUtil.buildP12CertificateFile(siecertificate);
        certPath = file.getAbsolutePath();
        certPassword = siecertificate.getPassword();
        sieDomain = siecredential.getDomain();
        logger.info(String.format("Sie parameters initialized correctly for enterpriseId: %d", enterprise.getId()));
    }

    public void stablish2ServerConnection() {
        String smtpHost = new StringBuilder("smtp.").append(sieDomain).toString();
        String localPart = sieUsername;
        String electronicAddress = new StringBuilder(localPart).append("@").append(sieDomain).toString();
        Properties properties = new Properties();
        properties.setProperty("mail.host", smtpHost);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        try {
            properties.setProperty("mail.smtp.localhost", InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            throw new RuntimeException(String.format("Obtaing localhost for param: 'mail.smtp.localhost' with user: %s", electronicAddress), e);
        }
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.user", localPart);
        properties.setProperty("mail.smtp.from", electronicAddress);
        KeyManagerFactory keyManagerFactory = KeyUtil.generateKeyManager(certPath, certPassword);
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("Obtaing TLS instance for SSLContext for: %s", electronicAddress), e);
        }
        try {
            context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
        } catch (KeyManagementException e) {
            throw new RuntimeException(String.format("Initializing TLS instance for SSLContext for: %s", electronicAddress), e);
        }
        SSLSocketFactory sSLSocketFactory = context.getSocketFactory();
        properties.put("mail.smtp.ssl.socketFactory", sSLSocketFactory);
        session = Session.getInstance(properties);
        // TODO check why doesn't it suffice: properties.setProperty("mail.password", "Pl5gz1A6")
        session.setPasswordAuthentication(new URLName("smtp://" + electronicAddress), new PasswordAuthentication(localPart, siePassword));
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(String.format("Obtaining Transport of sessión sie connection for: %s", electronicAddress), e);
        }
        try {
            transport.connect();
        } catch (MessagingException e) {
            throw new RuntimeException(String.format("Connecting to sie server mail by: %s", electronicAddress), e);
        }
        logger.info(String.format("Sucessfull conection to sie server mail for user: %s", electronicAddress));
    }

    private void sendMessage(InternetAddress[] to) {
        try {
            transport.sendMessage(message, to);
        } catch (MessagingException e) {
            logger.error(String.format("Sending sie message from: %s, to: %s", EmailAddress.getFromOf(message), EmailAddress.getToOf(message)), e);
        }
        logger.info(String.format("Sie message sent correctly from: %s, to: %s", EmailAddress.getFromOf(message), EmailAddress.getToOf(message)));
    }

    private void construcMessage(InternetAddress from, InternetAddress[] to, String subject, String body, List<File> attachments) {
        message = new MimeMessage(session);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart textpart = new MimeBodyPart();
        try {
            message.setFrom(from);
            //TODO Build for multiple sent
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);
            textpart.setContent(body, "text/html; charset=utf-8");
            attachments.stream().forEach(attachment -> {
                MimeBodyPart attachpart = new MimeBodyPart();
                try {
                    attachpart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
                    attachpart.setFileName(attachment.getName());
                    multipart.addBodyPart(attachpart);
                } catch (MessagingException e) {
                    logger.error(String.format("Adding attachments to sie mail notification from: %s", from.getAddress()), e);
                }
            });
            multipart.addBodyPart(textpart);
            message.setContent(multipart);
            logger.info(String.format("Sie message content builded correctly for: %s", from.getAddress()));
        } catch (MessagingException e) {
            logger.error(String.format("Building header and body both mail for: %s", from.getAddress()), e);
        }
    }
}
