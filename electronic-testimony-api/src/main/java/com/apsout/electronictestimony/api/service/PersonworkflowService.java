package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Personworkflow;

import java.util.Optional;

public interface PersonworkflowService {

    Optional<Personworkflow> findBy(Personworkflow personworkflow);

    Personworkflow save(Personworkflow personworkflow);
}
