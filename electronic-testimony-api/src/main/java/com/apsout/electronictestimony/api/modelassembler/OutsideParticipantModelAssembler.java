package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.ParticipantController;
import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.model.ParticipantModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class OutsideParticipantModelAssembler extends RepresentationModelAssemblerSupport<Participant, ParticipantModel> {

    public OutsideParticipantModelAssembler() {
        super(ParticipantController.class, ParticipantModel.class);
    }

    @Override
    public ParticipantModel toModel(Participant participant) {
        ParticipantModel participantModel = instantiateModel(participant);
        participantModel.setOrderParticipant(participant.getOrderParticipant());
        return participantModel;
    }
}
