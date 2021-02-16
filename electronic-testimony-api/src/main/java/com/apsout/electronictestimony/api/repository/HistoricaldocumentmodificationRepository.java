package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Historicaldocumentmodification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HistoricaldocumentmodificationRepository extends CrudRepository<Historicaldocumentmodification, Integer> {

    @Query(value = "SELECT h " +
            "FROM Historicaldocumentmodification h " +
            "WHERE h.documentmodificationByDocumentmodificationId.documentByDocumentIdOld = :document " +
            "AND h.active = true " +
            "AND h.deleted = false ")
    Optional<Historicaldocumentmodification> findBy(@Param("document") Document documentOld);
}
