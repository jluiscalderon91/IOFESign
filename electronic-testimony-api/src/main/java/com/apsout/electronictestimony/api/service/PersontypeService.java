package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Persontype;

import java.util.List;

public interface PersontypeService {

    List<Persontype> findInitialAll();

    List<Persontype> findAll();
}
