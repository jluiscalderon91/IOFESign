package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.entity.common.MoreAboutSiecredential;
import com.apsout.electronictestimony.api.util.others.NumberUtil;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

public class SiecredentialAllocator {

    public static Siecredential build(Enterprise enterprise, Optional<Siecredential> oldOptSiecredential, String username, String password) {
        Siecredential siecredential = new Siecredential();
        siecredential.setEnterpriseId(enterprise.getId());
        siecredential.setEnterpriseByEnterpriseId(enterprise);
        siecredential.setUsername(username);
        siecredential.setPassword(password);
        final int localPartPosition = 0;
        final int domainPosition = 1;
        String localPart = username.split(Pattern.quote("@"))[localPartPosition];
        String domain = username.split(Pattern.quote("@"))[domainPosition];
        siecredential.setLocalpart(localPart);
        siecredential.setDomain(domain);
        double nextVersion = getNextVersion(oldOptSiecredential);
        siecredential.setVersion(nextVersion);
        ofPostMethod(siecredential);
        return siecredential;
    }

    public static double getNextVersion(Optional<Siecredential> oldOptSiecredential) {
        if (oldOptSiecredential.isPresent()) {
            Siecredential siecredential = oldOptSiecredential.get();
            if (null == siecredential.getVersion()) {
                return 0.1;
            } else {
                return NumberUtil.round(siecredential.getVersion() + 0.1, 1);
            }
        }
        return 0.1;
    }

    public static void ofPostMethod(Siecredential siecredential) {
        siecredential.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        siecredential.setActive(States.ACTIVE);
        siecredential.setDeleted(States.EXISTENT);
    }

    public static void loadSieCertInfo(Enterprise enterprise, Siecertificate siecertificate, MoreAboutSiecredential moreAboutSiecredential) {
        moreAboutSiecredential.setEnterpriseName(enterprise.getName());
        moreAboutSiecredential.setCertificateName(siecertificate.getName());
        moreAboutSiecredential.setCertificateNotAfter(siecertificate.getNotAfter());
        moreAboutSiecredential.setCertificateNotBefore(siecertificate.getNotBefore());
    }

    public static Siecredential forReturn(Siecredential siecredential) {
        Siecredential siecredential1 = new Siecredential();
        siecredential1.setUsername(siecredential.getUsername());
        siecredential1.setPassword(siecredential.getPassword());
        siecredential1.setVersion(siecredential.getVersion());
        siecredential1.setLocalpart(siecredential.getLocalpart());
        return siecredential1;
    }
}
