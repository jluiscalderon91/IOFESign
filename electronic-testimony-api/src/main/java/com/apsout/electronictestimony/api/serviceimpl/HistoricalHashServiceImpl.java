package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Historicalhash;
import com.apsout.electronictestimony.api.exception.HistoricalhashNotFoundException;
import com.apsout.electronictestimony.api.repository.HistoricalHashRepository;
import com.apsout.electronictestimony.api.service.HistoricalHashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoricalHashServiceImpl implements HistoricalHashService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    @Autowired
    private HistoricalHashRepository historicalHashRepository;

    @Override
    public Historicalhash save(Historicalhash historicalhash) {
        final Historicalhash saved = historicalHashRepository.save(historicalhash);
        logger.info(String.format("Historial hash saved with historicalHashId: %d, hashIdentifier: %s", historicalhash.getId(), historicalhash.getHashIdentifier()));
        return saved;
    }

    @Override
    public Optional<Historicalhash> findBy(String hashIdentifier) {
        return historicalHashRepository.findBy(hashIdentifier);
    }

    @Override
    public Historicalhash getBy(String hashIdentifier) {
        Optional<Historicalhash> optional = this.findBy(hashIdentifier);
        if (optional.isPresent()) {
            return optional.get();
        }
        logger.warn(String.format("Historical hash document (*) not found  with hashIdentifier: %s", hashIdentifier));
        throw new HistoricalhashNotFoundException("Recurso inexistente para los parámetros enviados, comuníquese con el administrador");
    }
}
