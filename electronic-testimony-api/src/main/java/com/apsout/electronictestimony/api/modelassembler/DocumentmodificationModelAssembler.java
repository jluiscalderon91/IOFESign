package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.DocumentmodificationController;
import com.apsout.electronictestimony.api.entity.Documentmodification;
import com.apsout.electronictestimony.api.entity.model.DocumentmodificationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class DocumentmodificationModelAssembler extends RepresentationModelAssemblerSupport<Documentmodification, DocumentmodificationModel> {

    public DocumentmodificationModelAssembler() {
        super(DocumentmodificationController.class, DocumentmodificationModel.class);
    }

    @Override
    public DocumentmodificationModel toModel(Documentmodification documentmodification) {
        DocumentmodificationModel documentmodificationModel = instantiateModel(documentmodification);
        documentmodificationModel.setDescription(documentmodification.getDescription());
        documentmodificationModel.setCreateAt(documentmodification.getCreateAt());
        documentmodificationModel.set_more(documentmodification.getMoreAboutDocumentmodification());
        return documentmodificationModel;
    }
}
