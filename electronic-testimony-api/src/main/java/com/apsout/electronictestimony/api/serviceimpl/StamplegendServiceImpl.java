package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stamplegend;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StamplegendRepository;
import com.apsout.electronictestimony.api.service.StamplegendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StamplegendServiceImpl implements StamplegendService {
    private static final Logger logger = LoggerFactory.getLogger(StamplegendServiceImpl.class);

    @Autowired
    private StamplegendRepository repository;

    @Override
    public Stamplegend save(Stamplegend stamplegend) {
        return repository.save(stamplegend);
    }

    @Override
    public List<Stamplegend> save(List<Stamplegend> stamplegends) {
        return stamplegends.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Stamplegend> findByWorkflowId(int workflowId) {
        return repository.findAllByWorkflow(workflowId);
    }

    @Override
    public List<Stamplegend> findBy(Workflow workflow) {
        return this.findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamplegend> findBy(int stamplegendId) {
        return repository.findBy(stamplegendId);
    }

    @Override
    public Stamplegend getBy(int stamplegendId) {
        Optional<Stamplegend> optional = this.findBy(stamplegendId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stamplegend not found with stamplegendId: %d", stamplegendId));
    }
}
