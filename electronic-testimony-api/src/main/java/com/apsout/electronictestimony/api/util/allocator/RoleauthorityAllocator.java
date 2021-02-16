package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.Roleauthority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoleauthorityAllocator {


    public static Roleauthority build(Role role, Authority authority) {
        Roleauthority roleauthority = new Roleauthority();
        roleauthority.setRoleId(role.getId());
        roleauthority.setRoleByRoleId(role);
        roleauthority.setAuthorityId(authority.getId());
        roleauthority.setAuthorityByAuthorityId(authority);
        ofPostMethod(roleauthority);
        return roleauthority;
    }

    public static void forDelete(Roleauthority roleauthority) {
        roleauthority.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Roleauthority roleauthority) {
        roleauthority.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        roleauthority.setActive(States.ACTIVE);
        roleauthority.setDeleted(States.EXISTENT);
    }
}
