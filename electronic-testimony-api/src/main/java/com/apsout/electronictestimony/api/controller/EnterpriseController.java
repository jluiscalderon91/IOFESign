package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.model.EnterpriseModel;
import com.apsout.electronictestimony.api.modelassembler.EnterpriseModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.EnterpriseService;
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
import java.util.List;

// TODO review the way to improvement CrossOrigin operation
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccessResourceService accessResourceService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:enterprise:add')")
    @PostMapping(value = "/partners/{partnerId}/enterprises")
    public ResponseEntity<EnterpriseModel> save(@PathVariable int partnerId, @RequestBody Enterprise enterprise, HttpServletRequest request) {
        enterpriseService.register(partnerId, enterprise, request);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        EnterpriseModel enterpriseModel = assembler.toModel(enterprise);
        return ResponseEntity.ok().body(enterpriseModel);
    }

    @PreAuthorize("hasAuthority('own:enterprise:get:detail')")
    @GetMapping(value = "/enterprises/{enterpriseId}")
    public ResponseEntity<EnterpriseModel> getBy(@PathVariable int enterpriseId) {
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        EnterpriseModel enterpriseModel = assembler.toModel(enterprise);
        return ResponseEntity.ok().body(enterpriseModel);
    }

    @PreAuthorize("hasAuthority('own:enterprise:edit')")
    @PutMapping(value = "/enterprises")
    public ResponseEntity<EnterpriseModel> update(@RequestBody Enterprise enterprise, HttpServletRequest request) {
        accessResourceService.validateIfPersonRequestIsAuthorizedAdmin(request, enterprise.getPartnerId());
        enterpriseService.update(enterprise, request);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        EnterpriseModel enterpriseModel = assembler.toModel(enterprise);
        return ResponseEntity.ok().body(enterpriseModel);
    }

    @PreAuthorize("hasAuthority('own:enterprise:delete')")
    @DeleteMapping(value = "/enterprises/{enterpriseId}")
    public ResponseEntity<EnterpriseModel> delete(@PathVariable int enterpriseId, HttpServletRequest request) {
        accessResourceService.validateIfPersonRequestBelong2PartnerOfEnterprise(request, enterpriseId);
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        enterpriseService.delete(enterprise);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        EnterpriseModel enterpriseModel = assembler.toModel(enterprise);
        return ResponseEntity.ok().body(enterpriseModel);
    }

    @PreAuthorize("hasAuthority('own:enterprise:get:distinct')")
    @GetMapping(value = "/participants/{participantType}/enterprises/{enterpriseId}/distinct")
    public ResponseEntity<CollectionModel<EnterpriseModel>> getAllBy(@PathVariable int participantType,
                                                                     @PathVariable int enterpriseId,
                                                                     @RequestParam("findby") String nameOrDocnumber,
                                                                     HttpServletRequest request) {
        List<Enterprise> enterprises = enterpriseService.getAllBy(enterpriseId, participantType, nameOrDocnumber, request);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(enterprises));
    }

    @PreAuthorize("hasAnyAuthority('own:enterprise:get:page')")
    @GetMapping(value = "/partners/{partnerId}/enterprises")
    public ResponseEntity<CollectionModel<EnterpriseModel>> getAllBy(@PathVariable int partnerId,
                                                                     @RequestParam("onlycustomers") String onlyCustomers,
                                                                     @RequestParam("term2Search") String term2Search,
                                                                     Pageable pageable,
                                                                     HttpServletRequest request) {
        accessResourceService.validateIfPersonRequestIsAuthorizedAdmin(request, partnerId);
        Page<Enterprise> enterprisePage = enterpriseService.getAllBy(partnerId, onlyCustomers, term2Search, pageable);
        EnterpriseModelAssembler assembler = new EnterpriseModelAssembler();
        PagedModel<EnterpriseModel> pagedModel = resourcesAssembler.toModel(enterprisePage, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }
}
