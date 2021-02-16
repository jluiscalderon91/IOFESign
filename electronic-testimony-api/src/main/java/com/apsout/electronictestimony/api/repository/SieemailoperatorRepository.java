package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Sieemailoperator;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SieemailoperatorRepository extends CrudRepository<Sieemailoperator, Integer> {

    Optional<Sieemailoperator> findByOperatorIdAndActiveAndDeleted(int operatorId, byte active, byte deleted);
}
