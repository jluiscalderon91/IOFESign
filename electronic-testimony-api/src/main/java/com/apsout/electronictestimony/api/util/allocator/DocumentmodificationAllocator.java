package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumentmodification;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DocumentmodificationAllocator {

    public static Documentmodification build(Person person, Document documentNew, Document documentOld, String reasonModification) {
        Documentmodification documentmodification = new Documentmodification();
        documentmodification.setPersonId(person.getId());
        documentmodification.setDocumentIdNew(documentNew.getId());
        documentmodification.setDocumentIdOld(documentOld.getId());
        reasonModification = reasonModification == null || reasonModification.isEmpty() ? "-" : reasonModification;
        documentmodification.setDescription(reasonModification);
        ofPostMethod(documentmodification);
        return documentmodification;
    }

    public static void ofPostMethod(Documentmodification documentmodification) {
        documentmodification.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        documentmodification.setActive(true);
        documentmodification.setDeleted(false);
    }

    public static void forReturn(MoreAboutDocumentmodification more, Person person) {
        more.setFullnameCancellator(person.getFullname());
    }
}
