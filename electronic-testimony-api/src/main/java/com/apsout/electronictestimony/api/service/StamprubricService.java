package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Stamprubric;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface StamprubricService {

    Stamprubric save(Stamprubric stamprubric);

    List<Stamprubric> save(List<Stamprubric> stampimages);

    List<Stamprubric> findByWorkflowId(int workflowId);

    List<Stamprubric> findBy(Workflow workflow);

    Optional<Stamprubric> findBy(int stamprubricId);

    Stamprubric getBy(int stamprubricId);

    Optional<Stamprubric> findBy(Participant participant);

    Optional<Stamprubric> findByParticipantId(int participantId);

    Optional<Stamprubric> findBy(int personId, int documentId);
}
