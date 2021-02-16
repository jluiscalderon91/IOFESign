package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampqrcode;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstampqrcode;
import com.apsout.electronictestimony.api.repository.WorkflowstampqrcodeRepository;
import com.apsout.electronictestimony.api.service.WorkflowstampqrcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowstampqrcodeServiceImpl implements WorkflowstampqrcodeService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowstampqrcodeServiceImpl.class);

    @Autowired
    private WorkflowstampqrcodeRepository repository;

    @Override
    @Transactional
    public Workflowstampqrcode save(Workflowstampqrcode workflowstampqrcode) {
        final Workflowstampqrcode save = repository.save(workflowstampqrcode);
        logger.info(String.format("Workflowstampqrcode save for workflowstampqrcodeId: %d", workflowstampqrcode.getId()));
        return save;
    }

    @Transactional
    @Override
    public List<Workflowstampqrcode> save(List<Workflowstampqrcode> workflowstampqrcodes) {
        return workflowstampqrcodes.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Workflowstampqrcode> findBy(Workflow workflow, Stampqrcode stampqrcode) {
        return repository.findBy(workflow.getId(), stampqrcode.getId());
    }
}
