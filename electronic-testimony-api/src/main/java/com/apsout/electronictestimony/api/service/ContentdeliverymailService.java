package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Deliverymail;

import java.util.Optional;

public interface ContentdeliverymailService {

    Contentdeliverymail save(Contentdeliverymail contentdeliverymail);

    Optional<Contentdeliverymail> findBy(int deliverymailId);

    Contentdeliverymail getBy(Deliverymail deliverymail);
}
