package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Notification;
import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.util.statics.RecipientType;
import com.apsout.electronictestimony.api.util.statics.Priority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NotificationAllocator {
    public static Notification build4MarkAsNotSent(Operator operator) {
        Notification notification = new Notification();
        notification.setOperatorId(operator.getId());
        notification.setOperatorByOperatorId(operator);
        notification.setEnabled(States.DISABLED);
        notification.setSent(States.NOT_SENT);
        notification.setPriority(Priority.MAX);
        int type = States.NOT_MANDATORY != operator.getMandatory() ? RecipientType.USER_AND_INVITEDUSER : RecipientType.INVITED;
        notification.setType(type);
        ofPostMethod(notification);
        return notification;
    }


    public static void forUpdate(Notification notification) {
        notification.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        notification.setSent(States.SENT);
        notification.setEnabled(States.ENABLED);
    }

    public static void forUpdateResend(Notification notification) {
        notification.setEnabled(States.DISABLED);
        notification.setSent(States.NOT_SENT);
        notification.setPriority(Priority.MAX);
    }

    public static void forReducePriority(Notification notification) {
        notification.setPriority(Priority.MIN);
    }

    public static void ofPostMethod(Notification notification) {
        notification.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        notification.setActive(States.ACTIVE);
        notification.setDeleted(States.EXISTENT);
    }

    public static Notification build4MarkAsSent(Operator operator) {
        Notification notification = new Notification();
        notification.setOperatorId(operator.getId());
        notification.setOperatorByOperatorId(operator);
        notification.setEnabled(States.DISABLED);
        notification.setSent(States.SENT);
        notification.setPriority(Priority.MAX);
        notification.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        int type = States.NOT_MANDATORY != operator.getMandatory() ? RecipientType.USER_AND_INVITEDUSER : RecipientType.INVITED;
        notification.setType(type);
        ofPostMethod(notification);
        return notification;
    }

    public static void forDisable(Notification notification) {
        notification.setActive(States.INACTIVE);
    }

}
