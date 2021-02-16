package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Deliverymail;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.Priority;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DeliverymailAllocator {

    public static Deliverymail build(Document document) {
        Deliverymail deliverymail = new Deliverymail();
        deliverymail.setDocumentId(document.getId());
        deliverymail.setSent(Boolean.FALSE);
        deliverymail.setPriority(Priority.MAX);
        ofPostMethod(deliverymail);
        return deliverymail;
    }

    public static void ofPostMethod(Deliverymail deliverymail) {
        deliverymail.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        deliverymail.setActive(Boolean.TRUE);
        deliverymail.setDeleted(Boolean.FALSE);
    }

    public static void forUpdate(Deliverymail deliverymail) {
        deliverymail.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        deliverymail.setSent(Boolean.TRUE);
        deliverymail.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void forReducePriority(Deliverymail deliverymail) {
        if (deliverymail.getPriority() == Default.MAX_ATTEMPTS_DELIVERYMAIL - 1) {
            deliverymail.setActive(Boolean.FALSE);
        } else {
            reducePriority(deliverymail);
        }
        deliverymail.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    private static void reducePriority(Deliverymail deliverymail) {
        final int priority = deliverymail.getPriority();
        final int newPriority = priority + 1;
        deliverymail.setPriority(newPriority);
    }
}
