package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowstamprubricAllocator {

    public static List<Workflowstamprubric> build(Workflow workflow, List<Stamprubric> stamprubrics) {
        return stamprubrics.stream().map(stamprubric -> {
            Workflowstamprubric workflowstamprubric = new Workflowstamprubric();
            workflowstamprubric.setStamprubricByStamprubricId(stamprubric);
            workflowstamprubric.setStamprubricId(stamprubric.getId());
            workflowstamprubric.setWorkflowByWorkflowId(workflow);
            workflowstamprubric.setWorkflowId(workflow.getId());
            ofPostMethod(workflowstamprubric);
            return workflowstamprubric;
        }).collect(Collectors.toList());
    }

    public static void ofPostMethod(Workflowstamprubric workflowstamprubric) {
        workflowstamprubric.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflowstamprubric.setActive(States.ACTIVE);
        workflowstamprubric.setDeleted(States.EXISTENT);
    }
}
