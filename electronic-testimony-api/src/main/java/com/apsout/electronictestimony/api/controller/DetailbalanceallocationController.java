package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Detailbalanceallocation;
import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.DetailbalanceallocationModel;
import com.apsout.electronictestimony.api.entity.model.HeadbalanceallocationModel;
import com.apsout.electronictestimony.api.modelassembler.DetailbalanceallocationModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.HeadbalanceallocationModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.DetailbalanceallocationService;
import com.apsout.electronictestimony.api.service.HeadbalanceallocationService;
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
public class DetailbalanceallocationController {

    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private AccessResourceService accessResourceService;
    @Autowired
    private DetailbalanceallocationService detailbalanceallocationService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("true")
    @PostMapping(value = "/detailbalanceallocations")
    public ResponseEntity<HeadbalanceallocationModel> save(@RequestBody Headbalanceallocation headbalanceallocation, HttpServletRequest request) {
//        accessResourceService.validateIfPersonOfRequestIsSuperadminOrPartner(request);
        headbalanceallocation = headbalanceallocationService.register(headbalanceallocation, request);
        HeadbalanceallocationModelAssembler assembler = new HeadbalanceallocationModelAssembler();
        HeadbalanceallocationModel model = assembler.toModel(headbalanceallocation);
        return ResponseEntity.ok().body(model);
    }

    @PreAuthorize("true")
    @GetMapping(value = "/enterprises/{enterpriseId}/detailbalanceallocations")
    public ResponseEntity<CollectionModel<DetailbalanceallocationModel>> getAllBy(@PathVariable int enterpriseId,
                                                                                  Pageable pageable,
                                                                                  HttpServletRequest request) {
        accessResourceService.validateIfPersonRequestIsAuthorizedAdmin_(request, enterpriseId);
        Page<Detailbalanceallocation> detailbalanceallocations = detailbalanceallocationService.findAllBy(enterpriseId, pageable);
        DetailbalanceallocationModelAssembler assembler = new DetailbalanceallocationModelAssembler();
        final PagedModel<DetailbalanceallocationModel> model = resourcesAssembler.toModel(detailbalanceallocations, assembler);
        return ResponseEntity.ok(model);
    }
}
