package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Documenttraceability;
import com.apsout.electronictestimony.api.entity.model.DocumenttraceabilityModel;
import com.apsout.electronictestimony.api.modelassembler.DocumenttraceabilityModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.DocumenttraceabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class DocumenttraceabilityController {
    @Autowired
    private DocumenttraceabilityService traceability;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAnyAuthority('own:traceability:get:document')")
    @GetMapping(value = "/documents/{documentId}/traceability")
    public ResponseEntity<CollectionModel<DocumenttraceabilityModel>> getAllBy(@PathVariable int documentId,
                                                                               @RequestParam String types,
                                                                               HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentIdOperators(request, documentId);
        List<Documenttraceability> documents = traceability.findAllBy(documentId, types);
        DocumenttraceabilityModelAssembler assembler = new DocumenttraceabilityModelAssembler();
        CollectionModel<DocumenttraceabilityModel> collectionModel = assembler.toCollectionModel(documents);
        return ResponseEntity.ok().body(collectionModel);
    }
}
