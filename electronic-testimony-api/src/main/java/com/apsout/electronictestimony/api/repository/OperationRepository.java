package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends CrudRepository<Operation, Integer> {

    Optional<Operation> findByIdAndActiveAndDeleted(int operationId, byte active, byte deleted);

    @Query("SELECT o " +
            "FROM Operation o " +
            "WHERE o.active = 1 " +
            "AND o.deleted = 0 ")
    List<Operation> findAll();
}
