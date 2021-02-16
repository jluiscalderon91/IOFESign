package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Stamprubric;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StamprubricRepository;
import com.apsout.electronictestimony.api.service.StamprubricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StamprubricServiceImpl implements StamprubricService {
    private static final Logger logger = LoggerFactory.getLogger(StampimageServiceImpl.class);

    @Autowired
    private StamprubricRepository repository;

    @Override
    public Stamprubric save(Stamprubric stamprubric) {
        return repository.save(stamprubric);
    }

    @Override
    public List<Stamprubric> save(List<Stamprubric> stamprubrics) {
        return stamprubrics.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Stamprubric> findByWorkflowId(int workflowId) {
        return repository.findAllByWorkflow(workflowId);
    }

    @Override
    public List<Stamprubric> findBy(Workflow workflow) {
        return this.findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamprubric> findBy(int stamprubricId) {
        return repository.findBy(stamprubricId);
    }

    @Override
    public Stamprubric getBy(int stamprubricId) {
        Optional<Stamprubric> optional = this.findBy(stamprubricId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stamprubric not found with stamprubricId: %d", stamprubricId));
    }

    @Override
    public Optional<Stamprubric> findBy(Participant participant) {
        return this.findByParticipantId(participant.getId());
    }

    public Optional<Stamprubric> findByParticipantId(int participantId) {
        return repository.findByParticipantId(participantId);
    }

    public Optional<Stamprubric> findBy(int personId, int documentId) {
        return repository.findBy(personId, documentId);
    }
}
