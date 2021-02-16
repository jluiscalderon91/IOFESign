package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documenttraceability;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DocumenttraceabilityAllocator {
    private static final Logger logger = LoggerFactory.getLogger(DocumenttraceabilityAllocator.class);

    public static Documenttraceability build(Person person, Document document, int stateId, int type) {
        return build(person.getId(), document.getId(), stateId, type);
    }

    public static Documenttraceability build(int personId, int documentId, int stateId, int type) {
        return build(personId, documentId, stateId, States.VISIBLE, type);
    }

    public static void ofPostMethod(Documenttraceability documenttraceability) {
        documenttraceability.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        documenttraceability.setActive(States.ACTIVE);
        documenttraceability.setDeleted(States.EXISTENT);
    }

    public static Documenttraceability build(int personId, int documentId, int stateId, byte visible, int type) {
        Documenttraceability documenttraceability = new Documenttraceability();
        documenttraceability.setPersonId(personId);
        documenttraceability.setDocumentId(documentId);
        documenttraceability.setStateId(stateId);
        documenttraceability.setVisible(visible);
        documenttraceability.setType(type);
        ofPostMethod(documenttraceability);
        return documenttraceability;
    }
}
