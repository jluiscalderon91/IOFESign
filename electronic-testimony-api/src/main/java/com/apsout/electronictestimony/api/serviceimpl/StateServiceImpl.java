package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.State;
import com.apsout.electronictestimony.api.exception.StateNotFoundException;
import com.apsout.electronictestimony.api.repository.StateRepository;
import com.apsout.electronictestimony.api.service.StateService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {
    private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

    @Autowired
    private StateRepository repository;

    @Override
    public List<State> findInitialAll() {
        logger.info("Getting initial list of states");
        return repository.findAllByActiveAndDeletedOrderByOrderStateAsc(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<State> findAll() {
        return Localstorage.states.isEmpty() ? findInitialAll() : Localstorage.states;
    }

    @Override
    public State getBy(int stateId) {
        Optional<State> optional = Localstorage.states
                .stream()
                .filter(state -> state.getId().equals(stateId))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new StateNotFoundException(String.format("State not found by stateId: %d", stateId));
    }

    public List<State> findAllBy(List<Integer> stateIds) {
        return Localstorage.states
                .stream()
                .filter(state -> stateIds.stream().anyMatch(stateId -> stateId.equals(state.getId())))
                .collect(Collectors.toList());
    }
}
