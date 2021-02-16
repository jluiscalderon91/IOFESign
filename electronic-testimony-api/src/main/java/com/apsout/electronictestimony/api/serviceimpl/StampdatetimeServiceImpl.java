package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StampdatetimeRepository;
import com.apsout.electronictestimony.api.service.StampdatetimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StampdatetimeServiceImpl implements StampdatetimeService {
    private static final Logger logger = LoggerFactory.getLogger(StampdatetimeServiceImpl.class);

    @Autowired
    private StampdatetimeRepository repository;

    @Override
    public Stampdatetime save(Stampdatetime stampdatetime) {
        return repository.save(stampdatetime);
    }

    @Override
    public List<Stampdatetime> save(List<Stampdatetime> stampdatetimes) {
        return stampdatetimes.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Stampdatetime> findByWorkflowId(int workflowId) {
        return repository.findAllByWorkflow(workflowId);
    }

    @Override
    public List<Stampdatetime> findBy(Workflow workflow) {
        return this.findByWorkflowId(workflow.getId());
    }

    public Optional<Stampdatetime> findFirstBy(Workflow workflow) {
        return this.findBy(workflow).stream().findFirst();
    }

    @Override
    public Optional<Stampdatetime> findBy(int stampdatetimeId) {
        return repository.findBy(stampdatetimeId);
    }

    @Override
    public Stampdatetime getBy(int stampdatetimeId) {
        Optional<Stampdatetime> optional = this.findBy(stampdatetimeId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stampdatetime not found with stampdatetimeId: %d", stampdatetimeId));
    }
}
