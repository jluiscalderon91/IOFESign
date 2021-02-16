package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Temporarysession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporarysessionRepository extends CrudRepository<Temporarysession, Integer> {

    @Query("SELECT ts " +
            "FROM Temporarysession ts " +
            "WHERE ts.personId = :personId " +
            "AND ts.uuid = :uuid " +
            "AND ts.active = true " +
            "AND ts.deleted = false ")
    Optional<Temporarysession> findBy(int personId, String uuid);
}
