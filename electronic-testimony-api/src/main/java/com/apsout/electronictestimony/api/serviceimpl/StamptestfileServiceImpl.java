package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stamptestfile;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StamptestfileRepository;
import com.apsout.electronictestimony.api.service.StamptestfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StamptestfileServiceImpl implements StamptestfileService {
    private static final Logger logger = LoggerFactory.getLogger(StamptestfileServiceImpl.class);

    @Autowired
    private StamptestfileRepository repository;

    @Override
    public Stamptestfile save(Stamptestfile stamptestfile) {
        return repository.save(stamptestfile);
    }

    @Override
    public Stamptestfile getByWorkflowId(int workflowId) {
        Optional<Stamptestfile> optional = findByWorkflowId(workflowId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Optional<Stamptestfile> findByWorkflowId(int workflowId) {
        return repository.findByWorkflow(workflowId);
    }

    @Override
    public Stamptestfile getBy(Workflow workflow) {
        return this.getByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamptestfile> findBy(Workflow workflow) {
        return findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamptestfile> findBy(int stamptestfileId) {
        return repository.findBy(stamptestfileId);
    }

    @Override
    public Stamptestfile getBy(int stamptestfileId) {
        Optional<Stamptestfile> optional = this.findBy(stamptestfileId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stamptestfile not found with stamptestfileId: %d", stamptestfileId));
    }
}
