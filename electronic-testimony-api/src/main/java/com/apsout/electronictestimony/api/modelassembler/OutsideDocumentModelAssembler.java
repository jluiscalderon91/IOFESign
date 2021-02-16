package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.DocumentController;
import com.apsout.electronictestimony.api.controller.PublicController;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.model.DocumentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class OutsideDocumentModelAssembler extends RepresentationModelAssemblerSupport<Document, DocumentModel> {

    public OutsideDocumentModelAssembler() {
        super(DocumentController.class, DocumentModel.class);
    }

    @Override
    public DocumentModel toModel(Document document) {
        DocumentModel documentModel = instantiateModel(document);
        documentModel.add(linkTo(methodOn(PublicController.class).getStreamByOutside(document.getId(), document.getHashIdentifier(), null)).withRel("stream"));
        documentModel.setId(document.getId());
        documentModel.setSubject(document.getSubject());
        documentModel.setFinished(document.getFinished());
        documentModel.setHashIdentifier(document.getHashIdentifier());
        documentModel.setCreateAt(document.getCreateAt());
        documentModel.setActive(document.getActive());
        documentModel.setObservation(document.getObservation());
        documentModel.set_more(document.getMoreAboutDocument());
        return documentModel;
    }
}
