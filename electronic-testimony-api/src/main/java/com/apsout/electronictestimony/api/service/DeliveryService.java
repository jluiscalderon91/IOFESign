package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Delivery;

import java.util.Optional;

public interface DeliveryService {

    Delivery save(Delivery delivery);

    Optional<Delivery> findBy(byte sent);

    Optional<Delivery> find4Send();

    void deliver();
}
