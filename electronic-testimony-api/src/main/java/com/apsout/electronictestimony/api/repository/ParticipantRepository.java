package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Integer> {

    @Query(value = "SELECT p " +
            "FROM Participant p " +
            "WHERE p.workflowId = :workflowId " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 " +
            "ORDER BY p.orderParticipant ASC")
    List<Participant> findAllBy(int workflowId);

    @Query(value = "SELECT p " +
            "FROM Participant p " +
            "WHERE p.workflowId = :workflowId " +
            "AND p.personId = 1 " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 " +
            "ORDER BY p.orderParticipant ASC")
    List<Participant> findAllOnlyReplaceablesBy(int workflowId);

    @Query(value = "SELECT p " +
            "FROM Participant p " +
            "WHERE p.workflowId = :workflowId " +
            "AND p.deleted = :deleted " +
            "AND p.active = 1 " +
            "ORDER BY p.orderParticipant ASC")
    Page<Participant> findByWorkflowIdAndDeleted(int workflowId, byte deleted, Pageable pageable);

    @Query(value = "SELECT p " +
            "FROM Participant p " +
            "WHERE p.workflowId = :workflowId " +
            "AND p.personId = :personId " +
            "AND p.orderParticipant = :orderParticipant " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ")
    Optional<Participant> findByWorkflowIdAndPersonIdAndOrderParticipant(@Param("workflowId") int workflowId,
                                                                         @Param("personId") int personId,
                                                                         @Param("orderParticipant") int orderParticipant);

    @Query(value = "SELECT p " +
            "FROM Participant p " +
            "WHERE p.workflowId = :workflowId " +
            "AND p.orderParticipant = :orderParticipant " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ")
    Optional<Participant> findByWorkflowIdAndOrderParticipant(@Param("workflowId") int workflowId,
                                                              @Param("orderParticipant") int orderParticipant);
}
