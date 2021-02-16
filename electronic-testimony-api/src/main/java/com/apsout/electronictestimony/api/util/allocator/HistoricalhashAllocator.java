package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Historicalhash;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HistoricalhashAllocator {
    public static Historicalhash build(Document document) {
        Historicalhash historicalhash = new Historicalhash();
        historicalhash.setDocumentId(document.getId());
        historicalhash.setDocumentByDocumentId(document);
        historicalhash.setHashIdentifier(document.getHashIdentifier());
        ofPostMethod(historicalhash);
        return historicalhash;
    }

    //Add info before save on db, for a Post request
    public static void ofPostMethod(Historicalhash historicalhash) {
        historicalhash.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        historicalhash.setActive(States.ACTIVE);
        historicalhash.setDeleted(States.EXISTENT);
    }
}
