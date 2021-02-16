package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.ParticipantType;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OperatorAllocator {

    public static Operator build(Enterprise enterprise, Person person, Participant participant, Operation operation, Document document) {
        Operator operator = new Operator();
        operator.setEnterpriseId(enterprise.getId());
        operator.setEnterpriseByEnterpriseId(enterprise);
        operator.setPersonId(person.getId());
        operator.setPersonByPersonId(person);
        operator.setOperationId(operation.getId());
        operator.setOperationByOperationId(operation);
        operator.setDocumentId(document.getId());
        operator.setDocumentByDocumentId(document);
        operator.setOrderOperation(participant.getOrderParticipant());
        int participantType = participant.getParticipantType();
        if (ParticipantType.USER == participantType || ParticipantType.INVITED_USER == participantType) {
            operator.setMandatory(States.MANDATORY);
        } else {
            operator.setMandatory(States.NOT_MANDATORY);
        }
        operator.setAddTsa(participant.getAddTsa());
        operator.setSendNotification(participant.getSendNotification());
        operator.setSendAlert(participant.getSendAlert());
        operator.setUploadRubric(participant.getUploadRubric());
        operator.setDigitalSignature(participant.getDigitalSignature());
        operator.setTypeElectronicSignature(participant.getTypeElectronicSignature());
        ofPostMethod(operator);
        return operator;
    }

    public static Operator build(Enterprise enterprise, Person person, Operation operation, Document document, int orderOperation, byte addTsa) {
        Operator operator = new Operator();
        operator.setEnterpriseId(enterprise.getId());
        operator.setEnterpriseByEnterpriseId(enterprise);
        operator.setPersonId(person.getId());
        operator.setPersonByPersonId(person);
        operator.setOperationId(operation.getId());
        operator.setOperationByOperationId(operation);
        operator.setDocumentId(document.getId());
        operator.setDocumentByDocumentId(document);
        operator.setOrderOperation(orderOperation);
        operator.setMandatory(States.NOT_MANDATORY);
        operator.setAddTsa(addTsa);
        operator.setSendNotification(States.NOT_SEND_SIE_NOTIFICATION);
        operator.setSendAlert(States.SEND_ALERT);
        ofPostMethod(operator);
        return operator;
    }

    public static void ofPostMethod(Operator operator) {
        operator.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        operator.setActive(States.ACTIVE);
        operator.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Operator operatorDb, Operator oldOperator) {
        operatorDb.setOrderOperation(oldOperator.getOrderOperation());
        operatorDb.setOperationId(oldOperator.getOperationId());
        operatorDb.setActive(oldOperator.getActive());
    }


    public static void forUpdate2(Operator operator, int orderOperation) {
        operator.setOrderOperation(orderOperation);
    }
}
