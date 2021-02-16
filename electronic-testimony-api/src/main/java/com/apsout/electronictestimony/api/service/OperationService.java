package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    List<Operation> findInitialAll();

    List<Operation> findAll();

    Optional<Operation> findBy(int operationId);

    Operation getBy(int operationId);
}
