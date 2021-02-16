package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Historicalhash;

import java.util.Optional;

public interface HistoricalHashService {
    Historicalhash save(Historicalhash historicalhash);

    Optional<Historicalhash> findBy(String hashIdentifier);

    Historicalhash getBy(String hashIdentifier);
}
