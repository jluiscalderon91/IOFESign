package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowstampqrcodeAllocator {

    public static List<Workflowstampqrcode> build(Workflow workflow, List<Stampqrcode> stampqrcodes) {
        return stampqrcodes.stream().map(stampqrcode -> {
            Workflowstampqrcode workflowstampqrcode = new Workflowstampqrcode();
            workflowstampqrcode.setStampqrcodeByStampqrcodeId(stampqrcode);
            workflowstampqrcode.setStampqrcodeId(stampqrcode.getId());
            workflowstampqrcode.setWorkflowByWorkflowId(workflow);
            workflowstampqrcode.setWorkflowId(workflow.getId());
            ofPostMethod(workflowstampqrcode);
            return workflowstampqrcode;
        }).collect(Collectors.toList());
    }

    public static void ofPostMethod(Workflowstampqrcode workflowstampqrcode) {
        workflowstampqrcode.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        workflowstampqrcode.setActive(States.ACTIVE);
        workflowstampqrcode.setDeleted(States.EXISTENT);
    }
}
