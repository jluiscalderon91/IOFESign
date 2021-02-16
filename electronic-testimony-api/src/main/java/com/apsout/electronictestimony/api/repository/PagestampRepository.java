package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Pagestamp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PagestampRepository extends CrudRepository<Pagestamp, Integer> {

    @Query("SELECT ps " +
            "FROM Pagestamp ps " +
            "WHERE ps.active = 1 " +
            "AND ps.deleted = 0 " +
            "ORDER BY ps.order ASC ")
    List<Pagestamp> findAll();

    @Query("SELECT ps " +
            "FROM Pagestamp ps " +
            "WHERE ps.id = :pagestampId " +
            "AND ps.active = 1 " +
            "AND ps.deleted = 0 ")
    Optional<Pagestamp> findBy(@Param("pagestampId") int pagestampId);
}
