package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;

import java.util.List;
import java.util.Optional;

public interface WorkflowstamprubricService {

    Workflowstamprubric save(Workflowstamprubric workflowstamprubric);

    List<Workflowstamprubric> save(List<Workflowstamprubric> workflowstamprubrics);

    Optional<Workflowstamprubric> findBy(Workflow workflow, Stamprubric stamprubric);
}
