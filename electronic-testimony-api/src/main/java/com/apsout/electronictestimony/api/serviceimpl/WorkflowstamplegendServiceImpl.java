package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stamplegend;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstamplegend;
import com.apsout.electronictestimony.api.repository.WorkflowstamplegendRepository;
import com.apsout.electronictestimony.api.service.WorkflowstamplegendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowstamplegendServiceImpl implements WorkflowstamplegendService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowstamplegendServiceImpl.class);

    @Autowired
    private WorkflowstamplegendRepository repository;

    @Override
    @Transactional
    public Workflowstamplegend save(Workflowstamplegend workflowstamplegend) {
        final Workflowstamplegend save = repository.save(workflowstamplegend);
        logger.info(String.format("Workflowstamplegend save for workflowstamplegendId: %d", workflowstamplegend.getId()));
        return save;
    }

    @Transactional
    @Override
    public List<Workflowstamplegend> save(List<Workflowstamplegend> workflowstamplegends) {
        return workflowstamplegends.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Workflowstamplegend> findBy(Workflow workflow, Stamplegend stamplegend) {
        return repository.findBy(workflow.getId(), stamplegend.getId());
    }
}
