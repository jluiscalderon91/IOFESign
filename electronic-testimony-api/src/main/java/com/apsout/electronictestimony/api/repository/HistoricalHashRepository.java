package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Historicalhash;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HistoricalHashRepository extends CrudRepository<Historicalhash, Integer> {

    @Query(value = "SELECT h " +
            "FROM Historicalhash h " +
            "WHERE h.hashIdentifier = :hashIdentifier " +
            "AND h.active = 1 " +
            "AND h.deleted = 0")
    Optional<Historicalhash> findBy(@Param("hashIdentifier") String hashIdentifier);
}
