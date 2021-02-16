package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Profileupdateattempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileupdateattemptRepository extends CrudRepository<Profileupdateattempt, Integer> {

    @Query(value = "SELECT pua.* " +
            "FROM profileupdateattempt pua " +
            "INNER JOIN person p " +
            "ON pua.personId = p.id " +
            "WHERE pua.personId = :personId " +
            "AND pua.active = 1 " +
            "AND pua.deleted = 0 " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 " +
            "ORDER BY 1 DESC " +
            "LIMIT 2 ", nativeQuery = true)
    List<Profileupdateattempt> findLastTwoBy(@Param("personId") int personId);
}
