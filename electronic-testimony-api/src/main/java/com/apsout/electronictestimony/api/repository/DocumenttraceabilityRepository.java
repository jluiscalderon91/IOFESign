package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Documenttraceability;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumenttraceabilityRepository extends CrudRepository<Documenttraceability, Integer> {

    @Query("SELECT dt " +
            "FROM Documenttraceability dt " +
            "WHERE dt.documentId = :documentId " +
            "AND dt.type IN :types " +
            "AND dt.active = 1 " +
            "AND dt.deleted = 0 ")
    List<Documenttraceability> findAllByDocumentIdAndVisible(int documentId, List<Integer> types);
}
