package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface StampdatetimeService {

    Stampdatetime save(Stampdatetime stampdatetime);

    List<Stampdatetime> save(List<Stampdatetime> stampdatetimes);

    List<Stampdatetime> findByWorkflowId(int workflowId);

    List<Stampdatetime> findBy(Workflow workflow);

    Optional<Stampdatetime> findFirstBy(Workflow workflow);

    Optional<Stampdatetime> findBy(int stampdatetimeId);

    Stampdatetime getBy(int stampdatetimeId);
}
