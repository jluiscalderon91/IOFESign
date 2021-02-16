package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Sierecipient;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SierecipientAllocator {

    public static Sierecipient build(Sieemail sieemail, Sierecipient sierecipient) {
        sierecipient.setSieemailId(sieemail.getId());
        sierecipient.setSieemailBySieemailId(sieemail);
        ofPostMethod(sierecipient);
        return sierecipient;
    }

    public static Sierecipient build(int sieemailId, String address) {
        Sierecipient sierecipient = new Sierecipient();
        sierecipient.setSieemailId(sieemailId);
        sierecipient.setAddress(address);
        ofPostMethod(sierecipient);
        return sierecipient;
    }

    public static List<Sierecipient> build(Sieemail sieemail) {
        return sieemail.getSierecipientsById().stream().map(sierecipient -> build(sieemail, sierecipient)).collect(Collectors.toList());
    }

    public static void inactiveOf(Sieemail sieemail) {
        sieemail.getSierecipientsById().stream().forEach(sierecipient -> sierecipient.setActive(States.INACTIVE));
    }

    public static void ofPostMethod(Sierecipient sierecipient) {
        sierecipient.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        sierecipient.setActive(States.ACTIVE);
        sierecipient.setDeleted(States.EXISTENT);
    }
}
