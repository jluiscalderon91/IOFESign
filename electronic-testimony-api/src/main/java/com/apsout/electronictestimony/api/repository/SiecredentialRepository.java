package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SiecredentialRepository extends PagingAndSortingRepository<Siecredential, Integer> {

    @Modifying
    @Query(value = "UPDATE Siecredential sc " +
            "SET sc.active = 0 " +
            "WHERE sc.enterpriseId =:enterpriseId")
    int disableAllBy(@Param("enterpriseId") int enterpriseId);

    Optional<Siecredential> findFirstByEnterpriseByEnterpriseIdAndActiveAndDeletedOrderByIdDesc(Enterprise enterprise, byte active, byte deleted);

    @Query(value = "SELECT sc " +
            "FROM Siecredential sc " +
            "WHERE sc.enterpriseId IN :enterpriseIds " +
            "AND sc.deleted = 0 " +
            "ORDER BY sc.enterpriseByEnterpriseId.id ASC, sc.createAt DESC")
    Page<Siecredential> findBy(List<Integer> enterpriseIds, Pageable pageable);
}
