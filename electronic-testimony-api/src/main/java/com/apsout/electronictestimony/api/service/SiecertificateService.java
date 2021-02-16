package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;

import java.util.Optional;

public interface SiecertificateService {

    Siecertificate save(Siecertificate siecertificate);

    Integer disableAllBy(Enterprise enterprise);

    Optional<Siecertificate> findBy(Siecredential siecredential);

    Siecertificate getBy(Siecredential siecredential);
}
