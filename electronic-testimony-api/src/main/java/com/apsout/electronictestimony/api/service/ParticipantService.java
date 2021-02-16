package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.model.pojo._GroupParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {

    Participant onlySave(Participant participant);

    List<Participant> save(_GroupParticipant groupParticipant);

    List<Participant> getAllBy(int workflowId);

    List<Participant> getAllBy(Workflow workflow);

    Page<Participant> getAllBy(int workflowId, Pageable pageable);

    List<Participant> update(_GroupParticipant groupParticipant);

    Participant delete(Participant participant);

    Participant getBy(int participantId);

    Optional<Participant> findBy(int workflowId, int personId, int orderParticipant);

    Optional<Participant> findBy(int workflowId, int orderParticipant);

    Participant getBy(int workflowId, int orderParticipant);

    List<Participant> getAllBy(int workflowId, boolean onlyReplaceable);

    List<Participant> getAllReplaceablesBy4Outside(int workflowId);

    Participant updateSieConfig(Participant participant, byte sieConfigured);

    List<Operator> update(int documentId, String reasignIdentifiers);
}
