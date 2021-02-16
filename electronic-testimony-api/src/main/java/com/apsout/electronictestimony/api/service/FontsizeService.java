package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Fontsize;

import java.util.List;
import java.util.Optional;

public interface FontsizeService {

    List<Fontsize> findInitialAll();

    List<Fontsize> findAll();

    Optional<Fontsize> findBy(int fontsizeId);

    Fontsize getBy(int fontsizeId);
}
