package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Temporarysession;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TemporarysessionAllocator {

    public static Temporarysession build(Person person, String uuid) {
        Temporarysession temporarysession = new Temporarysession();
        temporarysession.setPersonId(person.getId());
        temporarysession.setUuid(uuid);
        ofPostMethod(temporarysession);
        return temporarysession;
    }

//    public static void forUpdate(Authority authority, Authority newAuthority) {
//        newAuthority.setModule(authority.getModule());
//        newAuthority.setCode(authority.getCode());
//        newAuthority.setDescription(authority.getDescription());
//        newAuthority.setOnlySuperadmin(authority.getOnlySuperadmin());
//        newAuthority.setActive(authority.getActive());
//    }

    public static void ofPostMethod(Temporarysession temporarysession) {
        temporarysession.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        temporarysession.setActive(true);
        temporarysession.setDeleted(false);
    }

//    public static void forDelete(Temporarysession authority) {
//        authority.setDeleted(States.DELETED);
//    }
}
