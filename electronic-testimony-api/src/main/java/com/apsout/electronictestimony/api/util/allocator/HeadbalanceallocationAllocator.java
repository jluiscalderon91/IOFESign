package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.util.statics.States;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HeadbalanceallocationAllocator {

    public static Headbalanceallocation build(int enterpriseId) {
        Headbalanceallocation headbalanceallocation = new Headbalanceallocation();
        headbalanceallocation.setEnterpriseId(enterpriseId);
        ofPostMethod(headbalanceallocation);
        return headbalanceallocation;
    }

    public static Headbalanceallocation build(Enterprise enterprise, BigDecimal quantity) {
        Headbalanceallocation headbalanceallocation = new Headbalanceallocation();
        headbalanceallocation.setEnterpriseId(enterprise.getId());
        headbalanceallocation.setQuantity(quantity);
        ofPostMethod(headbalanceallocation);
        return headbalanceallocation;
    }

    public static void forInitialBalance(Headbalanceallocation headbalanceallocation) {
        BigDecimal quantity = headbalanceallocation.getQuantity();
        headbalanceallocation.setQuantity(quantity);
        headbalanceallocation.setBalance(quantity);
        ofPostMethod(headbalanceallocation);
    }

    public static void forDisable(Headbalanceallocation headbalanceallocation) {
        headbalanceallocation.setActive(States.INACTIVE);
    }

    public static void ofPostMethod(Headbalanceallocation headbalanceallocation) {
        headbalanceallocation.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        headbalanceallocation.setActive(States.ACTIVE);
        headbalanceallocation.setDeleted(States.EXISTENT);
    }
}
