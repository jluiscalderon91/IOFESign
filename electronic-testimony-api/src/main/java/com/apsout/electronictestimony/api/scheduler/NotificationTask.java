package com.apsout.electronictestimony.api.scheduler;

import com.apsout.electronictestimony.api.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import java.util.List;

@Resource
public class NotificationTask implements Runnable {

    @Autowired
    private MailerService mailerService;

    private InternetAddress from;
    private List<InternetAddress> to;
    private List<InternetAddress> cc;
    private List<InternetAddress> bcc;
    private String subject;
    private String body;

    public NotificationTask(InternetAddress from, List<InternetAddress> to, List<InternetAddress> cc, List<InternetAddress> bcc, String subject, String body) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void run() {
        mailerService.send(from, to, cc, bcc, subject, body);
    }
}
