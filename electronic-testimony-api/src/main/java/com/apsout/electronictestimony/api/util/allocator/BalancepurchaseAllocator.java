package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BalancepurchaseAllocator {

    public static Balancepurchase build(Shoppingcard shoppingcard, Enterprise enterprise, Person person) {
        Balancepurchase balancepurchase = new Balancepurchase();
        balancepurchase.setQuantity(shoppingcard.getQuantity());
        balancepurchase.setPrice(shoppingcard.getPrice());
        balancepurchase.setEnterpriseId(enterprise.getId());
        balancepurchase.setPersonId(person.getId());
        ofPostMethod(balancepurchase);
        return balancepurchase;
    }

    public static void ofPostMethod(Balancepurchase balancepurchase) {
        balancepurchase.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        balancepurchase.setActive(States.ACTIVE);
        balancepurchase.setDeleted(States.EXISTENT);
    }
}
