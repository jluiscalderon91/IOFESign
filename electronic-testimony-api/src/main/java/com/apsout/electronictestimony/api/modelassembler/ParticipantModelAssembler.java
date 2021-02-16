package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.ParticipantController;
import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.model.ParticipantModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ParticipantModelAssembler extends RepresentationModelAssemblerSupport<Participant, ParticipantModel> {

    public ParticipantModelAssembler() {
        super(ParticipantController.class, ParticipantModel.class);
    }

    @Override
    public ParticipantModel toModel(Participant participant) {
        ParticipantModel participantModel = instantiateModel(participant);
        participantModel.add(linkTo(methodOn(ParticipantController.class).getAllBy(participant.getWorkflowId(), null, null)).withSelfRel());
        participantModel.setId(participant.getId());
        participantModel.setWorkflowId(participant.getWorkflowId());
        participantModel.setOperationId(participant.getOperationId());
        participantModel.setPersonId(participant.getPersonId());
        participantModel.setParticipantType(participant.getParticipantType());
        participantModel.setOrderParticipant(participant.getOrderParticipant());
        participantModel.setUploadRubric(participant.getUploadRubric());
        participantModel.setDigitalSignature(participant.getDigitalSignature());
        participantModel.setTypeElectronicSignature(participant.getTypeElectronicSignature());
        participantModel.setCreateAt(participant.getCreateAt());
        participantModel.setActive(participant.getActive());
        participantModel.setAddTsa(participant.getAddTsa());
        participantModel.setSendNotification(participant.getSendNotification());
        participantModel.setSendAlert(participant.getSendAlert());
        participantModel.setObservation(participant.getObservation());
        participantModel.set_more(participant.getMoreAboutParticipant());
        return participantModel;
    }
}
