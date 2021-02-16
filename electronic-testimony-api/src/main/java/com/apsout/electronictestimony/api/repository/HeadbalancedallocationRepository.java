package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HeadbalancedallocationRepository extends CrudRepository<Headbalanceallocation, Integer> {

    @Query(value = "SELECT hba.* " +
            "FROM headbalanceallocation hba " +
            "WHERE hba.enterpriseId =:enterpriseId " +
            "AND hba.active = 1 " +
            "AND hba.deleted = 0 " +
            "ORDER BY hba.createAt DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Headbalanceallocation> findFirstAvailableBy(int enterpriseId);
}
