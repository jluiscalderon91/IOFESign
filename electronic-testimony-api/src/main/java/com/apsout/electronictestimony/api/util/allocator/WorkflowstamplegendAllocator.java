package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Stamplegend;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.Workflowstamplegend;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowstamplegendAllocator {

    public static List<Workflowstamplegend> build(Workflow workflow, List<Stamplegend> stamplegends) {
        return stamplegends.stream().map(stamplegend -> {
            Workflowstamplegend workflowstamplegend = new Workflowstamplegend();
            workflowstamplegend.setStamplegendByStamplegendId(stamplegend);
            workflowstamplegend.setStamplegendId(stamplegend.getId());
            workflowstamplegend.setWorkflowByWorkflowId(workflow);
            workflowstamplegend.setWorkflowId(workflow.getId());
            ofPostMethod(workflowstamplegend);
            return workflowstamplegend;
        }).collect(Collectors.toList());
    }

    public static void ofPostMethod(Workflowstamplegend workflowstamplegend) {
        workflowstamplegend.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflowstamplegend.setActive(States.ACTIVE);
        workflowstamplegend.setDeleted(States.EXISTENT);
    }
}
