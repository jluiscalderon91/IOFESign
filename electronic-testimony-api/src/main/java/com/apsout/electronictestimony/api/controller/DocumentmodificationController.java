package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.model.DocumentmodificationModel;
import com.apsout.electronictestimony.api.modelassembler.DocumentmodificationModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.DocumentmodificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class DocumentmodificationController {
    @Autowired
    private DocumentmodificationService documentmodificationService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("true")
    @GetMapping(value = "/documents/{documentId}/obsmodification")
    public ResponseEntity<DocumentmodificationModel> getObsModification(@PathVariable int documentId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentIdOperators(request, documentId);
        Optional<Documentmodification> optional = documentmodificationService.loadBy(documentId);
        if (optional.isPresent()) {
            Documentmodification documentmodification = optional.get();
            DocumentmodificationModelAssembler assembler = new DocumentmodificationModelAssembler();
            DocumentmodificationModel observationcancelModel = assembler.toModel(documentmodification);
            return ResponseEntity.ok().body(observationcancelModel);
        } else {
            return ResponseEntity.ok().body(null);
        }
    }
}
