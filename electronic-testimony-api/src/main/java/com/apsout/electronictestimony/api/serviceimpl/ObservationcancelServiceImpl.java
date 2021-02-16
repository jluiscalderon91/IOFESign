package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutObservationcancel;
import com.apsout.electronictestimony.api.repository.ObservationcancelRepository;
import com.apsout.electronictestimony.api.service.DocumentService;
import com.apsout.electronictestimony.api.service.ObservationcancelService;
import com.apsout.electronictestimony.api.util.allocator.ObservationcancelAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObservationcancelServiceImpl implements ObservationcancelService {
    private static final Logger logger = LoggerFactory.getLogger(ObservationcancelServiceImpl.class);

    @Autowired
    private ObservationcancelRepository repository;
    @Autowired
    private DocumentService documentService;

    @Override
    public Observationcancel save(Observationcancel scope) {
        return repository.save(scope);
    }

    @Override
    public Optional<Observationcancel> findByDocument(Document document) {
        return repository.findByDocumentByDocumentIdAndDeleted(document, States.EXISTENT);
    }

    public Optional<Observationcancel> loadBy(int documentId) {
        Document document = documentService.getByNoDeleted(documentId);
        Optional<Observationcancel> optional = findByDocument(document);
        MoreAboutObservationcancel more = new MoreAboutObservationcancel();
        if (optional.isPresent()) {
            Observationcancel observationcancel = optional.get();
            Person person = observationcancel.getPersonByPersonId();
            ObservationcancelAllocator.forReturn(more, person);
            observationcancel.setMoreAboutObservationcancel(more);
            return optional;
        }
        return Optional.empty();
    }
}
