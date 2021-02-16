package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowtype;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkflowtypeRepository extends CrudRepository<Workflowtype, Integer> {

    @Query("SELECT wt " +
            "FROM Workflowtype wt " +
            "WHERE wt.active = 1 " +
            "AND wt.deleted = 0 ")
    List<Workflowtype> findAll();

    @Query("SELECT wt " +
            "FROM Workflowtype wt " +
            "WHERE wt.id = :workflowtypeId " +
            "AND wt.active = 1 " +
            "AND wt.deleted = 0 ")
    Optional<Workflowtype> findBy(@Param("workflowtypeId") int workflowtypeId);
}
