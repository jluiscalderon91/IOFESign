package com.apsout.electronictestimony.api.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

public interface MailerService {
    void send(MimeMessage mimeMessage);

    void send(InternetAddress from,
              List<InternetAddress> to,
              List<InternetAddress> cc,
              List<InternetAddress> bcc,
              String subject,
              String body);

    void send(InternetAddress from,
              List<InternetAddress> to,
              List<InternetAddress> cc,
              List<InternetAddress> bcc,
              String subject,
              String body,
              boolean html);

    void send(InternetAddress from,
              List<InternetAddress> to,
              String subject,
              String body);

    void send(InternetAddress from,
              List<InternetAddress> to,
              String subject,
              String body,
              boolean html);

    MimeMessage buildMessage(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, String subject, String body, List<File> attachments) throws MessagingException;

    MimeMessage buildMessage(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, String subject, String body) throws MessagingException;
}
