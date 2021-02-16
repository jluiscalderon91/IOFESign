package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Sieemailoperator;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SieemailoperatorAllocator {

    public static Sieemailoperator build(Sieemail sieemail, Operator operator) {
        Sieemailoperator sieemailoperator = new Sieemailoperator();
        sieemailoperator.setSieemailId(sieemail.getId());
        sieemailoperator.setSieemailBySieemailId(sieemail);
        sieemailoperator.setOperatorId(operator.getId());
        sieemailoperator.setOperatorByOperatorId(operator);
        ofPostMethod(sieemailoperator);
        return sieemailoperator;
    }

    private static void ofPostMethod(Sieemailoperator sieemailoperator) {
        sieemailoperator.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        sieemailoperator.setActive(States.ACTIVE);
        sieemailoperator.setDeleted(States.EXISTENT);
    }
}
