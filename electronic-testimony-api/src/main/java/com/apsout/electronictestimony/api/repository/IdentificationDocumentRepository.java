package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Identificationdocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IdentificationDocumentRepository extends CrudRepository<Identificationdocument, Integer> {

    @Query("SELECT idc " +
            "FROM Identificationdocument idc " +
            "WHERE idc.active = 1 " +
            "AND idc.deleted = 0 ")
    List<Identificationdocument> findAll();
}
