package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampimage;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstampimage;
import com.apsout.electronictestimony.api.repository.WorkflowstampimageRepository;
import com.apsout.electronictestimony.api.service.WorkflowstampimageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowstampimageServiceImpl implements WorkflowstampimageService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowstampimageServiceImpl.class);

    @Autowired
    private WorkflowstampimageRepository repository;

    @Override
    @Transactional
    public Workflowstampimage save(Workflowstampimage workflowstampimage) {
        final Workflowstampimage save = repository.save(workflowstampimage);
        logger.info(String.format("Workflowstampimage save for workflowstampimageId: %d", workflowstampimage.getId()));
        return save;
    }

    @Transactional
    @Override
    public List<Workflowstampimage> save(List<Workflowstampimage> workflowstampimages) {
        return workflowstampimages.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Workflowstampimage> findBy(Workflow workflow, Stampimage stampimage) {
        return repository.findBy(workflow.getId(), stampimage.getId());
    }
}
