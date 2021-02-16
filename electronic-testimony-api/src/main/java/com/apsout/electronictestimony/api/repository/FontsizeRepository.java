package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Fontsize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FontsizeRepository extends CrudRepository<Fontsize, Integer> {

    @Query("SELECT fs " +
            "FROM Fontsize fs " +
            "WHERE fs.active = 1 " +
            "AND fs.deleted = 0 ")
    List<Fontsize> findAll();

    @Query("SELECT fs " +
            "FROM Fontsize fs " +
            "WHERE fs.id = :fontsizeId " +
            "AND fs.active = 1 " +
            "AND fs.deleted = 0 ")
    Optional<Fontsize> findBy(@Param("fontsizeId") int fontsizeId);
}
