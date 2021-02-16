package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutObservationcancel;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ObservationcancelAllocator {

//    public static Observationcancel build(Observationcancel observationcancel, Person person) {
//        observationcancel.setPersonId(person.getId());
//        observationcancel.setPersonByPersonId(person);
//        ofPostMethod(observationcancel);
//        return observationcancel;
//    }

    public static Observationcancel build(Person person, Document document, String comment) {
        Observationcancel observationcancel = new Observationcancel();
        observationcancel.setPersonId(person.getId());
        observationcancel.setDocumentId(document.getId());
        if (comment == null || comment.isEmpty()) {
            observationcancel.setDescription("Ninguno.");
        }
        observationcancel.setDescription(comment);
        ofPostMethod(observationcancel);
        return observationcancel;
    }

    public static void ofPostMethod(Observationcancel observationcancel) {
        observationcancel.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        observationcancel.setActive(States.ACTIVE);
        observationcancel.setDeleted(States.EXISTENT);
    }

    public static void forReturn(MoreAboutObservationcancel more, Person person) {
        more.setFullnameCancellator(person.getFullname());
    }
}
