package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Numbersignature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NumbersignatureRepository extends CrudRepository<Numbersignature, Integer> {

    @Query("SELECT ns " +
            "FROM Numbersignature ns " +
            "WHERE ns.documentByDocumentId = :document " +
            "AND ns.hashResource = :hashResource " +
            "AND ns.active = 1 " +
            "AND ns.deleted = 0 ")
    Optional<Numbersignature> findBy(@Param("document") Document document, @Param("hashResource") String hashResource);
}
