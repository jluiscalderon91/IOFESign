package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stamprubric;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StamprubricRepository extends CrudRepository<Stamprubric, Integer> {

    @Query("SELECT sr " +
            "FROM Stamprubric sr " +
            "WHERE sr.id = :stamprubricId " +
            "AND sr.active = 1 " +
            "AND sr.deleted = 0 ")
    Optional<Stamprubric> findBy(@Param("stamprubricId") int stamprubricId);

    @Query(value = "SELECT wsr.stamprubricByStamprubricId " +
            "FROM Workflowstamprubric wsr " +
            "WHERE wsr.workflowId = :workflowId " +
            "AND wsr.active = 1 " +
            "AND wsr.deleted = 0 " +
            "AND wsr.stamprubricByStamprubricId.active = 1 " +
            "AND wsr.stamprubricByStamprubricId.deleted = 0 ")
    List<Stamprubric> findAllByWorkflow(int workflowId);

    @Query("SELECT sr " +
            "FROM Stamprubric sr " +
            "WHERE sr.participantId = :participantId " +
            "AND sr.active = 1 " +
            "AND sr.deleted = 0 ")
    Optional<Stamprubric> findByParticipantId(@Param("participantId") int participantId);

    @Query(value = "SELECT sr.* " +
            "FROM stamprubric sr " +
            "INNER JOIN participant p " +
            "ON p.id = sr.participantId " +
//            "INNER JOIN person p1 " +
//            "ON p1.id = p.personId " +
            "INNER JOIN workflow w " +
            "ON w.id = p.workflowId " +
            "INNER JOIN document d " +
            "ON d.workflowId = w.id " +
            "INNER JOIN operator o " +
            "ON o.documentId = d.id " +
            "WHERE o.personId = :personId " +
            "AND d.id = :documentId " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 ", nativeQuery = true)
    Optional<Stamprubric> findBy(@Param("personId") int personId, @Param("documentId") int documentId);
}
