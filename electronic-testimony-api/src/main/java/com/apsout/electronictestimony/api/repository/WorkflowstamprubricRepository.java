package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowstamprubric;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowstamprubricRepository extends CrudRepository<Workflowstamprubric, Integer> {

    @Query(value = "SELECT wsr " +
            "FROM Workflowstamprubric wsr " +
            "WHERE wsr.workflowId = :workflowId " +
            "AND wsr.stamprubricId = :stamprubricId " +
            "AND wsr.active = 1 " +
            "AND wsr.deleted = 0 ")
    Optional<Workflowstamprubric> findBy(int workflowId, int stamprubricId);
}
