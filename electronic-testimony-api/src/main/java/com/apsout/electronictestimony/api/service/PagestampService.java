package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Pagestamp;

import java.util.List;
import java.util.Optional;

public interface PagestampService {

    List<Pagestamp> findInitialAll();

    List<Pagestamp> findAll();

    Optional<Pagestamp> findBy(int pagestampId);

    Pagestamp getBy(int pagestampId);
}
