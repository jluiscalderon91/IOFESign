package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowstampdatetimeAllocator {

    public static List<Workflowstampdatetime> build(Workflow workflow, List<Stampdatetime> stampdatetimes) {
        return stampdatetimes.stream().map(stampdatetime -> {
            Workflowstampdatetime workflowstampdatetime = new Workflowstampdatetime();
            workflowstampdatetime.setStampdatetimeByStampdatetimeId(stampdatetime);
            workflowstampdatetime.setStampdatetimeId(stampdatetime.getId());
            workflowstampdatetime.setWorkflowByWorkflowId(workflow);
            workflowstampdatetime.setWorkflowId(workflow.getId());
            ofPostMethod(workflowstampdatetime);
            return workflowstampdatetime;
        }).collect(Collectors.toList());
    }

    public static void ofPostMethod(Workflowstampdatetime workflowstampdatetime) {
        workflowstampdatetime.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflowstampdatetime.setActive(States.ACTIVE);
        workflowstampdatetime.setDeleted(States.EXISTENT);
    }
}
