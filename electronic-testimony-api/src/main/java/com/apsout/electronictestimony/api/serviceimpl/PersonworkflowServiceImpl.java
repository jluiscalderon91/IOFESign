package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Personworkflow;
import com.apsout.electronictestimony.api.repository.PersonWorkflowRepository;
import com.apsout.electronictestimony.api.service.PersonworkflowService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonworkflowServiceImpl implements PersonworkflowService {
    private static final Logger logger = LoggerFactory.getLogger(PersonworkflowServiceImpl.class);

    @Autowired
    private PersonWorkflowRepository repository;

    @Override
    public Optional<Personworkflow> findBy(Personworkflow personworkflow) {
        return repository.findByPersonIdAndWorkflowIdAndDeleted(
                personworkflow.getPersonId(),
                personworkflow.getWorkflowId(),
                States.EXISTENT);
    }

    @Override
    public Personworkflow save(Personworkflow personworkflow) {
        return repository.save(personworkflow);
    }
}
