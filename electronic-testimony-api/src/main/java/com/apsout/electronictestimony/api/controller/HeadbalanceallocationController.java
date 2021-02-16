package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.HeadbalanceallocationModel;
import com.apsout.electronictestimony.api.modelassembler.HeadbalanceallocationModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.HeadbalanceallocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class HeadbalanceallocationController {

    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("true")
    @PostMapping(value = "/headbalanceallocations")
    public ResponseEntity<HeadbalanceallocationModel> save(@RequestBody Headbalanceallocation headbalanceallocation, HttpServletRequest request) {
        accessResourceService.validateIfPersonOfRequestIsSuperadminOrPartner(request);
        headbalanceallocation = headbalanceallocationService.register(headbalanceallocation, request);
        HeadbalanceallocationModelAssembler assembler = new HeadbalanceallocationModelAssembler();
        HeadbalanceallocationModel model = assembler.toModel(headbalanceallocation);
        return ResponseEntity.ok().body(model);
    }
}
