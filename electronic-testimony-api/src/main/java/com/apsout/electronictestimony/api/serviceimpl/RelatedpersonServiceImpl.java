package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Relatedperson;
import com.apsout.electronictestimony.api.repository.RelatedpersonRepository;
import com.apsout.electronictestimony.api.service.RelatedpersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelatedpersonServiceImpl implements RelatedpersonService {
    private static final Logger logger = LoggerFactory.getLogger(RelatedpersonServiceImpl.class);

    @Autowired
    private RelatedpersonRepository repository;

    @Override
    public Optional<Relatedperson> findByPersonId(int personId) {
        return repository.findByPersonId(personId);
    }

    @Override
    public Optional<Relatedperson> findByPersonIdRelated(int personIdRelated) {
        return repository.findByPersonIdRelated(personIdRelated);
    }
}
