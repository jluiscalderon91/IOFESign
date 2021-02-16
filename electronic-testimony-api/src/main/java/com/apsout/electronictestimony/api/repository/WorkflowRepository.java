package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface WorkflowRepository extends PagingAndSortingRepository<Workflow, Integer> {

    List<Workflow> findAllByEnterpriseIdInAndActiveAndDeleted(List<Integer> enterpriseIds, byte active, byte deleted);

    @Query("SELECT w " +
            "FROM Workflow w " +
            "WHERE w.enterpriseId <> 1 " +
            "AND w.active = :active " +
            "AND w.deleted = :deleted ")
    List<Workflow> findAllByActiveAndDeleted(byte active, byte deleted);

    Page<Workflow> findByEnterpriseIdInAndDeleted(List<Integer> enterpriseIds, byte deleted, Pageable pageable);

    Page<Workflow> findByEnterpriseIdInAndDescriptionContainingAndDeleted(List<Integer> enterpriseIds, String term2Search, byte deleted, Pageable pageable);

    Optional<Workflow> findByIdAndActiveAndDeleted(int workflowId, byte active, byte deleted);

    @Query("SELECT pw.workflowByWorkflowId " +
            "FROM Personworkflow pw " +
            "WHERE pw.personId = :personId " +
            "AND pw.active = 1 " +
            "AND pw.deleted = 0 " +
            "AND pw.personByPersonId.active = 1 " +
            "AND pw.personByPersonId.deleted = 0 " +
            "AND pw.workflowByWorkflowId.active = 1 " +
            "AND pw.workflowByWorkflowId.deleted = 0 ")
    List<Workflow> findAllWherePersonWasAssigned(int personId);

    @Query("SELECT DISTINCT p.workflowByWorkflowId " +
            "FROM Participant p " +
            "WHERE p.personId = :personId " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 " +
            "AND p.personByPersonId.active = 1 " +
            "AND p.personByPersonId.deleted = 0 " +
            "AND p.workflowByWorkflowId.active = 1 " +
            "AND p.workflowByWorkflowId.deleted = 0 ")
    List<Workflow> findAllWherePersonIsParticipant(int personId);
}
