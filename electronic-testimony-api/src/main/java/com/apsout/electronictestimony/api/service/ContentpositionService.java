package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Contentposition;

import java.util.List;
import java.util.Optional;

public interface ContentpositionService {

    List<Contentposition> findInitialAll();

    List<Contentposition> findAll();

    Optional<Contentposition> findBy(int contentpositionId);

    Contentposition getBy(int contentpositionId);
}
