package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampimage;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StampimageRepository;
import com.apsout.electronictestimony.api.service.StampimageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StampimageServiceImpl implements StampimageService {
    private static final Logger logger = LoggerFactory.getLogger(StampimageServiceImpl.class);

    @Autowired
    private StampimageRepository repository;

    @Override
    public Stampimage save(Stampimage stampimage) {
        return repository.save(stampimage);
    }

    @Override
    public List<Stampimage> save(List<Stampimage> stampimages) {
        return stampimages.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Stampimage> findByWorkflowId(int workflowId) {
        return repository.findAllByWorkflow(workflowId);
    }

    @Override
    public List<Stampimage> findBy(Workflow workflow) {
        return this.findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stampimage> findBy(int stampimageId) {
        return repository.findBy(stampimageId);
    }

    @Override
    public Stampimage getBy(int stampimageId) {
        Optional<Stampimage> optional = this.findBy(stampimageId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stampimage not found with stampimageId: %d", stampimageId));
    }
}
