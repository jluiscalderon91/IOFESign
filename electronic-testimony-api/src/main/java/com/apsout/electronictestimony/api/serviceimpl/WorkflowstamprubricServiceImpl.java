package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stamprubric;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstamprubric;
import com.apsout.electronictestimony.api.repository.WorkflowstamprubricRepository;
import com.apsout.electronictestimony.api.service.WorkflowstamprubricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowstamprubricServiceImpl implements WorkflowstamprubricService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowstamprubricServiceImpl.class);

    @Autowired
    private WorkflowstamprubricRepository repository;

    @Override
    @Transactional
    public Workflowstamprubric save(Workflowstamprubric workflowstamprubric) {
        final Workflowstamprubric save = repository.save(workflowstamprubric);
        logger.info(String.format("Workflowstamprubric save for workflowstamprubricId: %d", workflowstamprubric.getId()));
        return save;
    }

    @Transactional
    @Override
    public List<Workflowstamprubric> save(List<Workflowstamprubric> workflowstamprubrics) {
        return workflowstamprubrics.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Workflowstamprubric> findBy(Workflow workflow, Stamprubric stamprubric) {
        return repository.findBy(workflow.getId(), stamprubric.getId());
    }
}
