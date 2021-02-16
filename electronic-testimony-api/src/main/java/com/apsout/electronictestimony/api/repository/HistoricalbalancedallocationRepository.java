package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoricalbalancedallocationRepository extends CrudRepository<Historicalbalanceallocation, Integer> {

    @Query(value = "SELECT hba " +
            "FROM Historicalbalanceallocation hba " +
            "WHERE hba.headbalanceallocationByHeadbalanceallocationId.enterpriseId = :enterpriseId " +
            "AND hba.active = 1 " +
            "AND hba.deleted = 0 " +
            "ORDER BY hba.createAt DESC ")
    List<Historicalbalanceallocation> findAllBy(@Param("enterpriseId") int enterpriseId);

    @Query(value = "SELECT hba " +
            "FROM Historicalbalanceallocation hba " +
            "WHERE hba.headbalanceallocationByHeadbalanceallocationId.enterpriseId = :enterpriseId " +
            "AND hba.active = 1 " +
            "AND hba.deleted = 0 " +
            "ORDER BY hba.createAt DESC ")
    Page<Historicalbalanceallocation> findAllBy(@Param("enterpriseId") int enterpriseId, Pageable pageable);
}
