package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Temporarysession;

import java.util.Optional;

public interface TemporarysessionService {

    Temporarysession save(Temporarysession temporarysession);

    Optional<Temporarysession> findBy(int personId, String uuid);

    Temporarysession getBy(int personId, String uuid);
}
