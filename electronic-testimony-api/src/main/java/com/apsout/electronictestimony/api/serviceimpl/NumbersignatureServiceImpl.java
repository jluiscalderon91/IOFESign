package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Numbersignature;
import com.apsout.electronictestimony.api.repository.NumbersignatureRepository;
import com.apsout.electronictestimony.api.service.NumbersignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NumbersignatureServiceImpl implements NumbersignatureService {
    private static final Logger logger = LoggerFactory.getLogger(NumbersignatureServiceImpl.class);

    @Autowired
    private NumbersignatureRepository repository;

    @Override
    public Numbersignature save(Numbersignature numbersignature) {
        return repository.save(numbersignature);
    }

    @Override
    public Optional<Numbersignature> findBy(Document document, String hashResource) {
        return repository.findBy(document, hashResource);
    }
}
