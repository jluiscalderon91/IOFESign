package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Fonttype;

import java.util.List;
import java.util.Optional;

public interface FonttypeService {

    List<Fonttype> findInitialAll();

    List<Fonttype> findAll();

    Optional<Fonttype> findBy(int fonttypeId);

    Fonttype getBy(int fonttypeId);
}
