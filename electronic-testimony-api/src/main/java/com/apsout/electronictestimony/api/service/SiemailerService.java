package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Enterprise;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;

public interface SiemailerService {

    void send(Enterprise enterprise, InternetAddress from, InternetAddress[] to, String subject, String body, List<File> attachments);
}
