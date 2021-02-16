package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Stationcounter;
import com.apsout.electronictestimony.api.entity.model.StationcounterModel;
import com.apsout.electronictestimony.api.modelassembler.StationcounterModelAssembler;
import com.apsout.electronictestimony.api.service.StationcounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class StationcounterController {
    private static final Logger logger = LoggerFactory.getLogger(StationcounterController.class);

    @Autowired
    private StationcounterService stationcounterService;

    @PreAuthorize("hasAuthority('outside:signnotification:add:quantity:notification') and hasAnyAuthority('outside:signnotification:add:quantity:sign')")
    @PostMapping(value = "/outside/stationcounter")
    public ResponseEntity<StationcounterModel> save(@RequestBody Stationcounter stationcounter, HttpServletRequest request) {
        Stationcounter stationcounter1 = stationcounterService.save(stationcounter, request);
        StationcounterModelAssembler assembler = new StationcounterModelAssembler();
        StationcounterModel stationcounterModel = assembler.toModel(stationcounter1);
        return ResponseEntity.ok().body(stationcounterModel);
    }

    @PreAuthorize("hasAuthority('outside:signnotification:edit:quantity:notification') and hasAnyAuthority('outside:signnotification:edit:quantity:sign')")
    @PutMapping(value = "/outside/stationcounter")
    public ResponseEntity<StationcounterModel> edit(@RequestBody Stationcounter stationcounter) {
        stationcounterService.update(stationcounter);
        StationcounterModelAssembler assembler = new StationcounterModelAssembler();
        StationcounterModel stationcounterModel = assembler.toModel(stationcounter);
        return ResponseEntity.ok().body(stationcounterModel);
    }
}
