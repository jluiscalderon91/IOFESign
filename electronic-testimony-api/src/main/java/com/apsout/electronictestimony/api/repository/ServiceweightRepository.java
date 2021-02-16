package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Serviceweight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceweightRepository extends CrudRepository<Serviceweight, Integer> {

    @Query("SELECT sw " +
            "FROM Serviceweight sw " +
            "WHERE sw.active = 1 " +
            "AND sw.deleted = 0 ")
    List<Serviceweight> findAll();

    @Query("SELECT sw " +
            "FROM Serviceweight sw " +
            "WHERE sw.id = :serviceweightId " +
            "AND sw.active = 1 " +
            "AND sw.deleted = 0 ")
    Optional<Serviceweight> findBy(@Param("serviceweightId") int serviceweightId);
}
