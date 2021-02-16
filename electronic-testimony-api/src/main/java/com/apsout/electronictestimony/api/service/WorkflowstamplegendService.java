package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stamplegend;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstamplegend;

import java.util.List;
import java.util.Optional;

public interface WorkflowstamplegendService {

    Workflowstamplegend save(Workflowstamplegend workflowstamplegend);

    List<Workflowstamplegend> save(List<Workflowstamplegend> workflowstamplegends);

    Optional<Workflowstamplegend> findBy(Workflow workflow, Stamplegend stamplegend);
}
