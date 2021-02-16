package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Scope;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScopeRepository extends CrudRepository<Scope, Integer> {

    Optional<Scope> findFirstByPersonIdAndActiveAndDeleted(int personId, byte active, byte deleted);

    Optional<Scope> findByPersonIdAndDeleted(int personId, byte deleted);
}
