package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Workflowtype;
import com.apsout.electronictestimony.api.repository.WorkflowtypeRepository;
import com.apsout.electronictestimony.api.service.WorkflowtypeService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowtypeServiceImpl implements WorkflowtypeService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowtypeServiceImpl.class);

    @Autowired
    private WorkflowtypeRepository repository;

    @Override
    public List<Workflowtype> findInitialAll() {
        logger.info("Getting initial list of workflowtypes");
        return repository.findAll();
    }

    @Override
    public List<Workflowtype> findAll() {
        return Localstorage.workflowtypes.isEmpty() ? findInitialAll() : Localstorage.workflowtypes;
    }

    @Override
    public Optional<Workflowtype> findBy(int workflowtypeId) {
        return Localstorage.workflowtypes
                .stream()
                .filter(workflowtype -> workflowtype.getId().equals(workflowtypeId))
                .findFirst();
    }

    @Override
    public Workflowtype getBy(int workflowtypeId) {
        Optional<Workflowtype> optional = this.findBy(workflowtypeId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Workflowtype not found with workflowtypeId: %d", workflowtypeId));
    }
}
