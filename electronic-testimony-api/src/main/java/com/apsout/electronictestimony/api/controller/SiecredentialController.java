package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.entity.model.SiecredentialModel;
import com.apsout.electronictestimony.api.modelassembler.SiecredentialModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.SiecredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class SiecredentialController {

    @Autowired
    private SiecredentialService siecredentialService;
    @Autowired
    private AccessResourceService accessResourceService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:sie:add:credential')")
    @PostMapping(value = "/siecredential")
    public ResponseEntity<SiecredentialModel> save(@RequestParam("enterpriseId") int enterpriseId,
                                                   @RequestParam("usernameSie") String username,
                                                   @RequestParam("passwordSie") String password,
                                                   @RequestParam("certificate") MultipartFile certificate,
                                                   @RequestParam("passwordCertificate") String passwordCertificate,
                                                   HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Siecredential siecredential = siecredentialService.save(enterpriseId, username, password, certificate, passwordCertificate);
        SiecredentialModelAssembler assembler = new SiecredentialModelAssembler();
        SiecredentialModel siecredentialModel = assembler.toModel(siecredential);
        return ResponseEntity.ok().body(siecredentialModel);
    }

    @PreAuthorize("hasAuthority('own:sie:get:credential:enterprise')")
    @GetMapping(value = "/enterprises/{enterpriseId}/siecredentials")
    public ResponseEntity<CollectionModel<SiecredentialModel>> getBy(@PathVariable int enterpriseId, Pageable pageable, HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Page<Siecredential> siecredentials = siecredentialService.getBy(enterpriseId, pageable);
        SiecredentialModelAssembler assembler = new SiecredentialModelAssembler();
        PagedModel<SiecredentialModel> pagedModel = resourcesAssembler.toModel(siecredentials, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

}
