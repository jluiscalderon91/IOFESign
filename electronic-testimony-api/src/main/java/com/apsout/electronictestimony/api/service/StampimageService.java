package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stampimage;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface StampimageService {

    Stampimage save(Stampimage stampimage);

    List<Stampimage> save(List<Stampimage> stampimages);

    List<Stampimage> findByWorkflowId(int workflowId);

    List<Stampimage> findBy(Workflow workflow);

    Optional<Stampimage> findBy(int stampimageId);

    Stampimage getBy(int stampimageId);
}
