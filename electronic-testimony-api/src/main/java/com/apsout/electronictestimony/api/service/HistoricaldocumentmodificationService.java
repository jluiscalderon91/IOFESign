package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Historicaldocumentmodification;

import java.util.Optional;

public interface HistoricaldocumentmodificationService {

    Historicaldocumentmodification save(Historicaldocumentmodification historicaldocumentmodification);

    Optional<Historicaldocumentmodification> findBy(Document document);
}
