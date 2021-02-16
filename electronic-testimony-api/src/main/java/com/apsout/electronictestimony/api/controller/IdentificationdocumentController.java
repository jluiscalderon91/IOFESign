package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Identificationdocument;
import com.apsout.electronictestimony.api.entity.model.IdentificationdocumentModel;
import com.apsout.electronictestimony.api.modelassembler.IdentificationdocumentModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.OutsideIdentificationdocumentModelAssembler;
import com.apsout.electronictestimony.api.service.IdentificationDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class IdentificationdocumentController {
    @Autowired
    private IdentificationDocumentService service;

    @PreAuthorize("hasAuthority('own:identificationdocument:get:all')")
    @GetMapping(value = "/identificationdocuments")
    public ResponseEntity<CollectionModel<IdentificationdocumentModel>> getAll() {
        List<Identificationdocument> identificationdocuments = service.findAll();
        IdentificationdocumentModelAssembler assembler = new IdentificationdocumentModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(identificationdocuments));
    }

    @PreAuthorize("hasAuthority('outside:identificationdocument:get:all')")
    @GetMapping(value = "/outside/identificationdocuments")
    public ResponseEntity<CollectionModel<IdentificationdocumentModel>> getAll2() {
        List<Identificationdocument> identificationdocuments = service.findAll();
        OutsideIdentificationdocumentModelAssembler assembler = new OutsideIdentificationdocumentModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(identificationdocuments));
    }
}
