package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Balancepurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BalancepurchaseService {

    Balancepurchase save(Balancepurchase balancepurchase);

    Page<Balancepurchase> findBy(int partnerId, String fromOnMilis, String toOnMilis, Pageable pageable);
}
