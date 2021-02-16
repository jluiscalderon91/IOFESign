package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Authenticationattempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthenticationattemptRepository extends CrudRepository<Authenticationattempt, Integer> {

    @Query(value = "SELECT aa.* " +
            "FROM authenticationattempt aa " +
            "INNER JOIN user u " +
            "ON aa.userId = u.id " +
            "WHERE aa.userId = :userId " +
            "AND aa.active = 1 " +
            "AND aa.deleted = 0 " +
            "AND u.active = 1 " +
            "AND u.deleted = 0 " +
            "ORDER BY 1 DESC " +
            "LIMIT :numRows ", nativeQuery = true)
    List<Authenticationattempt> findLastBy(@Param("userId") int userId, @Param("numRows") int numRows);
}
