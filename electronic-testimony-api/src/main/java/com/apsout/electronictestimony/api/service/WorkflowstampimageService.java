package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;

import java.util.List;
import java.util.Optional;

public interface WorkflowstampimageService {

    Workflowstampimage save(Workflowstampimage workflowstampimage);

    List<Workflowstampimage> save(List<Workflowstampimage> workflowstampimages);

    Optional<Workflowstampimage> findBy(Workflow workflow, Stampimage stampimage);
}
