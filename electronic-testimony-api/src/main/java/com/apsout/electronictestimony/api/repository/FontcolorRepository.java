package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Fontcolor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FontcolorRepository extends CrudRepository<Fontcolor, Integer> {

    @Query("SELECT fc " +
            "FROM Fontcolor fc " +
            "WHERE fc.active = 1 " +
            "AND fc.deleted = 0 ")
    List<Fontcolor> findAll();

    @Query("SELECT fc " +
            "FROM Fontcolor fc " +
            "WHERE fc.id = :fontcolorId " +
            "AND fc.active = 1 " +
            "AND fc.deleted = 0 ")
    Optional<Fontcolor> findBy(@Param("fontcolorId") int fontcolorId);
}
