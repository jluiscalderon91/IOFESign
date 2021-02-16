package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stationcounter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StationcounterRepository extends CrudRepository<Stationcounter, Integer> {

    @Query("SELECT sc " +
            "FROM Stationcounter sc " +
            "WHERE sc.id = :stationcounterId " +
            "AND sc.active = 1 " +
            "AND sc.deleted = 0 ")
    Optional<Stationcounter> findBy(@Param("stationcounterId") int stationcounterId);
}
