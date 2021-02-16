package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkflowService {

    List<Workflow> findAllBy(int enterpriseId);

    List<Workflow> findAll();

    Page<Workflow> findAllBy(int partnerId, int enterpriseId, String term2Search, Pageable pageable);

    Workflow save(Workflow workflow);

    boolean ready2Use(Workflow workflow);

    Workflow onlySave(Workflow workflow);

    Workflow update(Workflow workflow);

    Workflow delete(Workflow workflow);

    Workflow getBy(int workflowId);

    Optional<Workflow> findBy(int workflowId);

    List<Workflow> findAllWhereIsAssigned(int personId);

    List<Workflow> findAllWhereIsParticipant(int personId);

    List<Workflow> findAllWhereIsParticipantOrAssigned(Person person);

    List<Workflow> findAllWhereIsParticipantOrAssigned(int personId);

    List<Workflow> findAllBy4Outside(int enterpriseId);

    Workflow saveTemplateDesign(_WorkflowTemplateDesign workflowTemplateDesign);

    Workflow updateTemplateDesign(_WorkflowTemplateDesign workflowTemplateDesign);

    List<Workflow> findAllBy(List<Integer> enterpriseIds);
}
