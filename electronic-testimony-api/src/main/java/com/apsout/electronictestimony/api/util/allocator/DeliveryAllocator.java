package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Delivery;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.Priority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DeliveryAllocator {

    public static Delivery build(Document document) {
        Delivery delivery = new Delivery();
        delivery.setPriority(Priority.MAX);
        delivery.setSent(States.NOT_SENT);
        delivery.setDocumentByDocumentId(document);
        delivery.setDocumentId(document.getId());
        ofPostMethod(delivery);
        return delivery;
    }

    public static void forUpdate(Delivery delivery, String requestBody, String response, String responseCode, String responseDescription) {
        delivery.setSent(States.SENT);
        delivery.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        delivery.setRequestBody(requestBody);
        delivery.setResponse(response);
        delivery.setResponseCode(responseCode);
        delivery.setResponseDescription(responseDescription);
        delivery.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void forUpdate(Delivery delivery, String response, String responseCode, String responseDescription) {
        forUpdate(delivery, null, response, responseCode, responseDescription);
    }

    public static void forReducePriority(Delivery delivery, String responseCode, String responseDescription) {
        if (delivery.getPriority() == Default.MAX_ATTEMPTS - 1) {
            delivery.setActive(States.INACTIVE);
        } else {
            reducePriority(delivery);
        }
        delivery.setResponseCode(responseCode);
        delivery.setResponseDescription(responseDescription);
        delivery.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    private static void reducePriority(Delivery delivery) {
        final int priority = delivery.getPriority();
        final int newPriority = priority + 1;
        delivery.setPriority(newPriority);
    }

    public static void ofPostMethod(Delivery delivery) {
        delivery.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        delivery.setActive(States.ACTIVE);
        delivery.setDeleted(States.EXISTENT);
    }
}
