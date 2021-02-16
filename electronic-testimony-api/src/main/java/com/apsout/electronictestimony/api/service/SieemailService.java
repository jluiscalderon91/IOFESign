package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface SieemailService {
    Sieemail save(Sieemail sieemail);

    Sieemail onlySave(Sieemail sieemail);

    Optional<Sieemail> findByParticipantId(int participantId);

    Optional<Sieemail> findBy(Participant participant);

    Optional<Sieemail> findBy(int sieemailId);

    Sieemail getBy(int sieemailId);

    Sieemail update(Sieemail sieemail);

    Optional<Sieemail> findByWorkflow(Workflow workflow);

    Optional<Sieemail> findByWorkflow(int workflowId);

    List<Sieemail> findByParticipantIds(List<Integer> participantIds);
}
