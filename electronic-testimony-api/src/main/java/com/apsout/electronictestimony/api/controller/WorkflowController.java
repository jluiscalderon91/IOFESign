package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.model.WorkflowModel;
import com.apsout.electronictestimony.api.entity.model.WorkflowTemplateDesignModel;
import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;
import com.apsout.electronictestimony.api.modelassembler.OutsideWorkflowModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.OutsideWorkflowTemplateDesignModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.WorkflowModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.WorkflowTemplateDesignModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.WorkflowService;
import com.apsout.electronictestimony.api.service.WorkflowTemplateDesignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class WorkflowController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private WorkflowTemplateDesignService workflowTemplateDesignService;
    @Autowired
    private AccessResourceService accessResourceService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:workflow:get:enterprise')")
    @GetMapping(value = "/partners/{partnerId}/enterprises/{enterpriseId}/workflows")
    public ResponseEntity<CollectionModel<WorkflowModel>> getAllBy(@PathVariable("partnerId") int partnerId,
                                                                   @PathVariable("enterpriseId") int enterpriseId,
                                                                   @RequestParam("findby") String findby,
                                                                   Pageable pageable,
                                                                   HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Page<Workflow> workflows = workflowService.findAllBy(partnerId, enterpriseId, findby, pageable);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        PagedModel<WorkflowModel> pagedModel = resourcesAssembler.toModel(workflows, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:add')")
    @PostMapping(value = "/workflows")
    public ResponseEntity<WorkflowModel> save(@RequestBody Workflow workflow) {
        workflowService.save(workflow);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        WorkflowModel workflowModel = assembler.toModel(workflow);
        return ResponseEntity.ok().body(workflowModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:edit')")
    @PutMapping(value = "/workflows")
    public ResponseEntity<WorkflowModel> edit(@RequestBody Workflow workflow) {
        workflowService.update(workflow);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        WorkflowModel workflowModel = assembler.toModel(workflow);
        return ResponseEntity.ok().body(workflowModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:delete')")
    @DeleteMapping(value = "/workflows")
    public ResponseEntity<WorkflowModel> delete(@RequestParam("workflowId") int workflowId) {
        Workflow workflow = workflowService.getBy(workflowId);
        workflowService.delete(workflow);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        WorkflowModel workflowModel = assembler.toModel(workflow);
        return ResponseEntity.ok().body(workflowModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:get:person')")
    @GetMapping(value = "/people/{personId}/workflows")
    public ResponseEntity<CollectionModel<WorkflowModel>> getAllByPerson(@PathVariable int personId, HttpServletRequest request) {
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        List<Workflow> workflows = workflowService.findAllWhereIsAssigned(personId);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(workflows));
    }

    @PreAuthorize("hasAuthority('outside:workflow:get:enterprise')")
    @GetMapping(value = "/outside/enterprises/{enterpriseId}/workflows")
    public ResponseEntity<CollectionModel<WorkflowModel>> getAllByEnterprise(@PathVariable("enterpriseId") int enterpriseId,
                                                                             HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        List<Workflow> workflows = workflowService.findAllBy4Outside(enterpriseId);
        OutsideWorkflowModelAssembler assembler = new OutsideWorkflowModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(workflows));
    }

    @PreAuthorize("hasAuthority('outside:workflow:get:person')")
    @GetMapping(value = "/outside/people/{personId}/workflows")
    public ResponseEntity<CollectionModel<WorkflowModel>> getAllByPerson2(@PathVariable int personId, HttpServletRequest request) {
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        List<Workflow> workflows = workflowService.findAllWhereIsParticipantOrAssigned(personId);
        OutsideWorkflowModelAssembler assembler = new OutsideWorkflowModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(workflows));
    }

    @PreAuthorize("hasAuthority('own:workflow:template:add')")
    @PostMapping(value = "/workflows/templatedesign")
    public ResponseEntity<WorkflowModel> save(@RequestBody _WorkflowTemplateDesign workflowTemplateDesign, HttpServletRequest request) {
        Workflow workflow = workflowService.saveTemplateDesign(workflowTemplateDesign);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        WorkflowModel workflowModel = assembler.toModel(workflow);
        return ResponseEntity.ok().body(workflowModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:template:get')")
    @GetMapping(value = "/workflows/{workflowId}/templatedesign")
    public ResponseEntity<WorkflowTemplateDesignModel> get(@PathVariable int workflowId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfWorkflowId(request, workflowId);
        _WorkflowTemplateDesign workflowTemplateDesign = workflowTemplateDesignService.getBy(workflowId);
        WorkflowTemplateDesignModelAssembler assembler = new WorkflowTemplateDesignModelAssembler();
        WorkflowTemplateDesignModel workflowTemplateDesignModel = assembler.toModel(workflowTemplateDesign);
        return ResponseEntity.ok().body(workflowTemplateDesignModel);
    }

    @PreAuthorize("hasAuthority('own:workflow:template:edit')")
    @PutMapping(value = "/workflows/templatedesign")
    public ResponseEntity<WorkflowModel> update(@RequestBody _WorkflowTemplateDesign workflowTemplateDesign, HttpServletRequest request) {
        Workflow workflow = workflowService.updateTemplateDesign(workflowTemplateDesign);
        WorkflowModelAssembler assembler = new WorkflowModelAssembler();
        WorkflowModel workflowModel = assembler.toModel(workflow);
        return ResponseEntity.ok().body(workflowModel);
    }

    @PreAuthorize("hasAuthority('outside:workflow:template:get')")
    @GetMapping(value = "/outside/workflows/{workflowId}/templatedesign")
    public ResponseEntity<WorkflowTemplateDesignModel> get2(@PathVariable int workflowId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfWorkflowId(request, workflowId);
        _WorkflowTemplateDesign workflowTemplateDesign = workflowTemplateDesignService.getBy4Outside(workflowId);
        OutsideWorkflowTemplateDesignModelAssembler assembler = new OutsideWorkflowTemplateDesignModelAssembler();
        WorkflowTemplateDesignModel workflowTemplateDesignModel = assembler.toModel(workflowTemplateDesign);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(workflowTemplateDesignModel);
    }
}
