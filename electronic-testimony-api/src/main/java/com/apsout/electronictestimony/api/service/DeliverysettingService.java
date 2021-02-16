package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Deliverysetting;
import com.apsout.electronictestimony.api.entity.Enterprise;

import java.util.Optional;

public interface DeliverysettingService {

    Deliverysetting save(Deliverysetting deliverysetting);

    Optional<Deliverysetting> findByEnterprise(Enterprise enterprise);

    Deliverysetting getByEnterprise(Enterprise enterprise);
}
