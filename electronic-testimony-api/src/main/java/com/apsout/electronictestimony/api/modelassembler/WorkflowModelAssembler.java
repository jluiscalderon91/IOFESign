package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.PublicController;
import com.apsout.electronictestimony.api.controller.WorkflowController;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.model.WorkflowModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WorkflowModelAssembler extends RepresentationModelAssemblerSupport<Workflow, WorkflowModel> {

    public WorkflowModelAssembler() {
        super(WorkflowController.class, WorkflowModel.class);
    }

    @Override
    public WorkflowModel toModel(Workflow workflow) {
        WorkflowModel workflowModel = instantiateModel(workflow);
        workflowModel.add(linkTo(methodOn(PublicController.class).getStreamResourceBy(workflow.getId(), null)).withRel("templatestream"));
        workflowModel.setId(workflow.getId());
        workflowModel.setEnterpriseId(workflow.getEnterpriseId());
        workflowModel.setDescription(workflow.getDescription());
        workflowModel.setMaxParticipants(workflow.getMaxParticipants());
        workflowModel.setBatch(workflow.getBatch());
        workflowModel.setCompleted(workflow.getCompleted());
        workflowModel.setCreateAt(workflow.getCreateAt());
        workflowModel.setActive(workflow.getActive());
        workflowModel.setObservation(workflow.getObservation());
        workflowModel.set_more(workflow.getMoreAboutWorkflow());
        workflowModel.setReady2Use(workflow.getReady2Use());
        workflowModel.setDeliver(workflow.getDeliver());
        workflowModel.setCode(workflow.getCode());
        workflowModel.setType(workflow.getType());
        workflowModel.setType(workflow.getType());
        workflowModel.setDynamic(workflow.getDynamic());
        return workflowModel;
    }
}
