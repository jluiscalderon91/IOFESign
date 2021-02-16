package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Done;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoneRepository extends CrudRepository<Done, Integer> {

    @Query("SELECT d " +
            "FROM Done d " +
            "WHERE d.assignerByAssignerId.operatorId = :operatorId " +
            "AND d.assignerByAssignerId.documentId = :documentId " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "AND d.assignerByAssignerId.active = 1 " +
            "AND d.assignerByAssignerId.deleted = 0 ")
    Optional<Done> findAllByOperatorIdAndDocumentId(@Param("operatorId") int operatorId, @Param("documentId") int documentId);

    // TODO REVIEW -> findByAssignerIdAndOrderResource
    Optional<Done> findByAssignerIdAndActiveAndDeleted(int assignerId, byte active, byte deleted);

    @Query(value = "SELECT do.* " +
            "FROM done do " +
            "INNER JOIN assigner a " +
            "ON a.id = do.assignerId " +
            "INNER JOIN resource r " +
            "ON r.id = do.resourceId " +
            "WHERE a.id =:assignerId " +
            "AND r.hash = :hashResource " +
            "AND do.active = 1 " +
            "AND do.deleted = 0 " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 ", nativeQuery = true)
    Optional<Done> findByAssignerIdAndHashResource(int assignerId, String hashResource);

    @Query("SELECT d " +
            "FROM Done d " +
            "WHERE d.assignerByAssignerId.id =:assignerId " +
            "AND d.resourceByResourceId.orderResource =:orderResource " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "AND d.assignerByAssignerId.active = 1 " +
            "AND d.assignerByAssignerId.deleted = 0 " +
            "AND d.resourceByResourceId.active = 1 " +
            "AND d.resourceByResourceId.deleted = 0")
    Optional<Done> findByAssignerIdAndOrderResource(int assignerId, int orderResource);

    @Query(value = "SELECT do.* " +
            "FROM done do " +
            "INNER JOIN assigner a " +
            "ON do.assignerId = a.id " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "INNER JOIN operation op " +
            "ON o.operationId = op.id " +
            "INNER JOIN document d " +
            "ON a.documentId = d.id " +
            "WHERE a.documentId =:documentId " +
            "AND o.operationId IN :operationIds " +
            "AND do.active = 1 " +
            "AND d.finished = 1 " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "ORDER BY do.createAt ASC ", nativeQuery = true)
    List<Done> findByFinishedDocument(int documentId, List<Integer> operationIds);

    @Query(value = "SELECT do.* " +
            "FROM done do " +
            "INNER JOIN assigner a " +
            "ON do.assignerId = a.id " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "INNER JOIN operation op " +
            "ON o.operationId = op.id " +
            "INNER JOIN document d " +
            "ON a.documentId = d.id " +
            "WHERE a.documentId =:documentId " +
            "AND o.operationId = :operationId " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "ORDER BY do.createAt ASC ", nativeQuery = true)
    List<Done> findBy(int documentId, int operationId);

    @Query(value = "SELECT do.* " +
            "FROM done do " +
            "INNER JOIN assigner a " +
            "ON do.assignerId = a.id " +
            "INNER JOIN operator o " +
            "ON a.operatorId = o.id " +
            "INNER JOIN document d " +
            "ON a.documentId = d.id " +
            "WHERE a.documentId =:documentId " +
            "AND do.active = 1 " +
            "AND do.deleted = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "AND d.deleted = 0 " +
            "AND o.operationId = 2 " +
            "ORDER BY do.createAt DESC ", nativeQuery = true)
    List<Done> findAllByDocumentId(@Param("documentId") int documentId);

    @Query(value = "SELECT do.* " +
            "FROM done do " +
            "INNER JOIN assigner a " +
            "ON a.id = do.assignerId " +
            "WHERE a.id =:assignerId " +
            "AND do.active = 1 " +
            "AND do.deleted = 0 " +
            "AND a.active = 1 " +
            "AND a.deleted = 0 " +
            "LIMIT 1 ", nativeQuery = true)
    Optional<Done> findUniqueBy(int assignerId);
}
