package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;

import java.util.List;
import java.util.Optional;

public interface WorkflowstampdatetimeService {

    Workflowstampdatetime save(Workflowstampdatetime workflowstampdatetime);

    List<Workflowstampdatetime> save(List<Workflowstampdatetime> workflowstampdatetimes);

    Optional<Workflowstampdatetime> findBy(Workflow workflow, Stampdatetime stampdatetime);
}
