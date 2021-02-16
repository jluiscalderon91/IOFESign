package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DoneAllocator {

    public static Done build(Assigner assigner, Resource resource, byte observed, String comment, boolean allowedAddTsa) {
        return build(assigner, resource, observed, comment, allowedAddTsa, States.ACTIVE);
    }

    public static Done build(Assigner assigner, Resource resource, byte observed, String comment, boolean allowedAddTsa, byte active) {
        Done done = new Done();
        done.setAssignerId(assigner.getId());
        done.setResourceId(resource.getId());
        done.setObserved(observed);
        done.setComment(comment);
        done.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        done.setHasTsa(allowedAddTsa ? States.ADD_TSA : States.NOT_ADD_TSA);
        done.setActive(active);
        done.setDeleted(States.EXISTENT);
        return done;
    }
}
