package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumentmodification;
import com.apsout.electronictestimony.api.repository.DocumentmodificationRepository;
import com.apsout.electronictestimony.api.service.DocumentService;
import com.apsout.electronictestimony.api.service.DocumentmodificationService;
import com.apsout.electronictestimony.api.util.allocator.DocumentmodificationAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentmodificationServiceImpl implements DocumentmodificationService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentmodificationServiceImpl.class);

    @Autowired
    private DocumentmodificationRepository repository;
    @Autowired
    private DocumentService documentService;

    @Override
    public Documentmodification save(Documentmodification documentmodification) {
        return repository.save(documentmodification);
    }

    @Override
    public List<Documentmodification> findHistoricalGreaterThan(Timestamp historicalCreationDatetime) {
        return repository.findHistoricalGreaterThan(historicalCreationDatetime);
    }

    public Optional<Documentmodification> loadBy(int documentId) {
        Document document = documentService.getByNoDeleted(documentId);
        Optional<Documentmodification> optional = repository.findAsociatedHistoricalBy(document);
        MoreAboutDocumentmodification more = new MoreAboutDocumentmodification();
        if (optional.isPresent()) {
            Documentmodification observationcancel = optional.get();
            Person person = observationcancel.getPersonByPersonId();
            DocumentmodificationAllocator.forReturn(more, person);
            observationcancel.setMoreAboutDocumentmodification(more);
            return optional;
        }
        return Optional.empty();
    }
}
