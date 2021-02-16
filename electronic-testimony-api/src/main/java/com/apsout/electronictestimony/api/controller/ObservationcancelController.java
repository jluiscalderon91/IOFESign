package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.model.ObservationcancelModel;
import com.apsout.electronictestimony.api.modelassembler.ObservationcancelModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.ObservationcancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class ObservationcancelController {
    @Autowired
    private ObservationcancelService observationcancelService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAuthority('own:document:get:cancel:observation')")
    @GetMapping(value = "/documents/{documentId}/obscancel")
    public ResponseEntity<ObservationcancelModel> getObsCancel(@PathVariable int documentId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentIdOperators(request, documentId);
        Optional<Observationcancel> optional = observationcancelService.loadBy(documentId);
        if (optional.isPresent()) {
            Observationcancel observationcancel = optional.get();
            ObservationcancelModelAssembler assembler = new ObservationcancelModelAssembler();
            ObservationcancelModel observationcancelModel = assembler.toModel(observationcancel);
            return ResponseEntity.ok().body(observationcancelModel);
        } else {
            return ResponseEntity.ok().body(null);
        }
    }
}
