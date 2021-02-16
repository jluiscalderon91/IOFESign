package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DetailbalanceallocationAllocator {

    public static Detailbalanceallocation build(Headbalanceallocation headbalanceallocation, Document document, Person person, Serviceweight serviceweight) {
        Detailbalanceallocation detailbalanceallocation = new Detailbalanceallocation();
        detailbalanceallocation.setHeadbalanceallocationId(headbalanceallocation.getId());
        detailbalanceallocation.setDocumentId(document.getId());
        detailbalanceallocation.setPersonId(person.getId());
        detailbalanceallocation.setServiceweightId(serviceweight.getId());
        detailbalanceallocation.setWeight(serviceweight.getWeight());
        detailbalanceallocation.setOldBalance(headbalanceallocation.getBalance());
        ofPostMethod(detailbalanceallocation);
        return detailbalanceallocation;
    }

    public static void forUpdate(Detailbalanceallocation detailbalanceallocation, BigDecimal balance) {
        detailbalanceallocation.setActualBalance(balance);
    }

    public static void ofPostMethod(Detailbalanceallocation detailbalanceallocation) {
        detailbalanceallocation.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        detailbalanceallocation.setActive(States.ACTIVE);
        detailbalanceallocation.setDeleted(States.EXISTENT);
    }
}
