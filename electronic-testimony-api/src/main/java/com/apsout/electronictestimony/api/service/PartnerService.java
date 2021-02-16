package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Partner;

import java.util.List;

public interface PartnerService {

    Partner getBy(int partnerId);

    List<Partner> findAll();

//    List<State> findAllBy(List<Integer> stateIds);
}
