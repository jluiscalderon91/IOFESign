package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.entity.model.ResourceModel;
import com.apsout.electronictestimony.api.modelassembler.ResourceModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAuthority('own:resource:get:detail')")
    @GetMapping("/resources/{resourceId}")
    public ResponseEntity<ResourceModel> getBy(@PathVariable int resourceId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfResourceId(request, resourceId);
        Optional<Resource> resourceOptional = resourceService.finBy(resourceId);
        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            ResourceModelAssembler assembler = new ResourceModelAssembler();
            ResourceModel resourceModel = assembler.toModel(resource);
            return ResponseEntity.ok().body(resourceModel);
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    @PreAuthorize("hasAuthority('own:resource:get:detail:stream')")
    @GetMapping("/resources/{resourceId}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy(@PathVariable int resourceId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfResourceId(request, resourceId);
        org.springframework.core.io.Resource resource = resourceService.getStreamBy(resourceId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }

    @PreAuthorize("hasAuthority('own:resource:get:document:detail')")
    @GetMapping("/documents/{documentId}/resource")
    public ResponseEntity<ResourceModel> getResourceBy(@PathVariable int documentId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        Optional<Resource> resourceOptional = resourceService.getOriginalResourceBy(documentId);
        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            ResourceModelAssembler assembler = new ResourceModelAssembler();
            ResourceModel resourceModel = assembler.toModel(resource);
            return ResponseEntity.ok().body(resourceModel);
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    @PreAuthorize("hasAuthority('own:resource:get:one2one:stream')")
    @GetMapping("/documents/{documentId}/resource/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamResourceBy(@PathVariable int documentId,
                                                                                    HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        org.springframework.core.io.Resource resource = resourceService.getStreamResourceBy(documentId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }

    @PreAuthorize("hasAuthority('own:resource:get:batch:stream')")
    @PostMapping("/enterprises/{enterpriseId}/documents/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamGroupDocuments(@PathVariable int enterpriseId,
                                                                                        @RequestParam("docIdentifiers") String docIdentifiers,
                                                                                        HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        org.springframework.core.io.Resource resource = resourceService.getStreamBy(docIdentifiers);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }
}
