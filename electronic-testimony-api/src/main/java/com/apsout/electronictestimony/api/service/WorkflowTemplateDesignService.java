package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;

public interface WorkflowTemplateDesignService {

    _WorkflowTemplateDesign getBy(int workflowId);

    _WorkflowTemplateDesign getBy4Outside(int workflowId);
}
