package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Identificationdocument;
import com.apsout.electronictestimony.api.repository.IdentificationDocumentRepository;
import com.apsout.electronictestimony.api.service.IdentificationDocumentService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationDocumentServiceImpl implements IdentificationDocumentService {
    private static final Logger logger = LoggerFactory.getLogger(IdentificationDocumentServiceImpl.class);

    @Autowired
    private IdentificationDocumentRepository repository;

    @Override
    public List<Identificationdocument> findInitialAll() {
        logger.info("Getting initial list of identificationdocuments");
        return repository.findAll();
    }

    @Override
    public List<Identificationdocument> findAll() {
        return Localstorage.identificationdocuments.isEmpty() ? findInitialAll() : Localstorage.identificationdocuments;
    }
}
