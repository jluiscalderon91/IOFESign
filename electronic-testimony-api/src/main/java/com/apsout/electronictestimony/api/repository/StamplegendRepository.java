package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Fontcolor;
import com.apsout.electronictestimony.api.entity.Stamplegend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StamplegendRepository extends CrudRepository<Stamplegend, Integer> {

    @Query(value = "SELECT ws.stamplegendByStamplegendId " +
            "FROM Workflowstamplegend ws " +
            "WHERE ws.workflowId = :workflowId " +
            "AND ws.active = 1 " +
            "AND ws.deleted = 0 " +
            "AND ws.stamplegendByStamplegendId.active = 1 " +
            "AND ws.stamplegendByStamplegendId.deleted = 0 ")
    List<Stamplegend> findAllByWorkflow(int workflowId);

    @Query("SELECT sl " +
            "FROM Stamplegend sl " +
            "WHERE sl.id = :stamplegendId " +
            "AND sl.active = 1 " +
            "AND sl.deleted = 0 ")
    Optional<Stamplegend> findBy(@Param("stamplegendId") int stamplegendId);
}
