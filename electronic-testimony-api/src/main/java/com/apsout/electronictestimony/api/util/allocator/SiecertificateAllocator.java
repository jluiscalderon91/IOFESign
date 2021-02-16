package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import com.apsout.electronictestimony.api.util.x509.CertificateUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SiecertificateAllocator {
    private static final Logger logger = LoggerFactory.getLogger(SiecertificateAllocator.class);

    public static Siecertificate build(Siecredential siecredential, MultipartFile multipartFile, String password) {
        Siecertificate siecertificate = new Siecertificate();
        siecertificate.setSiecredentialId(siecredential.getId());
        siecertificate.setSiecredentialBySiecredentialId(siecredential);
        siecertificate.setPassword(password);
        final String originalFilename = multipartFile.getOriginalFilename();
        siecertificate.setName(originalFilename);
        siecertificate.setBasename(FilenameUtils.getBaseName(originalFilename));
        siecertificate.setExtension(FilenameUtils.getExtension(originalFilename));
        byte[] fileBytes;
        try {
            fileBytes = multipartFile.getBytes();
            siecertificate.setData(fileBytes);
        } catch (IOException e) {
            logger.error(String.format("Writing for save certificate file for siecredentialId: %d", siecredential.getId()));
        }
        X509Certificate x509Certificate = CertificateUtil.buildX509Certificate(siecertificate);
        siecertificate.setNotBefore(DateUtil.cast(x509Certificate.getNotBefore()));
        siecertificate.setNotAfter(DateUtil.cast(x509Certificate.getNotAfter()));
        String alias = CertificateUtil.getFirstCertificateAlias(siecertificate);
        siecertificate.setAlias(alias);
        siecertificate.setLength(multipartFile.getSize());
        ofPostMethod(siecertificate);
        return siecertificate;
    }

    public static void ofPostMethod(Siecertificate siecertificate) {
        siecertificate.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        siecertificate.setActive(States.ACTIVE);
        siecertificate.setDeleted(States.EXISTENT);
    }

    public static Siecertificate forReturn(Siecertificate siecertificate){
        byte[] data = siecertificate.getData();
        String encodedBase64 = FileUtil.encodeBytes2Base64(data);
        Siecertificate siecertificate1 = new Siecertificate();
        siecertificate1.setPassword(siecertificate.getPassword());
        siecertificate1.setName(siecertificate.getName());
        siecertificate1.setNotBefore(siecertificate.getNotBefore());
        siecertificate1.setNotAfter(siecertificate.getNotAfter());
        siecertificate1.setBase64Data(encodedBase64);
        return siecertificate1;
    }
}
