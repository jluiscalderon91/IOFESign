package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Operation;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowtype;
import com.apsout.electronictestimony.api.entity.common.MoreAboutWorkflow;
import com.apsout.electronictestimony.api.util.statics.Constant;
import com.apsout.electronictestimony.api.util.statics.OperationType;
import com.apsout.electronictestimony.api.util.statics.SignatureType;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class WorkflowAllocator {
    public static void ofPostMethod(Workflow workflow) {
        workflow.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflow.setActive(States.ACTIVE);
        workflow.setDeleted(States.EXISTENT);
    }

    public static Workflow build(Workflow workflow, Enterprise enterprise, Workflowtype workflowtype) {
        workflow.setEnterpriseId(enterprise.getId());
        workflow.setType(workflowtype.getId());
        if (workflow.getDynamic() == States.DYNAMICALLY) {
            workflow.setReady2Use(States.READY_TO_USE);
        }
        ofPostMethod(workflow);
        return workflow;
    }

    public static void forUpdate(Workflow workflowDb, Workflow workflow, Workflowtype workflowtype) {
        workflowDb.setDescription(workflow.getDescription());
        workflowDb.setMaxParticipants(workflow.getMaxParticipants());
        workflowDb.setBatch(workflow.getBatch());
        workflowDb.setDeliver(workflow.getDeliver());
        workflowDb.setCode(workflow.getCode());
        workflowDb.setActive(workflow.getActive());
        workflowDb.setType(workflowtype.getId());
        workflowDb.setDynamic(workflow.getDynamic());
        if (workflow.getDynamic() == States.DYNAMICALLY) {
            workflowDb.setReady2Use(States.READY_TO_USE);
            workflowDb.setDynamic(workflow.getDynamic());
            workflowDb.setBatch(SignatureType.ONE2ONE);
        }
    }

    public static void forDeleted(Workflow workflow) {
        workflow.setDeleted(States.DELETED);
    }

    public static void loadMoreInfo(MoreAboutWorkflow moreAboutWorkflow, Enterprise enterprise, byte sieConfigured, byte requiredSieConfig) {
        moreAboutWorkflow.setEnterpriseTradeName(enterprise.getTradeName());
        moreAboutWorkflow.setSieConfigured(sieConfigured);
        moreAboutWorkflow.setRequiredSieConfig(requiredSieConfig);
    }

    public static void forReturn(Workflow workflow) {
        workflow.setBatch(null);
        workflow.setCompleted(null);
        workflow.setCreateAt(null);
        workflow.setActive(null);
        workflow.setDeleted(null);
        workflow.setObservation(null);
        workflow.setDeliver(null);
        workflow.setCode(null);
    }

    public static void forReturn(List<Workflow> workflows) {
        workflows.stream().forEach(WorkflowAllocator::forReturn);
    }

    public static void forUpdateCompleted(Workflow workflow, byte completed) {
        workflow.setCompleted(completed);
    }

    public static void forUpdateReady2Use(Workflow workflow, byte ready2Use) {
        workflow.setReady2Use(ready2Use);
    }

    public static void forReturn2Station(Workflow workflow) {
        workflow.setDynamic(null);
    }

    public static void forReturn2Station(List<Workflow> workflows) {
        workflows.stream().forEach(WorkflowAllocator::forReturn2Station);
    }
}
