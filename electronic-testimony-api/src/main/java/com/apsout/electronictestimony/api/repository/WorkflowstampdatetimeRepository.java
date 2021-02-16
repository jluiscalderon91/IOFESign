package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowstampdatetime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowstampdatetimeRepository extends CrudRepository<Workflowstampdatetime, Integer> {

    @Query(value = "SELECT wsd " +
            "FROM Workflowstampdatetime wsd " +
            "WHERE wsd.workflowId = :workflowId " +
            "AND wsd.stampdatetimeId = :stampdatetimeId " +
            "AND wsd.active = 1 " +
            "AND wsd.deleted = 0 ")
    Optional<Workflowstampdatetime> findBy(int workflowId, int stampdatetimeId);
}
