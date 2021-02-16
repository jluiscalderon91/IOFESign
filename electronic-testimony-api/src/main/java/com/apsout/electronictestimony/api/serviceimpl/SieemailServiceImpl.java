package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Sierecipient;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.exception.SieemailNotFoundException;
import com.apsout.electronictestimony.api.repository.SieemailRepository;
import com.apsout.electronictestimony.api.service.ParticipantService;
import com.apsout.electronictestimony.api.service.SieemailService;
import com.apsout.electronictestimony.api.service.SierecipientService;
import com.apsout.electronictestimony.api.util.allocator.SieemailAllocator;
import com.apsout.electronictestimony.api.util.allocator.SierecipientAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SieemailServiceImpl implements SieemailService {
    private static final Logger logger = LoggerFactory.getLogger(SieemailServiceImpl.class);

    @Autowired
    private SieemailRepository repository;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private SierecipientService sierecipientService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Sieemail save(Sieemail sieemail) {
        int participantId = sieemail.getParticipantId();
        Participant participant = participantService.getBy(participantId);
        Sieemail sieemail1 = SieemailAllocator.build(participant, sieemail);
        this.onlySave(sieemail1);
        List<Sierecipient> sierecipiens = SierecipientAllocator.build(sieemail1);
        sierecipientService.save(sierecipiens);
        updateSieConfigState(participant, sierecipiens);
        logger.info(String.format("Sieemail saved with sieemailId: %d", sieemail1.getId()));
        return sieemail;
    }

    private void updateSieConfigState(Participant participant, List<Sierecipient> sierecipients) {
        final boolean sieConfiguredCorrectly = isSieConfiguredCorrectly(sierecipients);
        if (sieConfiguredCorrectly) {
            participantService.updateSieConfig(participant, States.SIE_CONFIGURED);
        } else {
            participantService.updateSieConfig(participant, States.SIE_NOT_CONFIGURED);
        }
    }

    private boolean isSieConfiguredCorrectly(List<Sierecipient> sierecipients) {
        return !sierecipients.isEmpty();
    }

    @Override
    public Sieemail onlySave(Sieemail sieemail) {
        return repository.save(sieemail);
    }

    @Override
    public Optional<Sieemail> findByParticipantId(int participantId) {
        Participant participant = participantService.getBy(participantId);
        return repository.findBy(participant);
    }

    @Override
    public Optional<Sieemail> findBy(Participant participant) {
        return repository.findBy(participant);
    }

    @Override
    public Optional<Sieemail> findBy(int sieemailId) {
        return repository.findBy(sieemailId);
    }

    @Override
    public Sieemail getBy(int sieemailId) {
        Optional<Sieemail> optional = findBy(sieemailId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new SieemailNotFoundException(String.format("Sieemail not found for sieemailId: %d", sieemailId));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Sieemail update(Sieemail sieemail) {
        Sieemail sieemaildb = this.getBy(sieemail.getId());
        int participantId = sieemail.getParticipantId();
        Participant participant = participantService.getBy(participantId);
        SieemailAllocator.forUpdate(sieemaildb, sieemail);
        this.onlySave(sieemaildb);
        SierecipientAllocator.inactiveOf(sieemaildb);
        sierecipientService.save(sieemaildb);
        List<Sierecipient> newSierecipients = SierecipientAllocator.build(sieemail);
        sierecipientService.update(newSierecipients);
        updateSieConfigState(participant, newSierecipients);
        logger.info(String.format("Sieemails updated with sieemailId: %d", sieemail.getId()));
        return sieemail;
    }

    public Optional<Sieemail> findByWorkflow(Workflow workflow) {
        return findByWorkflow(workflow.getId());
    }

    public Optional<Sieemail> findByWorkflow(int workflowId) {
        List<Participant> participants = participantService.getAllBy(workflowId);
        List<Integer> participantIds = participants.stream().map(Participant::getId).collect(Collectors.toList());
        List<Sieemail> sieemails = findByParticipantIds(participantIds);
        if (!sieemails.isEmpty()) {
            return sieemails.stream().findFirst();
        }
        logger.warn(String.format("Sieemail not found for workflowId: %s", workflowId));
        return Optional.empty();
    }

    public List<Sieemail> findByParticipantIds(List<Integer> participantIds) {
        return repository.findParticipants(participantIds);
    }
}
