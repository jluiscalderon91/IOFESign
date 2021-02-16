package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.Observationcancel;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DocumentmodificationService {

    Documentmodification save(Documentmodification documentmodification);

    List<Documentmodification> findHistoricalGreaterThan(Timestamp historicalCreationDatetime);

    Optional<Documentmodification> loadBy(int documentId);
}
