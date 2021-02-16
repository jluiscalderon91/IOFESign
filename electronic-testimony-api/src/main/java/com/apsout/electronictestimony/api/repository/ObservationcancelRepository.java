package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ObservationcancelRepository extends CrudRepository<Observationcancel, Integer> {

    Optional<Observationcancel> findByDocumentByDocumentIdAndDeleted(Document document, byte deleted);
}
