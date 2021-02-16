package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository extends PagingAndSortingRepository<Operator, Integer> {
    Page<Operator> findAllByEnterpriseIdAndDeleted(int enterpriseId, byte deleted, Pageable pageable);

    @Query(value = "SELECT MAX(o.orderOperation) " +
            "FROM operator o " +
            "WHERE o.enterpriseId = :enterpriseId " +
            "AND o.active=1 " +
            "AND o.deleted=0 ", nativeQuery = true)
    Optional<Integer> getMaxOrderOperationBy(@Param("enterpriseId") int enterpriseId);

    @Query(value = "SELECT o " +
            "FROM Operator o " +
            "WHERE o.personId = :personId " +
            "AND o.deleted = 0 ")
    Optional<Operator> findBy(@Param("personId") int personId);

    @Query(value = "SELECT o.* " +
            "FROM operator o " +
            "INNER JOIN enterprise e " +
            "ON o.enterpriseId = e.id " +
            "INNER JOIN  document dc " +
            "ON o.documentId = dc.id " +
            "WHERE o.enterpriseId =:enterpriseId " +
            "AND o.documentId = :documentId " +
            "AND o.active = 1 " +
            "AND e.active = 1 " +
            "AND o.deleted = 0 " +
            "AND e.deleted = 0 " +
            "ORDER BY o.orderOperation ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Operator> findFirstByEnterpriseIdAsc(@Param("enterpriseId") int enterpriseId, @Param("documentId") int documentId);

    @Query(value = "SELECT o.* " +
            "FROM operator o " +
            "INNER JOIN enterprise e " +
            "ON o.enterpriseId = e.id " +
            "WHERE o.enterpriseId =:enterpriseId " +
            "AND o.documentId = :documentId " +
            "AND o.orderOperation > :orderOperation " +
            "AND o.active = 1 " +
            "AND e.active = 1 " +
            "AND o.deleted = 0 " +
            "AND e.deleted = 0 " +
            "ORDER BY o.orderOperation ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Operator> findFirstByEnterpriseIdAndMajorOfOrderOperation(@Param("enterpriseId") int enterpriseId,
                                                                       @Param("documentId") int documentId,
                                                                       @Param("orderOperation") int orderOperation);

    @Query(value = "SELECT o.* " +
            "FROM operator o " +
            "WHERE o.documentId = :documentId " +
            "AND o.orderOperation = :orderOperation " +
            "AND o.active = 1 " +
            "AND o.deleted = 0 " +
            "ORDER BY o.orderOperation ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Operator> findByDocumentIdAndOrderOperation(@Param("documentId") int documentId,
                                                         @Param("orderOperation") int orderOperation);

    Optional<Operator> findByIdAndActiveAndDeleted(int operatorId, byte active, byte deleted);

    @Query(value = "SELECT COUNT(o.id) " +
            "FROM operator o " +
            "WHERE o.enterpriseId = :enterpriseId " +
            "AND o.active = 1 " +
            "AND o.deleted = 0 ", nativeQuery = true)
    Optional<Integer> getCountOperatorsBy(@Param("enterpriseId") int enterpriseId);


    @Query(value = "SELECT o " +
            "FROM Operator o " +
            "WHERE o.personId = :personId " +
            "AND o.documentId = :documentId " +
            "AND o.deleted = 0 ")
    Optional<Operator> findBy(@Param("personId") int personId, @Param("documentId") int documentId);

    @Query(value = "SELECT o " +
            "FROM Operator o " +
            "WHERE o.documentByDocumentId =:document " +
            "AND o.active = 1 " +
            "AND o.deleted = 0 ")
    List<Operator> getAllBy(@Param("document") Document document);
}
