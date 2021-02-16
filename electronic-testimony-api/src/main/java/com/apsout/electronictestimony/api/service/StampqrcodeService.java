package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stampimage;
import com.apsout.electronictestimony.api.entity.Stampqrcode;
import com.apsout.electronictestimony.api.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface StampqrcodeService {

    Stampqrcode save(Stampqrcode stampqrcode);

    List<Stampqrcode> save(List<Stampqrcode> stampqrcodes);

    List<Stampqrcode> findByWorkflowId(int workflowId);

    List<Stampqrcode> findBy(Workflow workflow);

    Optional<Stampqrcode> findBy(int stampqrcodeId);

    Stampqrcode getBy(int stampqrcodeId);
}
