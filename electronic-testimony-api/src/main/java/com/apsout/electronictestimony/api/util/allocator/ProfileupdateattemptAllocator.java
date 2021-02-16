package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Profileupdateattempt;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProfileupdateattemptAllocator {

    public static Profileupdateattempt build(int personId) {
        Profileupdateattempt profileupdateattempt = new Profileupdateattempt();
        profileupdateattempt.setPersonId(personId);
        profileupdateattempt.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
        ofPostMethod(profileupdateattempt);
        return profileupdateattempt;
    }

    public static void ofPostMethod(Profileupdateattempt profileupdateattempt) {
        profileupdateattempt.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        profileupdateattempt.setActive(States.ACTIVE);
        profileupdateattempt.setDeleted(States.EXISTENT);
    }
}
