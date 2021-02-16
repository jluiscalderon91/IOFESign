package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Temporarysession;
import com.apsout.electronictestimony.api.exception.TemporaryNotFoundException;
import com.apsout.electronictestimony.api.repository.TemporarysessionRepository;
import com.apsout.electronictestimony.api.service.TemporarysessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemporarysessionServiceImpl implements TemporarysessionService {
    private static final Logger logger = LoggerFactory.getLogger(TemporarysessionServiceImpl.class);

    @Autowired
    private TemporarysessionRepository repository;

    @Override
    public Temporarysession save(Temporarysession temporarysession) {
        return repository.save(temporarysession);
    }

    public Optional<Temporarysession> findBy(int personId, String uuid) {
        return repository.findBy(personId, uuid);
    }

    @Override
    public Temporarysession getBy(int personId, String uuid) {
        Optional<Temporarysession> optional = this.findBy(personId, uuid);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new TemporaryNotFoundException(String.format("Temporary not found by personId: %d, UUID: %s", personId, uuid));
    }
}
