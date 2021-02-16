package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stamplayoutfile;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.io.File;
import java.util.Optional;

public interface StamplayoutfileService {

    Stamplayoutfile save(Stamplayoutfile stamplayoutfile);

    Stamplayoutfile getByWorkflowId(int workflowId);

    Optional<Stamplayoutfile> findByWorkflowId(int workflowId);

    Stamplayoutfile getBy(Workflow workflow);

    Optional<Stamplayoutfile> findBy(Workflow workflow);

    Optional<Stamplayoutfile> findBy(int stamplayoutfileId);

    Stamplayoutfile getBy(int stamplayoutfileId);

    File buildUniqueFile(Stamplayoutfile stamplayoutfile);
}
