package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stamptestfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StamptestfileRepository extends CrudRepository<Stamptestfile, Integer> {

    @Query("SELECT stf " +
            "FROM Stamptestfile stf " +
            "WHERE stf.id = :stamptestfileId " +
            "AND stf.active = 1 " +
            "AND stf.deleted = 0 ")
    Optional<Stamptestfile> findBy(@Param("stamptestfileId") int stamptestfileId);

    @Query(value = "SELECT stf " +
            "FROM Stamptestfile stf " +
            "WHERE stf.workflowId = :workflowId " +
            "AND stf.active = 1 " +
            "AND stf.deleted = 0 ")
    Optional<Stamptestfile> findByWorkflow(int workflowId);
}
