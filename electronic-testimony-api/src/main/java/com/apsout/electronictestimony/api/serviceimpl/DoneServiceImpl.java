package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Done;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.repository.DoneRepository;
import com.apsout.electronictestimony.api.service.AssignerService;
import com.apsout.electronictestimony.api.service.DoneService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoneServiceImpl implements DoneService {

    @Autowired
    private DoneRepository repository;
    @Autowired
    private AssignerService assignerService;

    @Override
    @Transactional
    public Done save(Done done) {
        return repository.save(done);
    }

    @Override
    public Optional<Done> getBy(Resource resource) {
        return repository.findById(resource.getId());
    }

    @Override
    public Optional<Done> getLastBy(int documentId) {
        Optional<Assigner> optional = assignerService.findLastCompletedBy(documentId);
        if (optional.isPresent()) {
            Assigner assigner = optional.get();
            return repository.findByAssignerIdAndActiveAndDeleted(assigner.getId(), States.ACTIVE, States.EXISTENT);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Done> getLastBy(int documentId, String hashResource) {
        Optional<Assigner> optional = assignerService.findLastCompletedBy(documentId);
        if (optional.isPresent()) {
            Assigner assigner = optional.get();
            return repository.findByAssignerIdAndHashResource(assigner.getId(), hashResource);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Done> getLastBy(int documentId, int orderResource) {
        Optional<Assigner> optional = assignerService.findLastCompletedBy(documentId);
        if (optional.isPresent()) {
            Assigner assigner = optional.get();
            return repository.findByAssignerIdAndOrderResource(assigner.getId(), orderResource);
        } else {
            return Optional.empty();
        }
    }

    public List<Done> findByFinishedDocument(int documentId, List<Integer> operationIds) {
        return repository.findByFinishedDocument(documentId, operationIds);
    }

    public List<Done> findBy(int documentId, int operationId) {
        return repository.findBy(documentId, operationId);
    }

    public List<Done> findAllBy(int documentId) {
        final List<Done> donesByDocument = repository.findAllByDocumentId(documentId);
        List<Done> filteredDone = new ArrayList<>();
        donesByDocument.stream().forEach(done -> {
            boolean founded = false;
            for (Done done1 : filteredDone) {
                if (done.getAssignerByAssignerId() == done1.getAssignerByAssignerId()) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                filteredDone.add(done);
            }
        });
        return filteredDone;
    }

    public Optional<Done> findUniqueBy(Assigner assigner) {
        return repository.findUniqueBy(assigner.getId());
    }
}
