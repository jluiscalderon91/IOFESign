package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowstamplegend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowstamplegendRepository extends CrudRepository<Workflowstamplegend, Integer> {

    @Query(value = "SELECT wsl " +
            "FROM Workflowstamplegend wsl " +
            "WHERE wsl.workflowId = :workflowId " +
            "AND wsl.stamplegendId = :stamplegendId " +
            "AND wsl.active = 1 " +
            "AND wsl.deleted = 0 ")
    Optional<Workflowstamplegend> findBy(int workflowId, int stamplegendId);
}
