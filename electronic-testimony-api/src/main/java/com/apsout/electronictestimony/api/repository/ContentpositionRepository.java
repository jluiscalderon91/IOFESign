package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Contentposition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentpositionRepository extends CrudRepository<Contentposition, Integer> {

    @Query("SELECT cp " +
            "FROM Contentposition cp " +
            "WHERE cp.active = 1 " +
            "AND cp.deleted = 0 ")
    List<Contentposition> findAll();

    @Query("SELECT cp " +
            "FROM Contentposition cp " +
            "WHERE cp.id = :contentpositionId " +
            "AND cp.active = 1 " +
            "AND cp.deleted = 0 ")
    Optional<Contentposition> findBy(@Param("contentpositionId") int contentpositionId);
}
