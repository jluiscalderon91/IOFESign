package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.service.MailerService;
import com.apsout.electronictestimony.api.util.enums.MailRecipientType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerServiceImpl implements MailerService {
    private static final Logger logger = LoggerFactory.getLogger(MailerServiceImpl.class);

    @Autowired
    public JavaMailSender javaMailSender;

    @Override
    public void send(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void send(InternetAddress from,
                     List<InternetAddress> to,
                     List<InternetAddress> cc,
                     List<InternetAddress> bcc,
                     String subject,
                     String body) {
        send(from, to, cc, bcc, subject, body, false);
    }

    @Override
    public void send(InternetAddress from,
                     List<InternetAddress> to,
                     List<InternetAddress> cc,
                     List<InternetAddress> bcc,
                     String subject,
                     String body,
                     boolean html) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            messageHelper.setFrom(from);
            prepareRecipients(messageHelper, to, MailRecipientType.TO);
            prepareRecipients(messageHelper, cc, MailRecipientType.CC);
            prepareRecipients(messageHelper, bcc, MailRecipientType.BCC);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, html);
            this.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(String.format("Sending mail message of from: %s, to: %s", from, to));
        }
    }

    @Override
    public void send(InternetAddress from, List<InternetAddress> to, String subject, String body) {
        this.send(from, to, subject, body, false);
    }

    @Override
    public void send(InternetAddress from, List<InternetAddress> to, String subject, String body, boolean html) {
        this.send(from, to, null, null, subject, body, html);
    }

    private void prepareRecipients(MimeMessageHelper messageHelper, List<InternetAddress> recipients, MailRecipientType mailRecipientType) throws MessagingException {
        if (recipients != null)
            for (InternetAddress internetAddress : recipients)
                switch (mailRecipientType) {
                    case TO:
                        messageHelper.addTo(internetAddress);
                        break;
                    case CC:
                        messageHelper.addCc(internetAddress);
                        break;
                    default:
                        messageHelper.addBcc(internetAddress);
                        break;
                }
    }

    @Override
    public MimeMessage buildMessage(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, String subject, String body, List<File> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart textpart = new MimeBodyPart();
        message.setFrom(from);
        message.setRecipients(Message.RecipientType.TO, to);
        message.setRecipients(Message.RecipientType.CC, cc);
        message.setSubject(subject);
        textpart.setContent(body, "text/html; charset=utf-8");
        attachments.stream().forEach(attachment -> {
            MimeBodyPart attachpart = new MimeBodyPart();
            try {
                attachpart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
                attachpart.setFileName(attachment.getName());
                multipart.addBodyPart(attachpart);
            } catch (MessagingException e) {
                logger.error(String.format("Adding attachments to delivery mail notification from: %s", from.getAddress()), e);
                throw new RuntimeException(e);
            }
        });
        multipart.addBodyPart(textpart);
        message.setContent(multipart);
        logger.info(String.format("Mimemessage content builded correctly for: %s", from.getAddress()));
        return message;
    }

    public MimeMessage buildMessage(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, String subject, String body) throws MessagingException {
        return buildMessage(from, to, cc, subject, body, new ArrayList<>());
    }
}
