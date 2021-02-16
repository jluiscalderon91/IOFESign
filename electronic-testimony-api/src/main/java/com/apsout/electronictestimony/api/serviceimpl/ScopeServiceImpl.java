package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Scope;
import com.apsout.electronictestimony.api.exception.ScopeNotFoundException;
import com.apsout.electronictestimony.api.repository.ScopeRepository;
import com.apsout.electronictestimony.api.service.ScopeService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScopeServiceImpl implements ScopeService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private ScopeRepository scopeRepository;

    @Override
    public Scope getBy(int personId) {
        final Optional<Scope> optionalScope = scopeRepository.findFirstByPersonIdAndActiveAndDeleted(personId, States.ACTIVE, States.EXISTENT);
        if (optionalScope.isPresent()) {
            return optionalScope.get();
        }
        throw new ScopeNotFoundException(String.format("Scope not found for personId: %d", personId));
    }

    @Override
    public Scope save(Scope scope) {
        return scopeRepository.save(scope);
    }

    @Override
    public Scope findBy(int personId) {
        Optional<Scope> optional = scopeRepository.findByPersonIdAndDeleted(personId, States.EXISTENT);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ScopeNotFoundException(String.format("Scope not found for personId: %d", personId));
    }
}
