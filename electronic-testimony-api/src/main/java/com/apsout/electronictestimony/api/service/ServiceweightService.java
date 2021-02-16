package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Serviceweight;
import com.apsout.electronictestimony.api.util.enums.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceweightService {

    List<Serviceweight> findInitialAll();

    List<Serviceweight> findAll();

    Optional<Serviceweight> findBy(int serviceweightId);

    Serviceweight getBy(int serviceweightId);

    Serviceweight getBy(Service service);
}
