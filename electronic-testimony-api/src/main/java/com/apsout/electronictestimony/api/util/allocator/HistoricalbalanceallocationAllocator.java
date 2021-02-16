package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.util.statics.Constant;
import com.apsout.electronictestimony.api.util.statics.States;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HistoricalbalanceallocationAllocator {

    public static Historicalbalanceallocation build(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance) {
        return build(headbalanceallocationId, person, enterpriseIdAction, quantity, balance, Constant.ISNT_RETURNED_BALANCE);
    }

    public static Historicalbalanceallocation build(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance, byte isReturn) {
        Historicalbalanceallocation historicalbalanceallocation = new Historicalbalanceallocation();
        historicalbalanceallocation.setHeadbalanceallocationId(headbalanceallocationId);
        historicalbalanceallocation.setPersonId(person.getId());
        historicalbalanceallocation.setQuantity(quantity);
        historicalbalanceallocation.setBalance(balance);
        historicalbalanceallocation.setEnterpriseIdAction(enterpriseIdAction);
        historicalbalanceallocation.setIsReturn(isReturn);
        ofPostMethod(historicalbalanceallocation);
        return historicalbalanceallocation;
    }

    public static void ofPostMethod(Historicalbalanceallocation historicalbalanceallocation) {
        historicalbalanceallocation.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        historicalbalanceallocation.setActive(States.ACTIVE);
        historicalbalanceallocation.setDeleted(States.EXISTENT);
    }
}
