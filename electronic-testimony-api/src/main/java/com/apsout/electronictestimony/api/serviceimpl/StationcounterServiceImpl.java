package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Stationcounter;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.exception.StationcounterNotFoundException;
import com.apsout.electronictestimony.api.repository.StationcounterRepository;
import com.apsout.electronictestimony.api.service.PersonService;
import com.apsout.electronictestimony.api.service.StationcounterService;
import com.apsout.electronictestimony.api.service.WorkflowService;
import com.apsout.electronictestimony.api.util.allocator.StationcounterAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class StationcounterServiceImpl implements StationcounterService {
    private static final Logger logger = LoggerFactory.getLogger(StationcounterServiceImpl.class);

    @Autowired
    private StationcounterRepository repository;
    @Autowired
    private PersonService personService;
    @Autowired
    private WorkflowService workflowService;

    @Override
    public Optional<Stationcounter> findBy(int stationcounterId) {
        return repository.findBy(stationcounterId);
    }

    @Override
    public Stationcounter getBy(int stationcounterId) {
        final Optional<Stationcounter> optional = findBy(stationcounterId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new StationcounterNotFoundException(String.format("Stationcounter not found with stationcounterId: %d", stationcounterId));
    }

    @Override
    public Stationcounter save(Stationcounter stationcounter, HttpServletRequest request) {
        Person person = personService.getBy(request);
        Workflow workflow = workflowService.getBy(stationcounter.getWorkflowId());
        Stationcounter stationcounterdb = StationcounterAllocator.build(stationcounter, person, workflow);
        this.onlysave(stationcounterdb);
        logger.info(String.format("Stationcounter saved with stationcounterId: %s", stationcounterdb));
        return stationcounterdb;
    }

    @Override
    public Stationcounter update(Stationcounter stationcounter) {
        Stationcounter stationcounterdb = getBy(stationcounter.getId());
        StationcounterAllocator.forUpdate(stationcounter, stationcounterdb);
        final Stationcounter updated = onlysave(stationcounterdb);
        logger.info(String.format("Stationcounter updated with stationcounterId: %s", stationcounter));
        return updated;
    }

    @Override
    public Stationcounter onlysave(Stationcounter stationcounter) {
        return repository.save(stationcounter);
    }
}
