package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends CrudRepository<Resource, Integer> {

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN documentresource dr " +
            "ON r.id = dr.resourceId " +
            "INNER JOIN document d " +
            "ON d.id = dr.documentId " +
            "WHERE d.id = :documentId " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "ORDER BY r.orderResource ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Resource> getFirstByDocumentIdOrderAsc(@Param("documentId") int documentId);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN documentresource dr " +
            "ON r.id = dr.resourceId " +
            "INNER JOIN document d " +
            "ON d.id = dr.documentId " +
            "WHERE d.id = :documentId " +
            "AND r.orderResource =:orderResource " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "ORDER BY r.orderResource ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Resource> getFirstByDocumentIdOrderAsc(@Param("documentId") int documentId, @Param("orderResource") int orderResource);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN documentresource dr " +
            "ON r.id = dr.resourceId " +
            "INNER JOIN document d " +
            "ON dr.documentId = d.id " +
            "WHERE dr.documentId =:documentId " +
            "AND d.hashIdentifier =:hashIdentifier " +
            "AND r.orderResource =:orderResource " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.deleted = 0 " +
            "LIMIT 1", nativeQuery = true)
    Optional<Resource> getResourceByDocumentIdAndHashIdentifier(@Param("documentId") int documentId,
                                                                @Param("hashIdentifier") String hashIdentifier,
                                                                @Param("orderResource") int orderResource);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN documentresource dr " +
            "ON r.id = dr.resourceId " +
            "INNER JOIN document d " +
            "ON dr.documentId = d.id " +
            "WHERE dr.documentId =:documentId " +
            "AND d.hashIdentifier =:hashIdentifier " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.deleted = 0 " +
            "LIMIT 1", nativeQuery = true)
    Optional<Resource> getResourceByDocumentIdAndHashIdentifier(@Param("documentId") int documentId,
                                                                @Param("hashIdentifier") String hashIdentifier);

    @Query("SELECT dr.resourceByResourceId " +
            "FROM Documentresource dr " +
            "WHERE dr.documentId = :documentId " +
            "AND dr.active = 1 " +
            "AND dr.deleted = 0 " +
            "AND dr.documentByDocumentId.deleted = 0 " +
            "ORDER BY dr.resourceByResourceId.orderResource ASC")
    List<Resource> findAllByDocumentId(@Param("documentId") int documentId);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN documentresource dr " +
            "ON r.id = dr.resourceId " +
            "INNER JOIN document d " +
            "ON d.id = dr.documentId " +
            "WHERE d.id = :documentId " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.active = 1 " +
            "AND d.deleted = 0 " +
            "ORDER BY r.orderResource DESC " +
            "LIMIT 1", nativeQuery = true)
    Resource getFirstByDocumentIdOrderDesc(@Param("documentId") int documentId);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "INNER JOIN done do " +
            "ON do.resourceId = r.id " +
            "INNER JOIN assigner a " +
            "ON a.id = do.assignerId " +
            "INNER JOIN document d " +
            "ON d.id = a.documentId " +
            "WHERE d.id = :documentId " +
            "AND r.hash = :hashResource " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND d.deleted = 0 " +
            "ORDER BY r.orderResource DESC " +
            "LIMIT 1", nativeQuery = true)
    Resource getFirstByDocumentIdAndHashResourceOrderDesc(@Param("documentId") int documentId,
                                                          @Param("hashResource") String hashResource);

    @Query(value = "SELECT r.* " +
            "FROM resource r " +
            "WHERE r.resumeHash = :resumeHash " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "LIMIT 1", nativeQuery = true)
    Optional<Resource> findBy(@Param("resumeHash") String resumeHash);
}
