package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.WorkflowController;
import com.apsout.electronictestimony.api.entity.model.WorkflowTemplateDesignModel;
import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class WorkflowTemplateDesignModelAssembler extends RepresentationModelAssemblerSupport<_WorkflowTemplateDesign, WorkflowTemplateDesignModel> {

    public WorkflowTemplateDesignModelAssembler() {
        super(WorkflowController.class, WorkflowTemplateDesignModel.class);
    }

    @Override
    public WorkflowTemplateDesignModel toModel(_WorkflowTemplateDesign workflowTemplateDesign) {
        WorkflowTemplateDesignModel workflowTemplateDesignModel = instantiateModel(workflowTemplateDesign);
        workflowTemplateDesignModel.setWorkflowId(workflowTemplateDesign.getWorkflowId());
        workflowTemplateDesignModel.setStamplegends(workflowTemplateDesign.getStamplegends());
        workflowTemplateDesignModel.setStampimages(workflowTemplateDesign.getStampimages());
        workflowTemplateDesignModel.setStampqrcodes(workflowTemplateDesign.getStampqrcodes());
        workflowTemplateDesignModel.setStamptestfile(workflowTemplateDesign.getStamptestfile());
        workflowTemplateDesignModel.setStampdatetimes(workflowTemplateDesign.getStampdatetimes());
        workflowTemplateDesignModel.setStamplayoutfile(workflowTemplateDesign.getStamplayoutfile());
        workflowTemplateDesignModel.setStamprubrics(workflowTemplateDesign.getStamprubrics());
        return workflowTemplateDesignModel;
    }
}
