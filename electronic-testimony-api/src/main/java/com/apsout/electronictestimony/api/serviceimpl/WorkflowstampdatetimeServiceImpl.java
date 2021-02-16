package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstampdatetime;
import com.apsout.electronictestimony.api.repository.WorkflowstampdatetimeRepository;
import com.apsout.electronictestimony.api.service.WorkflowstampdatetimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowstampdatetimeServiceImpl implements WorkflowstampdatetimeService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowstampdatetimeServiceImpl.class);

    @Autowired
    private WorkflowstampdatetimeRepository repository;

    @Override
    @Transactional
    public Workflowstampdatetime save(Workflowstampdatetime workflowstampdatetime) {
        final Workflowstampdatetime save = repository.save(workflowstampdatetime);
        logger.info(String.format("Workflowstampdatetime save for workflowstampdatetimeId: %d", workflowstampdatetime.getId()));
        return save;
    }

    @Transactional
    @Override
    public List<Workflowstampdatetime> save(List<Workflowstampdatetime> workflowstampdatetimes) {
        return workflowstampdatetimes.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Workflowstampdatetime> findBy(Workflow workflow, Stampdatetime stampdatetime) {
        return repository.findBy(workflow.getId(), stampdatetime.getId());
    }
}
