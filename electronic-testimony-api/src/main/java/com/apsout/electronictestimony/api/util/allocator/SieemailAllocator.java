package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SieemailAllocator {

    public static Sieemail build(Participant participant, Sieemail sieemail) {
        sieemail.setParticipantId(participant.getId());
        sieemail.setParticipantByParticipantId(participant);
        ofPostMethod(sieemail);
        return sieemail;
    }

    public static void forUpdate(Sieemail sieemailDb, Sieemail oldSieemail) {
        sieemailDb.setSubject(oldSieemail.getSubject());
        sieemailDb.setBody(oldSieemail.getBody());
    }

    public static void ofPostMethod(Sieemail sieemail) {
        sieemail.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        sieemail.setActive(States.ACTIVE);
        sieemail.setDeleted(States.EXISTENT);
    }

    public static Sieemail forReturn(Sieemail sieemail) {
        Sieemail sieemail1 = new Sieemail();
        String subject = StringUtil.removeAccents(sieemail.getSubject());
        String body = StringUtil.replace2Html(sieemail.getBody());
        sieemail1.setSubject(subject);
        sieemail1.setBody(body);
        return sieemail1;
    }
}
