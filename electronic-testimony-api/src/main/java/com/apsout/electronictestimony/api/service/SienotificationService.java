package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Sienotification;

import java.util.Optional;

public interface SienotificationService {

    Sienotification save(Sienotification sienotification);

    Optional<Sienotification> findBy(byte sent);

    Optional<Sienotification> find4Send();

    void notificate();
}
