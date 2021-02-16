package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Passwordretriever;
import com.apsout.electronictestimony.api.util.statics.Priority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PasswordretrieverAllocator {

    public static Passwordretriever build(String uuid) {
        Passwordretriever passwordretriever = new Passwordretriever();
        passwordretriever.setUuid(uuid);
        ofPostMethod(passwordretriever);
        return passwordretriever;
    }

    public static void forSent(Passwordretriever passwordretriever, String username, String email, String hashFirstStep) {
        passwordretriever.setUsername(username);
        passwordretriever.setEmail(email);
        passwordretriever.setHashFirstStep(hashFirstStep);
        passwordretriever.setSent(States.NOT_SENT);
        passwordretriever.setSent(States.NOT_MATCHED);
        passwordretriever.setPriority(Priority.MAX);
        passwordretriever.setFinished(States.NOT_FINISHED);
    }

    public static void forUpdate(Passwordretriever passwordretriever, String verificationCode) {
        passwordretriever.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        passwordretriever.setSent(States.SENT);
        passwordretriever.setVerificationCode(verificationCode);
    }

    public static void forFinish(Passwordretriever passwordretriever) {
        passwordretriever.setFinished(States.FINISHED);
        passwordretriever.setFinishedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void forMatch(Passwordretriever passwordretriever, String hashSecondStep, byte matched) {
        passwordretriever.setMatched(matched);
        passwordretriever.setMatchedAt(Timestamp.valueOf(LocalDateTime.now()));
        passwordretriever.setHashSecondStep(hashSecondStep);
    }

    public static void forReducePriority(Passwordretriever passwordretriever) {
        reducePriority(passwordretriever);
    }

    private static void reducePriority(Passwordretriever passwordretriever) {
        final int priority = passwordretriever.getPriority();
        final int newPriority = priority + 1;
        passwordretriever.setPriority(newPriority);
    }

    public static void ofPostMethod(Passwordretriever passwordretriever) {
        passwordretriever.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        passwordretriever.setActive(States.ACTIVE);
        passwordretriever.setDeleted(States.EXISTENT);
    }
}
