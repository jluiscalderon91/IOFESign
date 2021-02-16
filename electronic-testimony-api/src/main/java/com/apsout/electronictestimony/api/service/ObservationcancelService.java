package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Observationcancel;

import java.util.Optional;

public interface ObservationcancelService {

    Observationcancel save(Observationcancel observationcancel);

    Optional<Observationcancel> findByDocument(Document document);

    Optional<Observationcancel> loadBy(int documentId);
}
