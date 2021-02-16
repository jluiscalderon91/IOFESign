package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Balancepurchase;
import com.apsout.electronictestimony.api.entity.model.BalancepurchaseModel;
import com.apsout.electronictestimony.api.modelassembler.BalancepurchaseModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.BalancepurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class BalancepurchaseController {

    @Autowired
    private BalancepurchaseService balancepurchaseService;
    @Autowired
    private AccessResourceService accessResourceService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("true")
    @GetMapping(value = "/partners/{partnerId}/balancepurchases")
    public ResponseEntity<CollectionModel<BalancepurchaseModel>> getBy(@PathVariable int partnerId,
                                                                       @RequestParam String from,
                                                                       @RequestParam String to,
                                                                       Pageable pageable,
                                                                       HttpServletRequest request) {
        accessResourceService.validateIfPersonRequestIsAuthorizedPartner(request, partnerId);
        Page<Balancepurchase> balancepurchases = balancepurchaseService.findBy(partnerId, from, to, pageable);
        BalancepurchaseModelAssembler assembler = new BalancepurchaseModelAssembler();
        PagedModel<BalancepurchaseModel> pagedModel = resourcesAssembler.toModel(balancepurchases, assembler);
        return ResponseEntity.ok(pagedModel);
    }
}
