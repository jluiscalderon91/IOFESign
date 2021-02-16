package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.Sienotification;
import com.apsout.electronictestimony.api.util.statics.Priority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SienotificationAllocator {
    public static Sienotification build4MarkAsNotSent(Operator operator) {
        Sienotification sienotification = new Sienotification();
        sienotification.setOperatorId(operator.getId());
        sienotification.setOperatorByOperatorId(operator);
        sienotification.setSent(States.NOT_SENT);
        sienotification.setPriority(Priority.MAX);
        ofPostMethod(sienotification);
        return sienotification;
    }


    public static void forUpdate(Sienotification notification) {
        notification.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        notification.setSent(States.SENT);
    }

    public static void forReducePriority(Sienotification sienotification) {
        reducePriority(sienotification);
    }

    public static void ofPostMethod(Sienotification sienotification) {
        sienotification.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        sienotification.setActive(States.ACTIVE);
        sienotification.setDeleted(States.EXISTENT);
    }

    private static void reducePriority(Sienotification sienotification) {
        final int priority = sienotification.getPriority();
        final int newPriority = priority + 1;
        sienotification.setPriority(newPriority);
    }
}
