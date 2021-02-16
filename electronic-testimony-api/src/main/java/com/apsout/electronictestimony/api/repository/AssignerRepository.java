package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssignerRepository extends CrudRepository<Assigner, Integer> {

    @Query(value = "SELECT a.* " +
            "FROM assigner a " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "WHERE o.enterpriseId =:enterpriseId " +
            "AND a.documentId =:documentId " +
            "AND a.active = 1 " +
            "AND o.active = 1 " +
            "AND a.deleted = 0 " +
            "AND o.deleted = 0 " +
            "ORDER BY a.orderOperation DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Assigner> findFirstByAndOrderOperationDesc(int enterpriseId, int documentId);

    @Query(value = "SELECT a.* " +
            "FROM assigner a " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "WHERE o.personId = :personId " +
            "AND a.documentId =:documentId " +
            "AND a.active = 1 " +
            "AND o.active = 1 " +
            "AND a.deleted = 0 " +
            "AND o.deleted = 0 " +
            "ORDER BY a.orderOperation DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Assigner> findByPersonIdAndDocumentId(int personId, int documentId);

    @Query(value = "SELECT a.* " +
            "FROM assigner a " +
            "WHERE a.documentId =:documentId " +
            "AND a.completed = 1 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "ORDER BY a.orderOperation DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Assigner> findFirstByAndCompletedAndOrderOperationDesc(int documentId);

    @Query("SELECT a " +
            "FROM Assigner a " +
            "WHERE a.documentId IN :identifiers " +
            "AND a.operatorByOperatorId.personId = :personId " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 ")
    List<Assigner> findByIdentifiersAndPersonId(@Param("personId") int personId, @Param("identifiers") List<Integer> identifiers);

    @Query(value = "SELECT a.* " +
            "FROM assigner a " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "INNER JOIN document d " +
            "ON a.documentId = d.id " +
            "WHERE o.personId = :personId " +
            "AND  d.hashIdentifier = :hashIdentifier " +
            "AND a.active = 1 " +
            "AND o.active = 1 " +
            "AND a.deleted = 0 " +
            "AND o.deleted = 0 " +
            "ORDER BY a.orderOperation DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Assigner> findByPersonIdAndHashIdentifier(int personId, String hashIdentifier);

    @Query(value = "SELECT a.* " +
            "FROM assigner a " +
            "WHERE a.documentId =:documentId " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "ORDER BY a.orderOperation ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Assigner> findFirstByAndOrderOperationAsc(int documentId);

    @Query(value = "SELECT a " +
            "FROM Assigner a " +
            "WHERE a.documentByDocumentId =:document " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 ")
    List<Assigner> getAllBy(@Param("document") Document document);

    @Query(value = "SELECT a " +
            "FROM Assigner a " +
            "WHERE a.operatorId =:operatorId " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "AND a.operatorByOperatorId.active = 1 " +
            "AND a.operatorByOperatorId.deleted = 0 ")
    Optional<Assigner> findBy(@Param("operatorId") int operatorId);
}
