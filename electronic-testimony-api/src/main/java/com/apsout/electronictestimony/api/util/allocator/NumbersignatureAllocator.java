package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NumbersignatureAllocator {

    public static Numbersignature build(Document document, Resource resource, int quantity) {
        Numbersignature numbersignature = new Numbersignature();
        numbersignature.setDocumentId(document.getId());
        numbersignature.setResourceId(resource.getId());
        numbersignature.setHashResource(resource.getHash());
        numbersignature.setQuantity(quantity);
        ofPostMethod(numbersignature);
        return numbersignature;
    }

    public static void ofPostMethod(Numbersignature numbersignature) {
        numbersignature.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        numbersignature.setActive(States.ACTIVE);
        numbersignature.setDeleted(States.EXISTENT);
    }
}
