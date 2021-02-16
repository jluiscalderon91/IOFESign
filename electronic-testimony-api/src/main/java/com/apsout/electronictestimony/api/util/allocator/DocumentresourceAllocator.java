package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documentresource;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DocumentresourceAllocator {
    public static Documentresource build(Document document, Resource resource) {
        Documentresource documentresource = new Documentresource();
        documentresource.setDocumentId(document.getId());
        documentresource.setDocumentByDocumentId(document);
        documentresource.setResourceId(resource.getId());
        documentresource.setResourceByResourceId(resource);
        ofPostMethod(documentresource);
        return documentresource;
    }

    public static void ofPostMethod(Documentresource documentresource) {
        documentresource.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        documentresource.setActive(States.ACTIVE);
        documentresource.setDeleted(States.EXISTENT);
    }
}
