package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuthorityAllocator {

    public static Authority build(Authority authority) {
        ofPostMethod(authority);
        return authority;
    }

    public static void forUpdate(Authority authority, Authority newAuthority) {
        newAuthority.setModule(authority.getModule());
        newAuthority.setCode(authority.getCode());
        newAuthority.setDescription(authority.getDescription());
        newAuthority.setOnlySuperadmin(authority.getOnlySuperadmin());
        newAuthority.setActive(authority.getActive());
    }

    public static void ofPostMethod(Authority authority) {
        authority.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        authority.setActive(States.ACTIVE);
        authority.setDeleted(States.EXISTENT);
    }

    public static void forDelete(Authority authority) {
        authority.setDeleted(States.DELETED);
    }
}
