package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DocumentmodificationRepository extends CrudRepository<Documentmodification, Integer> {

    @Query("SELECT hdm.documentmodificationByDocumentmodificationId " +
            "FROM Historicaldocumentmodification hdm " +
            "WHERE hdm.documentmodificationByDocumentmodificationId.documentByDocumentIdOld = :document " +
            "AND hdm.active = true " +
            "AND hdm.deleted = false ")
    Optional<Documentmodification> findAsociatedHistoricalBy(@Param("document") Document document);

    @Query("SELECT hdm.documentmodificationByDocumentmodificationId " +
            "FROM Historicaldocumentmodification hdm " +
            "WHERE hdm.createAt >= :historicalCreationDatetime " +
            "AND hdm.active = true " +
            "AND hdm.deleted = false ")
    List<Documentmodification> findHistoricalGreaterThan(@Param("historicalCreationDatetime") Timestamp historicalCreationDatetime);

//    @Query(value = "SELECT dm.* " +
//            "FROM documentmodification dm " +
//            "INNER JOIN historicaldocumentmodification hdm " +
//            "ON hdm.documentmodificationId = dm.id " +
//            "WHERE hdm.createAt >= :historicalCreationDatetime " +
//            "AND hdm.active = true " +
//            "AND hdm.deleted = false ", nativeQuery = true)
//    List<Documentmodification> findHistoricalGreaterThan(@Param("historicalCreationDatetime") Timestamp historicalCreationDatetime);
}
