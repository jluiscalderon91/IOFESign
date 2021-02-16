package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Sieemailoperator;
import com.apsout.electronictestimony.api.exception.SieemailoperatorNotFoundException;
import com.apsout.electronictestimony.api.repository.SieemailoperatorRepository;
import com.apsout.electronictestimony.api.service.SieemailoperatorService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SieemailoperatorServiceImpl implements SieemailoperatorService {
    private static final Logger logger = LoggerFactory.getLogger(SieemailoperatorServiceImpl.class);

    @Autowired
    private SieemailoperatorRepository repository;

    @Override
    public Sieemailoperator save(Sieemailoperator sieemailoperator) {
        return repository.save(sieemailoperator);
    }

    public Optional<Sieemailoperator> findBy(int operatorId) {
        return repository.findByOperatorIdAndActiveAndDeleted(operatorId, States.ACTIVE, States.EXISTENT);
    }

    public Sieemailoperator getBy(int operatorId) {
        Optional<Sieemailoperator> optional = findBy(operatorId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new SieemailoperatorNotFoundException(String.format("Config. SIE not found, sieemailoperator not found for operatorId: %d", operatorId));
    }
}
