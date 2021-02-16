package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stamplayoutfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StamplayoutfileRepository extends CrudRepository<Stamplayoutfile, Integer> {

    @Query("SELECT slf " +
            "FROM Stamplayoutfile slf " +
            "WHERE slf.id = :stamplayoutfileId " +
            "AND slf.active = 1 " +
            "AND slf.deleted = 0 ")
    Optional<Stamplayoutfile> findBy(@Param("stamplayoutfileId") int stamplayoutfileId);

    @Query(value = "SELECT slf " +
            "FROM Stamplayoutfile slf " +
            "WHERE slf.workflowId = :workflowId " +
            "AND slf.active = 1 " +
            "AND slf.deleted = 0 ")
    Optional<Stamplayoutfile> findByWorkflow(int workflowId);
}
