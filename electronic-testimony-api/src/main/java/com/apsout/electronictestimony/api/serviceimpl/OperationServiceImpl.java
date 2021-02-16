package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Operation;
import com.apsout.electronictestimony.api.exception.OperationNotFoundException;
import com.apsout.electronictestimony.api.repository.OperationRepository;
import com.apsout.electronictestimony.api.service.OperationService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {
    private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    @Autowired
    private OperationRepository repository;

    @Override
    public List<Operation> findInitialAll() {
        logger.info("Getting initial list of operations");
        return repository.findAll();
    }

    @Override
    public List<Operation> findAll() {
        return Localstorage.operations.isEmpty() ? findInitialAll() : Localstorage.operations;
    }

    @Override
    public Optional<Operation> findBy(int operationId) {
        return Localstorage.operations
                .stream()
                .filter(operation -> operation.getId().equals(operationId))
                .findFirst();
    }

    @Override
    public Operation getBy(int operationId) {
        Optional<Operation> optional = this.findBy(operationId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new OperationNotFoundException(String.format("Operation not found width operationId: %d", operationId));
    }
}
