package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Persontype;
import com.apsout.electronictestimony.api.entity.model.PersontypeModel;
import com.apsout.electronictestimony.api.modelassembler.OutsidePersontypeModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.PersontypeModelAssembler;
import com.apsout.electronictestimony.api.service.PersontypeService;
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
public class PersontypeController {
    @Autowired
    private PersontypeService persontypeService;

    @PreAuthorize("hasAuthority('own:persontype:get:all')")
    @GetMapping(value = "/persontypes")
    public ResponseEntity<CollectionModel<PersontypeModel>> getAll() {
        List<Persontype> persontypes = persontypeService.findAll();
        PersontypeModelAssembler assembler = new PersontypeModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(persontypes));
    }

    @PreAuthorize("hasAuthority('outside:persontype:get:all')")
    @GetMapping(value = "/outside/persontypes")
    public ResponseEntity<CollectionModel<PersontypeModel>> getAll2() {
        List<Persontype> persontypes = persontypeService.findAll();
        OutsidePersontypeModelAssembler assembler = new OutsidePersontypeModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(persontypes));
    }
}
