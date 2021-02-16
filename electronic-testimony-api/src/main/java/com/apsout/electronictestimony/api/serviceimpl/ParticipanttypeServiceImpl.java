package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Participanttype;
import com.apsout.electronictestimony.api.exception.ParticipanttypeNotFoundException;
import com.apsout.electronictestimony.api.repository.ParticipanttypeRepository;
import com.apsout.electronictestimony.api.service.ParticipanttypeService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanttypeServiceImpl implements ParticipanttypeService {
    private static final Logger logger = LoggerFactory.getLogger(ParticipanttypeServiceImpl.class);

    @Autowired
    private ParticipanttypeRepository repository;

    @Override
    public List<Participanttype> findInitialAll() {
        logger.info("Getting initial list of participanttypes");
        return repository.findAllByActiveAndDeletedOrderByOrderParticipanttypeAsc(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<Participanttype> findAll() {
        return Localstorage.participanttypes.isEmpty() ? findInitialAll() : Localstorage.participanttypes;
    }

    @Override
    public Participanttype getBy(int participanttypeId) {
        Optional<Participanttype> optional = Localstorage.participanttypes
                .stream()
                .filter(participanttype -> participanttype.getId().equals(participanttypeId))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ParticipanttypeNotFoundException(String.format("Participant type not found for participanttypeId: %d", participanttypeId));
    }
}
