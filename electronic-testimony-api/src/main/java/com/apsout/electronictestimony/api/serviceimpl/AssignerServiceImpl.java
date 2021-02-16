package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.exception.AssignerNotFoundException;
import com.apsout.electronictestimony.api.repository.AssignerRepository;
import com.apsout.electronictestimony.api.service.ApplicationService;
import com.apsout.electronictestimony.api.service.AssignerService;
import com.apsout.electronictestimony.api.util.allocator.AssignerAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssignerServiceImpl implements AssignerService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private AssignerRepository repository;
    @Autowired
    private ApplicationService applicationService;

    @Override
    @Transactional
    public Assigner save(Assigner assigner) {
        return repository.save(assigner);
    }

    public Optional<Assigner> findFirstByAndOrderOperationDesc(int enterpriseId, int documentId) {
        return repository.findFirstByAndOrderOperationDesc(enterpriseId, documentId);
    }

    public Assigner getBy(int personId, int documentId) {
        Optional<Assigner> optional = this.findBy(personId, documentId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new AssignerNotFoundException(String.format("Assigner not found for personId: %d, documentId: %d", personId, documentId));
        }
    }

    public Optional<Assigner> findLastCompletedBy(int documentId) {
        return repository.findFirstByAndCompletedAndOrderOperationDesc(documentId);
    }

    public byte checkStatusBatchBy(int personId, String uuid) {
        List<Integer> identifiers = applicationService.getIdentifiers(uuid);
        if (identifiers.isEmpty()) {
            return States.INCOMPLETE;
        }
        List<Assigner> assigners = repository.findByIdentifiersAndPersonId(personId, identifiers);
        boolean incomplete = assigners.stream().anyMatch(assigner -> assigner.getCompleted() == States.INCOMPLETE);
        if (incomplete) {
            return States.INCOMPLETE;
        } else {
            applicationService.remove(uuid);
            return States.COMPLETED;
        }
    }

    public Assigner findBy(int personId, String hashIdentifier) {
        Optional<Assigner> optional = repository.findByPersonIdAndHashIdentifier(personId, hashIdentifier);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new AssignerNotFoundException(String.format("Assigner not found for personId: %d, hashIdentifier: %s", personId, hashIdentifier));
        }
    }

    public Optional<Assigner> findFirstBy(int documentId) {
        return repository.findFirstByAndOrderOperationAsc(documentId);
    }

    public Optional<Assigner> findBy(int personId, int documentId) {
        return repository.findByPersonIdAndDocumentId(personId, documentId);
    }

    public List<Assigner> getAllBy(Document document) {
        return repository.getAllBy(document);
    }

    public void invalidate(Assigner assigner) {
        AssignerAllocator.forDisable(assigner);
        this.save(assigner);
    }

    public Optional<Assigner> findBy(int operatorId){
        return repository.findBy(operatorId);
    }
}
