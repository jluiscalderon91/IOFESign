package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Integer> {
    Page<Document> findAllByDeleted(byte deleted, Pageable pageable);

    Optional<Document> findByIdAndActiveAndDeleted(int documentId, byte active, byte deleted);

    Optional<Document> findByIdAndDeleted(int documentId, byte deleted);

    @Query("SELECT a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.documentId IN :identifiers " +
            "AND a.operatorByOperatorId.personId = :personId " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "AND a.documentByDocumentId.active = 1 " +
            "AND a.documentByDocumentId.deleted = 0 " +
            "ORDER BY a.documentByDocumentId.id ASC")
    List<Document> findAllByInAndPersonId(@Param("identifiers") List<Integer> identifiers, @Param("personId") int personId);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.personByPersonId.enterpriseIdView = :enterpriseId " +
            "AND d.deleted = 0 " +
            "ORDER BY d.createAt DESC ")
    Page<Document> findAllBy(int enterpriseId, Pageable pageable);

    Optional<Document> findByHashIdentifierAndActiveAndDeleted(String hashIdentifier, byte active, byte deleted);

    @Query("SELECT distinct a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.deleted = 0 " +
            "AND a.documentByDocumentId.workflowId IN :workflowIds " +
            "AND a.documentByDocumentId.stateId IN :stateIds " +
            "AND a.documentByDocumentId.deleted = 0 " +
            "ORDER BY a.documentByDocumentId.createAt DESC ")
    Page<Document> findAllBy4User(@Param("workflowIds") List<Integer> workflowIds,
                                  @Param("stateIds") List<Integer> stateIds,
                                  Pageable pageable);

    @Query("SELECT a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.operatorByOperatorId.personId = :personId " +
            "AND a.documentByDocumentId.finished = 0 " +
            "AND a.completed = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "AND a.documentByDocumentId.workflowId IN :workflowIds " +
            "AND a.documentByDocumentId.stateId IN :stateIds " +
            "AND a.documentByDocumentId.deleted = 0 " +
            "ORDER BY a.createAt DESC ")
    Page<Document> findPendingsBy4User(@Param("workflowIds") List<Integer> workflowIds,
                                       @Param("stateIds") List<Integer> stateIds,
                                       @Param("personId") int personId,
                                       Pageable pageable);

    @Query("SELECT a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.operatorByOperatorId.id = :operatorId " +
            "AND a.documentByDocumentId.finished = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "ORDER BY a.createAt DESC ")
    Document findBy(@Param("operatorId") int operatorId);

    @Query("SELECT a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.operatorByOperatorId.id = :operatorId " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "ORDER BY a.createAt DESC ")
    Document findByWithoutConditions(@Param("operatorId") int operatorId);

    Optional<Document> findFirstByIdAndHashIdentifierAndActiveAndDeleted(int documentId, String hashIdentifier, byte active, byte deleted);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.workflowByWorkflowId.enterpriseId IN :enterpriseIds " +
            "AND d.workflowByWorkflowId.id IN :workflowIds " +
            "AND d.stateId IN :stateIds " +
            "AND d.deleted = 0 " +
            "ORDER BY d.createAt DESC ")
    Page<Document> findAllBy4Admins(@Param("enterpriseIds") List<Integer> enterpriseIds,
                                    @Param("workflowIds") List<Integer> workflowIds,
                                    @Param("stateIds") List<Integer> stateIds,
                                    Pageable pageable);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.id IN :identifiers " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 ")
    List<Document> findAllByIndetifiers(@Param("identifiers") List<Integer> identifiers);

    @Query("SELECT dr.documentByDocumentId " +
            "FROM Documentresource dr " +
            "WHERE dr.resourceByResourceId = :resource " +
            "AND dr.active = 1 " +
            "AND dr.deleted = 0 " +
            "AND dr.documentByDocumentId.active = 1 " +
            "AND dr.documentByDocumentId.deleted = 0 " +
            "AND dr.resourceByResourceId.active = 1 " +
            "AND dr.resourceByResourceId.deleted = 0 ")
    Optional<Document> findBy(@Param("resource") Resource resource);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.workflowByWorkflowId.enterpriseId IN :enterpriseIds " +
            "AND d.workflowByWorkflowId.id IN :workflowIds " +
            "AND d.stateId IN :stateIds " +
            "AND d.subject LIKE CONCAT('%',:term2Search, '%') " +
            "AND d.deleted = 0 " +
            "ORDER BY d.createAt DESC ")
    Page<Document> findAllBy4Admins(@Param("enterpriseIds") List<Integer> enterpriseIds,
                                    @Param("workflowIds") List<Integer> workflowIds,
                                    @Param("stateIds") List<Integer> stateIds,
                                    @Param("term2Search") String term2Search,
                                    Pageable pageable);

    @Query("SELECT distinct a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.documentByDocumentId.subject LIKE CONCAT('%',:term2Search, '%') " +
            "AND a.deleted = 0 " +
            "AND a.documentByDocumentId.workflowId IN :workflowIds " +
            "AND a.documentByDocumentId.stateId IN :stateIds " +
            "AND a.documentByDocumentId.deleted = 0 " +
            "ORDER BY a.documentByDocumentId.createAt DESC ")
    Page<Document> findAllBy4User(@Param("workflowIds") List<Integer> workflowIds,
                                  @Param("stateIds") List<Integer> stateIds,
                                  @Param("term2Search") String term2Search,
                                  Pageable pageable);

    @Query("SELECT a.documentByDocumentId " +
            "FROM Assigner a " +
            "WHERE a.operatorByOperatorId.personId = :personId " +
            "AND a.documentByDocumentId.subject LIKE CONCAT('%',:term2Search, '%') " +
            "AND a.documentByDocumentId.finished = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "AND a.documentByDocumentId.workflowId IN :workflowIds " +
            "AND a.documentByDocumentId.stateId IN :stateIds " +
            "AND a.documentByDocumentId.deleted = 0 " +
            "ORDER BY a.createAt DESC ")
    Page<Document> findPendingsBy4User(@Param("workflowIds") List<Integer> workflowIds,
                                       @Param("stateIds") List<Integer> stateIds,
                                       @Param("personId") int personId,
                                       @Param("term2Search") String term2Search,
                                       Pageable pageable);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.workflowByWorkflowId.enterpriseId = :enterpriseId " +
            "AND d.workflowByWorkflowId.id IN :workflowIds " +
            "AND d.personId IN :personIds " +
            "AND d.stateId IN :stateIds " +
            "AND d.deleted = 0 " +
            "ORDER BY d.createAt DESC ")
    Page<Document> findAllBy4Assistant(@Param("enterpriseId") int enterpriseId,
                                       @Param("workflowIds") List<Integer> workflowIds,
                                       @Param("personIds") List<Integer> personIds,
                                       @Param("stateIds") List<Integer> stateIds,
                                       Pageable pageable);

    @Query("SELECT d " +
            "FROM Document d " +
            "WHERE d.workflowByWorkflowId.enterpriseId = :enterpriseId " +
            "AND d.workflowByWorkflowId.id IN :workflowIds " +
            "AND d.personId IN :personIds " +
            "AND d.stateId IN :stateIds " +
            "AND d.subject LIKE CONCAT('%',:term2Search, '%') " +
            "AND d.deleted = 0 " +
            "ORDER BY d.createAt DESC ")
    Page<Document> findAllBy4Assistant(@Param("enterpriseId") int enterpriseId,
                                       @Param("workflowIds") List<Integer> workflowIds,
                                       @Param("personIds") List<Integer> personIds,
                                       @Param("stateIds") List<Integer> stateIds,
                                       @Param("term2Search") String term2Search,
                                       Pageable pageable);

    Optional<Document> findByIdAndHashIdentifierAndDeleted(int documentId, String hashIdentifier, byte deleted);
}
