package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Detailbalanceallocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DetailbalancedallocationRepository extends CrudRepository<Detailbalanceallocation, Integer> {

    @Query(value = "SELECT dba " +
            "FROM Detailbalanceallocation dba " +
            "WHERE dba.headbalanceallocationByHeadbalanceallocationId.enterpriseId = :enterpriseId " +
            "AND dba.active = 1 " +
            "AND dba.deleted = 0 " +
            "AND dba.headbalanceallocationByHeadbalanceallocationId.active = 1 " +
            "AND dba.headbalanceallocationByHeadbalanceallocationId.deleted = 0 " +
            "ORDER BY dba.createAt DESC ")
    Page<Detailbalanceallocation> findAllBy(@Param("enterpriseId") int enterpriseId, Pageable pageable);
}
