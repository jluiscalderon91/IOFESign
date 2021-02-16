package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.HistoricalbalanceallocationModel;
import com.apsout.electronictestimony.api.modelassembler.HistoricalbalanceallocationModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.HistoricalbalanceallocationService;
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
public class HistoricalbalanceallocationController {

    @Autowired
    private AccessResourceService accessResourceService;
    @Autowired
    private HistoricalbalanceallocationService historicalbalanceallocationService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("true")
    @GetMapping(value = "/enterprises/{enterpriseId}/headbalanceallocations")
    public ResponseEntity<CollectionModel<HistoricalbalanceallocationModel>> getAllBy(@PathVariable int enterpriseId,
                                                                                      Pageable pageable,
                                                                                      HttpServletRequest request) {
        accessResourceService.validateIfBelong2PartnerIdOfEnterpriseId(request, enterpriseId);
        Page<Historicalbalanceallocation> historicalbalanceallocations = historicalbalanceallocationService.findAllBy(enterpriseId, pageable);
        HistoricalbalanceallocationModelAssembler assembler = new HistoricalbalanceallocationModelAssembler();
        final PagedModel<HistoricalbalanceallocationModel> model = resourcesAssembler.toModel(historicalbalanceallocations, assembler);
        return ResponseEntity.ok(model);
    }
}
