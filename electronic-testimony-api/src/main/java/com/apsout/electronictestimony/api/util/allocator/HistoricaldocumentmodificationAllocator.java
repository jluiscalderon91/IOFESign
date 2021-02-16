package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.Historicaldocumentmodification;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HistoricaldocumentmodificationAllocator {

    public static Historicaldocumentmodification build(Document document, Documentmodification documentmodification) {
        Historicaldocumentmodification historicaldocumentmodification = new Historicaldocumentmodification();
        historicaldocumentmodification.setDocumentId(document.getId());
        historicaldocumentmodification.setDocumentmodificationId(documentmodification.getId());
        ofPostMethod(historicaldocumentmodification);
        return historicaldocumentmodification;
    }

    public static void ofPostMethod(Historicaldocumentmodification historicaldocumentmodification) {
        historicaldocumentmodification.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        historicaldocumentmodification.setActive(true);
        historicaldocumentmodification.setDeleted(false);
    }
}
