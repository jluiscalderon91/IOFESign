package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Fontcolor;

import java.util.List;
import java.util.Optional;

public interface FontcolorService {

    List<Fontcolor> findInitialAll();

    List<Fontcolor> findAll();

    Optional<Fontcolor> findBy(int fontcolorId);

    Fontcolor getBy(int fontcolorId);
}
