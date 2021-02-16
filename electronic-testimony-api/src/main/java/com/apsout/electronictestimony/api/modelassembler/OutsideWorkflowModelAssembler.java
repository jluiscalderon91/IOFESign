package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.WorkflowController;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.model.WorkflowModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class OutsideWorkflowModelAssembler extends RepresentationModelAssemblerSupport<Workflow, WorkflowModel> {

    public OutsideWorkflowModelAssembler() {
        super(WorkflowController.class, WorkflowModel.class);
    }

    @Override
    public WorkflowModel toModel(Workflow workflow) {
        WorkflowModel workflowModel = instantiateModel(workflow);
        workflowModel.setId(workflow.getId());
        workflowModel.setEnterpriseId(workflow.getEnterpriseId());
        workflowModel.setDescription(workflow.getDescription());
        workflowModel.setMaxParticipants(workflow.getMaxParticipants());
        workflowModel.setType(workflow.getType());
        return workflowModel;
    }
}
