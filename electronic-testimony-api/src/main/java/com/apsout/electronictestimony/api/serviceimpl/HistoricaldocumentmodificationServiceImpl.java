package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Historicaldocumentmodification;
import com.apsout.electronictestimony.api.repository.HistoricaldocumentmodificationRepository;
import com.apsout.electronictestimony.api.service.HistoricaldocumentmodificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoricaldocumentmodificationServiceImpl implements HistoricaldocumentmodificationService {
    private static final Logger logger = LoggerFactory.getLogger(HistoricaldocumentmodificationServiceImpl.class);

    @Autowired
    private HistoricaldocumentmodificationRepository repository;

    @Override
    public Historicaldocumentmodification save(Historicaldocumentmodification historicaldocumentmodification) {
        return repository.save(historicaldocumentmodification);
    }

    @Override
    public Optional<Historicaldocumentmodification> findBy(Document document) {
        return repository.findBy(document);
    }
}
