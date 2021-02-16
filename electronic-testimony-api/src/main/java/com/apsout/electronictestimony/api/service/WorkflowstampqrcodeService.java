package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;

import java.util.List;
import java.util.Optional;

public interface WorkflowstampqrcodeService {

    Workflowstampqrcode save(Workflowstampqrcode workflowstampqrcode);

    List<Workflowstampqrcode> save(List<Workflowstampqrcode> workflowstampqrcodes);

    Optional<Workflowstampqrcode> findBy(Workflow workflow, Stampqrcode stampqrcode);
}
