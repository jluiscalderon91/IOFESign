package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Sieemailoperator;

import java.util.Optional;

public interface SieemailoperatorService {
    Sieemailoperator save(Sieemailoperator sieemailoperator);

    Optional<Sieemailoperator> findBy(int operatorId);

    Sieemailoperator getBy(int operatorId);
}
