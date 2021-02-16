package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Relatedperson;

import java.util.Optional;

public interface RelatedpersonService {

    Optional<Relatedperson> findByPersonId(int personId);

    Optional<Relatedperson> findByPersonIdRelated(int personIdRelated);
}
