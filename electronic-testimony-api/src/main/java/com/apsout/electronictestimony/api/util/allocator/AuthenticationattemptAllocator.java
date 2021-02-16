package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Authenticationattempt;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuthenticationattemptAllocator {

    public static Authenticationattempt build(User user) {
        Authenticationattempt authenticationattempt = new Authenticationattempt();
        authenticationattempt.setUserId(user.getId());
        authenticationattempt.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
        ofPostMethod(authenticationattempt);
        return authenticationattempt;
    }

    public static void ofPostMethod(Authenticationattempt authenticationattempt) {
        authenticationattempt.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        authenticationattempt.setActive(States.ACTIVE);
        authenticationattempt.setDeleted(States.EXISTENT);
    }
}
