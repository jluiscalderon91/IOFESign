package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AssignerAllocator {

    public static Assigner build(Document document, Operator operator) {
        return build(document, operator, null);
    }

    public static Assigner build2(Document document, Operator operator, int dato1) {
        return build(document, operator, null, dato1);
    }

    public static Assigner build(Document document, Operator operator, Assigner oldAssigner) {
        Assigner assigner = new Assigner();
        assigner.setOperatorId(operator.getId());
        assigner.setDocumentId(document.getId());
        assigner.setCompleted(States.INCOMPLETE);
        if (oldAssigner == null)
            assigner.setOrderOperation(1);
        else
            assigner.setOrderOperation(oldAssigner.getOrderOperation() + 1);
        ofPostMethod(assigner);
        return assigner;
    }

    public static Assigner build(Document document, Operator operator, Assigner oldAssigner, int dato1) {
        Assigner assigner = new Assigner();
        assigner.setOperatorId(operator.getId());
        assigner.setDocumentId(document.getId());
        assigner.setCompleted(States.INCOMPLETE);
        assigner.setOrderOperation(dato1);
        ofPostMethod(assigner);
        return assigner;
    }

    public static void ofPostMethod(Assigner assigner) {
        assigner.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        assigner.setActive(States.ACTIVE);
        assigner.setDeleted(States.EXISTENT);
    }

    public static void forDisable(Assigner assigner) {
        assigner.setActive(States.INACTIVE);
    }
}
