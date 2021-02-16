package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Persontype;
import com.apsout.electronictestimony.api.repository.PersonTypeRepository;
import com.apsout.electronictestimony.api.service.PersontypeService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersontypeServiceImpl implements PersontypeService {
    private static final Logger logger = LoggerFactory.getLogger(PersontypeServiceImpl.class);

    @Autowired
    private PersonTypeRepository repository;

    @Override
    public List<Persontype> findInitialAll() {
        logger.info("Getting initial list of persontypes");
        return repository.findAllByActiveAndDeleted(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<Persontype> findAll() {
        return Localstorage.persontypes.isEmpty() ? findInitialAll() : Localstorage.persontypes;
    }
}
