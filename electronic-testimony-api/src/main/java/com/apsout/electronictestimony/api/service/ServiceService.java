package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.util.enums.Service;

import java.util.List;

public interface ServiceService {

    public int trasnlateService(Service service);

    List<com.apsout.electronictestimony.api.entity.Service> findInitialAll();

    List<com.apsout.electronictestimony.api.entity.Service> findAll();
}
