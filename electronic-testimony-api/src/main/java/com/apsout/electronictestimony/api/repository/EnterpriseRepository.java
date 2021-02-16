package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends CrudRepository<Enterprise, Integer> {

    Page<Enterprise> findAllByPartnerIdAndActiveAndDeleted(int partnerId, byte active, byte deleted, Pageable pageable);

    Page<Enterprise> findAllByPartnerIdAndIsCustomerAndActiveAndDeleted(int partnerId, boolean isCustomer, byte active, byte deleted, Pageable pageable);

    @Query("SELECT e " +
            "FROM Enterprise e " +
            "WHERE e.partnerId = :partnerId " +
            "AND (e.name LIKE CONCAT('%',:term2Search, '%') " +
            "OR e.tradeName LIKE CONCAT('%',:term2Search, '%')" +
            "OR e.documentNumber LIKE CONCAT('%',:term2Search, '%')) " +
            "AND e.active = :active " +
            "AND e.deleted = :deleted " +
            "ORDER BY e.createAt DESC ")
    Page<Enterprise> findAllBy(@Param("partnerId") int partnerId,
                               @Param("term2Search") String term2Search,
                               @Param("active") byte active,
                               @Param("deleted") byte deleted,
                               Pageable pageable);

    @Query("SELECT e " +
            "FROM Enterprise e " +
            "WHERE e.partnerId = :partnerId " +
            "AND e.isCustomer = :isCustomer " +
            "AND (e.name LIKE CONCAT('%',:term2Search, '%') " +
            "OR e.tradeName LIKE CONCAT('%',:term2Search, '%')" +
            "OR e.documentNumber LIKE CONCAT('%',:term2Search, '%')) " +
            "AND e.active = :active " +
            "AND e.deleted = :deleted " +
            "ORDER BY e.createAt DESC ")
    Page<Enterprise> findAllBy(@Param("partnerId") int partnerId,
                               @Param("isCustomer") boolean isCustomer,
                               @Param("term2Search") String term2Search,
                               @Param("active") byte active,
                               @Param("deleted") byte deleted,
                               Pageable pageable);

    @Query(value = "SELECT e " +
            "FROM Enterprise e " +
            "WHERE e.documentNumber = :documentNumber " +
            "AND e.deleted = 0")
    Optional<Enterprise> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    List<Enterprise> findAllByActiveAndDeleted(byte active, byte deleted);

    List<Enterprise> findAllByDeletedOrderByExcludedDescNameAsc(byte deleted);

    @Query(value = "SELECT en.* " +
            "FROM enterprise en " +
            "WHERE en.id <> :enterpriseId " +
            "AND (en.documentNumber LIKE CONCAT('%',:nameOrDocnumber, '%') " +
            "OR en.name LIKE CONCAT('%',:nameOrDocnumber, '%')) " +
            "AND en.active = 1 " +
            "AND en.deleted = 0 ", nativeQuery = true)
    List<Enterprise> findByDistinctEnterpriseIdAndLikeNameOrDocumentnumber(@Param("enterpriseId") int enterpriseId,
                                                                           @Param("nameOrDocnumber") String nameOrDocnumber);

    List<Enterprise> findAllByPartnerIdAndActiveAndDeletedOrderByExcludedDescNameAsc(int partnerId, byte active, byte deleted);

    @Query(value = "SELECT e.* " +
            "FROM enterprise e " +
            "WHERE e.partnerId = :partnerId " +
            "AND e.isPartner = 1 " +
            "AND e.deleted = 0 " +
            "LIMIT 1 ", nativeQuery = true)
    Optional<Enterprise> findPartnerEnterpriseBy(int partnerId);

    @Query(value = "SELECT e " +
            "FROM Enterprise e " +
            "WHERE e.partnerByPartnerId = :partner " +
            "AND e.documentNumber = :documentNumber " +
            "AND e.deleted = 0")
    Optional<Enterprise> findBy(@Param("partner") Partner partner, @Param("documentNumber") String documentNumber);
}
