package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userrole;
import com.apsout.electronictestimony.api.util.statics.Roles;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserroleAllocator {

    public static Userrole build(User user, Role role) {
        Userrole userrole = new Userrole();
        userrole.setUserId(user.getId());
        userrole.setUserByUserId(user);
        userrole.setRoleId(role.getId());
        userrole.setRoleByRoleId(role);
        if (Roles.OUTSIDEUSER == role.getId()) {
            userrole.setOnlyApi(States.ONLY_API);
        } else {
            userrole.setOnlyApi(States.NOT_ONLY_API);
        }
        ofPostMethod(userrole);
        return userrole;
    }

    public static void forUpdate(Userrole userrole, Role role) {
        if (Roles.OUTSIDEUSER == role.getId()) {
            userrole.setOnlyApi(States.ONLY_API);
        } else {
            userrole.setOnlyApi(States.NOT_ONLY_API);
        }
        userrole.setRoleId(role.getId());
    }

    public static void ofPostMethod(Userrole userrole) {
        userrole.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        userrole.setActive(States.ACTIVE);
        userrole.setDeleted(States.EXISTENT);
    }
}
