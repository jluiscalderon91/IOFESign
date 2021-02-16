package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends CrudRepository<Module, Integer> {

    @Query("SELECT m " +
            "FROM Module m " +
            "WHERE m.active = 1 " +
            "AND m.deleted = 0 ")
    List<Module> findAll();

    @Query("SELECT m " +
            "FROM Module m " +
            "WHERE m.id = :moduleId " +
            "AND m.active = 1 " +
            "AND m.deleted = 0 ")
    Optional<Module> findBy(@Param("moduleId") int moduleId);
}
