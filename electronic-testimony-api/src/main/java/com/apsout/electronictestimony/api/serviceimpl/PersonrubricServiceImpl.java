package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Personrubric;
import com.apsout.electronictestimony.api.repository.PersonrubricRepository;
import com.apsout.electronictestimony.api.service.PersonrubricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonrubricServiceImpl implements PersonrubricService {
    private static final Logger logger = LoggerFactory.getLogger(PersonrubricServiceImpl.class);

    @Autowired
    private PersonrubricRepository repository;

    @Override
    public Personrubric save(Personrubric personrubric) {
        return repository.save(personrubric);
    }

    //
//    @Override
//    public List<Stampimage> save(List<Stampimage> stampimages) {
//        return stampimages.stream().map(this::save).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Stampimage> findByWorkflowId(int workflowId) {
//        return repository.findAllByWorkflow(workflowId);
//    }
//
//    @Override
//    public List<Stampimage> findBy(Workflow workflow) {
//        return this.findByWorkflowId(workflow.getId());
//    }
//
    @Override
    public Optional<Personrubric> findBy(Person person) {
        return repository.findBy(person);
    }
//
//    @Override
//    public Stampimage getBy(int stampimageId) {
//        Optional<Stampimage> optional = this.findBy(stampimageId);
//        if (optional.isPresent()) {
//            return optional.get();
//        }
//        throw new RuntimeException(String.format("Stampimage not found with stampimageId: %d", stampimageId));
//    }
}
