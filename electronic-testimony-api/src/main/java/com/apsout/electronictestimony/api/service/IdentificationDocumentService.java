package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Identificationdocument;

import java.util.List;

public interface IdentificationDocumentService {

    List<Identificationdocument> findInitialAll();

    List<Identificationdocument> findAll();
}
