package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Cancelnotification;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.util.statics.Priority;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CancelnotificationAllocator {

    public static Cancelnotification build(Document document, Person person) {
        Cancelnotification cancelnotification = new Cancelnotification();
        cancelnotification.setDocumentId(document.getId());
        cancelnotification.setPersonId(person.getId());
        String emailUploader = document.getPersonByPersonId().getEmail();
        cancelnotification.setEmail(emailUploader);
        cancelnotification.setPriority(Priority.MAX);
        cancelnotification.setSent(States.NOT_SENT);
        ofPostMethod(cancelnotification);
        return cancelnotification;
    }

    public static void forUpdate(Cancelnotification passwordretriever) {
        passwordretriever.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        passwordretriever.setSent(States.SENT);
        passwordretriever.setLastAttemptAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void forReducePriority(Cancelnotification cancelnotification) {
        reducePriority(cancelnotification);
    }

    private static void reducePriority(Cancelnotification cancelnotification) {
        final int priority = cancelnotification.getPriority();
        final int newPriority = priority + 1;
        cancelnotification.setPriority(newPriority);
    }

    public static void ofPostMethod(Cancelnotification cancelnotification) {
        cancelnotification.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        cancelnotification.setActive(States.ACTIVE);
        cancelnotification.setDeleted(States.EXISTENT);
    }
}
