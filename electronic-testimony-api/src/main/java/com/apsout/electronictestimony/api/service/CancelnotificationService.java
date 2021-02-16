package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Cancelnotification;

import java.util.Optional;

public interface CancelnotificationService {

    Cancelnotification save(Cancelnotification cancelnotification);

    Optional<Cancelnotification> findBy(byte sent);

    Optional<Cancelnotification> find4Send();

    void notificate();
}
