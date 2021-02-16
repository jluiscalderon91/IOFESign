package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.DocumentController;
import com.apsout.electronictestimony.api.controller.PublicController;
import com.apsout.electronictestimony.api.controller.ResourceController;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.model.DocumentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DocumentModelAssembler extends RepresentationModelAssemblerSupport<Document, DocumentModel> {

    public DocumentModelAssembler() {
        super(DocumentController.class, DocumentModel.class);
    }

    @Override
    public DocumentModel toModel(Document document) {
        DocumentModel documentModel = instantiateModel(document);
        documentModel.add(linkTo(methodOn(DocumentController.class).getAllBy4User(document.getPersonByPersonId().getEnterpriseId(), document.getWorkflowId(), document.getPersonId(), null, null, null, null)).withSelfRel());
        documentModel.add(linkTo(methodOn(ResourceController.class).getResourceBy(document.getId(), null)).withRel("resource"));
        documentModel.add(linkTo(methodOn(PublicController.class).getStreamByPrivate(document.getId(), document.getHashIdentifier(), null)).withRel("stream"));
        documentModel.setId(document.getId());
        documentModel.setPersonId(document.getPersonId());
        documentModel.setStateId(document.getStateId());
        documentModel.setEnterpriseDocumentNumber(document.getEnterpriseDocumentNumber());
        documentModel.setSubject(document.getSubject());
        documentModel.setFinished(document.getFinished());
        documentModel.setDescription(document.getDescription());
        documentModel.setHashIdentifier(document.getHashIdentifier());
        documentModel.setCreateAt(document.getCreateAt());
        documentModel.setActive(document.getActive());
        documentModel.setObservation(document.getObservation());
        documentModel.setHasMultipleAttachments(document.getHasMultipleAttachments());
        documentModel.set_more(document.getMoreAboutDocument());
        documentModel.setClosedStamping(document.getClosedStamping());
        return documentModel;
    }
}
