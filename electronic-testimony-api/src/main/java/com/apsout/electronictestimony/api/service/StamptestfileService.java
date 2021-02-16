package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stamptestfile;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.Optional;

public interface StamptestfileService {

    Stamptestfile save(Stamptestfile stamptestfile);

    Stamptestfile getByWorkflowId(int workflowId);

    Optional<Stamptestfile> findByWorkflowId(int workflowId);

    Stamptestfile getBy(Workflow workflow);

    Optional<Stamptestfile> findBy(Workflow workflow);

    Optional<Stamptestfile> findBy(int stamptestfileId);

    Stamptestfile getBy(int stamptestfileId);
}
