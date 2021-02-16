package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Workflowtype;

import java.util.List;
import java.util.Optional;

public interface WorkflowtypeService {

    List<Workflowtype> findInitialAll();

    List<Workflowtype> findAll();

    Optional<Workflowtype> findBy(int workflowtypeId);

    Workflowtype getBy(int workflowtypeId);
}
