package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Deliverymail;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ContentdeliverymailAllocator {

    public static void build(Deliverymail deliverymail, Contentdeliverymail contentdeliverymail) {
        contentdeliverymail.setDeliverymailId(deliverymail.getId());
        contentdeliverymail.setRecipient(contentdeliverymail.getRecipient() != null ? contentdeliverymail.getRecipient().trim() : null);
        contentdeliverymail.setCc(contentdeliverymail.getCc() != null ? contentdeliverymail.getCc().trim() : null);
        contentdeliverymail.setSubject(contentdeliverymail.getSubject() != null ? contentdeliverymail.getSubject().trim() : null);
        ofPostMethod(contentdeliverymail);
    }

    public static void ofPostMethod(Contentdeliverymail contentdeliverymail) {
        contentdeliverymail.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        contentdeliverymail.setActive(Boolean.TRUE);
        contentdeliverymail.setDeleted(Boolean.FALSE);
    }
}
