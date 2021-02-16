package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.security.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserauthorityAllocator {

    public static Userauthority build(User user, Authority authority) {
        Userauthority userauthority = new Userauthority();
        userauthority.setUserId(user.getId());
        userauthority.setUserByUserId(user);
        userauthority.setAuthorityId(authority.getId());
        userauthority.setAuthorityByAuthorityId(authority);
        ofPostMethod(userauthority);
        return userauthority;
    }

    public static void forDelete(Userauthority userauthority) {
        userauthority.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Userauthority userauthority) {
        userauthority.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        userauthority.setActive(States.ACTIVE);
        userauthority.setDeleted(States.EXISTENT);
    }
}
