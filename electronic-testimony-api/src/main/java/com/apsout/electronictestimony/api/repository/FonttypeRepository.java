package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Fonttype;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FonttypeRepository extends CrudRepository<Fonttype, Integer> {

    @Query("SELECT ft " +
            "FROM Fonttype ft " +
            "WHERE ft.active = 1 " +
            "AND ft.deleted = 0 ")
    List<Fonttype> findAll();

    @Query("SELECT ft " +
            "FROM Fonttype ft " +
            "WHERE ft.id = :fonttypeId " +
            "AND ft.active = 1 " +
            "AND ft.deleted = 0 ")
    Optional<Fonttype> findBy(@Param("fonttypeId") int fonttypeId);
}
