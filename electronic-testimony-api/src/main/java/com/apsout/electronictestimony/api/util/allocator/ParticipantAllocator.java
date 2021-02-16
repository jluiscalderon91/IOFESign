package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutParticipant;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.apsout.electronictestimony.api.util.statics.ParticipantType.INVITED;

public class ParticipantAllocator {
    public static void ofPostMethod(Participant participant) {
        participant.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        participant.setActive(States.ACTIVE);
        participant.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Participant participantDb, Participant participant) {
        participantDb.setOperationId(participant.getOperationId());
        participantDb.setPersonId(participant.getPersonId());
        participantDb.setOrderParticipant(participant.getOrderParticipant());
        participantDb.setParticipantType(participant.getParticipantType());
        participantDb.setAddTsa(participant.getAddTsa());
        participantDb.setSendAlert(participant.getSendAlert());
        participantDb.setSendNotification(participant.getSendNotification());
        participantDb.setActive(participant.getActive());
        participantDb.setUploadRubric(participant.getUploadRubric());
        participantDb.setDigitalSignature(participant.getDigitalSignature());
    }

    public static Participant build(Workflow workflow, Participant participant) {
        if (participant.getParticipantType().equals(INVITED)) {
            participant.setPersonId(Default.PERSON_ID);
        }
        //TODO cuando se envíe todo el conjunto de datos de los recipientes del SIE, debería ser evaluado
        participant.setWorkflowId(workflow.getId());
        participant.setWorkflowByWorkflowId(workflow);
        participant.setSieConfigured(States.SIE_NOT_CONFIGURED);
        ofPostMethod(participant);
        return participant;
    }

    public static void forDelete(Participant participant) {
        participant.setDeleted(States.DELETED);
    }

    public static void loadMoreInfo(MoreAboutParticipant moreAboutParticipant, Workflow workflow, Operation operation, Person person) {
        moreAboutParticipant.setWorkflowDescription(workflow.getDescription());
        moreAboutParticipant.setMaxParticipants(workflow.getMaxParticipants());
        moreAboutParticipant.setOperationDescription(operation.getDescription());
        moreAboutParticipant.setPersonFullName(person.getFullname());
    }

    public static void loadMoreInfo(MoreAboutParticipant moreAboutParticipant, Workflow workflow, Operation operation, Person person, Job job) {
        loadMoreInfo(moreAboutParticipant, workflow, operation, person);
        moreAboutParticipant.setJobDescription(job.getDescription());
    }

    public static void forReturn(Participant participant) {
        participant.setId(null);
        participant.setWorkflowId(null);
        participant.setOperationId(null);
        participant.setPersonId(participant.getPersonId());
        participant.setOrderParticipant(participant.getOrderParticipant());
        participant.setAddTsa(null);
        participant.setSendAlert(null);
        participant.setSendNotification(null);
        participant.setActive(null);
        participant.setDeleted(null);
        participant.setWorkflowByWorkflowId(null);
        participant.setCreateAt(null);
        participant.setPersonByPersonId(null);
        participant.setOperationByOperationId(null);
        participant.setParticipantType(null);
    }

    public static void forReturn(List<Participant> participants) {
        participants.stream().forEach(ParticipantAllocator::forReturn);
    }

    public static void forSieConfig(Participant participant, byte sieConfigured) {
        participant.setSieConfigured(sieConfigured);
    }
}
