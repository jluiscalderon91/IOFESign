package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StampdatetimeRepository extends CrudRepository<Stampdatetime, Integer> {

    @Query(value = "SELECT wsd.stampdatetimeByStampdatetimeId " +
            "FROM Workflowstampdatetime wsd " +
            "WHERE wsd.workflowId = :workflowId " +
            "AND wsd.active = 1 " +
            "AND wsd.deleted = 0 " +
            "AND wsd.stampdatetimeByStampdatetimeId.active = 1 " +
            "AND wsd.stampdatetimeByStampdatetimeId.deleted = 0 ")
    List<Stampdatetime> findAllByWorkflow(int workflowId);

    @Query("SELECT sdt " +
            "FROM Stampdatetime sdt " +
            "WHERE sdt.id = :stampdatetimeId " +
            "AND sdt.active = 1 " +
            "AND sdt.deleted = 0 ")
    Optional<Stampdatetime> findBy(@Param("stampdatetimeId") int stampdatetimeId);
}
