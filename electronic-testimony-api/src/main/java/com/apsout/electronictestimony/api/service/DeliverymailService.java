package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Deliverymail;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface DeliverymailService {

    Deliverymail register(int documentId, Contentdeliverymail contentdeliverymail, HttpServletRequest request);

    Deliverymail save(Deliverymail delivery);

    Optional<Deliverymail> findBy(boolean sent);

    Optional<Deliverymail> find4Send();

    void deliver();
}
