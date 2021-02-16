package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.model.OperatorModel;
import com.apsout.electronictestimony.api.modelassembler.OperatorModelAssembler;
import com.apsout.electronictestimony.api.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:operator:get:enterprise:page')")
    @GetMapping(value = "/enterprises/{enterpriseId}/operators")
    public ResponseEntity<CollectionModel<OperatorModel>> getAllBy(@PathVariable("enterpriseId") int enterpriseId, Pageable pageable) {
        Page<Operator> operators = operatorService.getAllBy(enterpriseId, pageable);
        OperatorModelAssembler assembler = new OperatorModelAssembler();
        PagedModel<OperatorModel> pagedModel = resourcesAssembler.toModel(operators, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:operator:add')")
    @PostMapping(value = "/operators")
    public ResponseEntity<OperatorModel> save(@RequestBody Operator operator) {
        operatorService.save(operator);
        OperatorModelAssembler assembler = new OperatorModelAssembler();
        OperatorModel operatorModel = assembler.toModel(operator);
        return ResponseEntity.ok().body(operatorModel);
    }

    @PreAuthorize("hasAuthority('own:operator:edit')")
    @PutMapping(value = "/operators")
    public ResponseEntity<OperatorModel> update(@RequestBody Operator operator) {
        operatorService.update(operator);
        OperatorModelAssembler assembler = new OperatorModelAssembler();
        OperatorModel operatorModel = assembler.toModel(operator);
        return ResponseEntity.ok().body(operatorModel);
    }

    @PreAuthorize("hasAuthority('own:operator:delete')")
    @DeleteMapping(value = "/operators")
    public ResponseEntity<OperatorModel> delete(@RequestParam("operatorId") int operatorId) {
        Operator operator = operatorService.getBy(operatorId);
        operatorService.delete(operator);
        OperatorModelAssembler assembler = new OperatorModelAssembler();
        OperatorModel operatorModel = assembler.toModel(operator);
        return ResponseEntity.ok().body(operatorModel);
    }
}
