package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Balancepurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BalancepurchaseRepository extends PagingAndSortingRepository<Balancepurchase, Integer> {

    @Query(value = "SELECT bp " +
            "FROM Balancepurchase bp " +
            "WHERE bp.enterpriseByEnterpriseId.partnerId = :partnerId " +
            "AND bp.createAt >= :from1 " +
            "AND bp.createAt <= :to " +
            "AND bp.active = 1 " +
            "AND bp.deleted = 0 " +
            "ORDER BY bp.createAt ASC ")
    Page<Balancepurchase> findBy21(@Param("partnerId") int partnerId, Pageable pageable);

    @Query(value = "SELECT bp.* " +
            "FROM balancepurchase bp " +
            "INNER JOIN enterprise e on e.id = bp.enterpriseId " +
            "WHERE e.partnerId =:partnerId " +
            "AND date(bp.createAt) >= :from1 " +
            "AND date(bp.createAt) <= :to " +
            "AND bp.active = 1 " +
            "AND bp.deleted = 0 " +
            "ORDER BY bp.createAt ASC ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM balancepurchase bp " +
                    "INNER JOIN enterprise e on e.id = bp.enterpriseId " +
                    "WHERE e.partnerId =:partnerId " +
                    "AND date(bp.createAt) >= :from1 " +
                    "AND date(bp.createAt) <= :to " +
                    "AND bp.active = 1 " +
                    "AND bp.deleted = 0 " +
                    "ORDER BY bp.createAt ASC ", nativeQuery = true)
    Page<Balancepurchase> findBy(@Param("partnerId") int partnerId,
                                  @Param("from1") String from,
                                  @Param("to") String to,
                                  Pageable pageable);
}
