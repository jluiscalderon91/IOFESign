package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stamplegend;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface StamplegendService {

    Stamplegend save(Stamplegend stamplegend);

    List<Stamplegend> save(List<Stamplegend> stamplegends);

    List<Stamplegend> findByWorkflowId(int workflowId);

    List<Stamplegend> findBy(Workflow workflow);

    Optional<Stamplegend> findBy(int stamplegendId);

    Stamplegend getBy(int stamplegendId);
}
