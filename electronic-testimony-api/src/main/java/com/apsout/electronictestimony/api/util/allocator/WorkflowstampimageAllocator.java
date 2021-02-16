package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowstampimageAllocator {

    public static List<Workflowstampimage> build(Workflow workflow, List<Stampimage> stampimages) {
        return stampimages.stream().map(stampimage -> {
            Workflowstampimage workflowstampimage = new Workflowstampimage();
            workflowstampimage.setStampimageByStampimageId(stampimage);
            workflowstampimage.setStampimageId(stampimage.getId());
            workflowstampimage.setWorkflowByWorkflowId(workflow);
            workflowstampimage.setWorkflowId(workflow.getId());
            ofPostMethod(workflowstampimage);
            return workflowstampimage;
        }).collect(Collectors.toList());
    }

    public static void ofPostMethod(Workflowstampimage workflowstampimage) {
        workflowstampimage.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflowstampimage.setActive(States.ACTIVE);
        workflowstampimage.setDeleted(States.EXISTENT);
    }
}
