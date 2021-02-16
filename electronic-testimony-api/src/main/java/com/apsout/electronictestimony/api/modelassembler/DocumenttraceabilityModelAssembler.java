package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.DocumenttraceabilityController;
import com.apsout.electronictestimony.api.entity.Documenttraceability;
import com.apsout.electronictestimony.api.entity.model.DocumenttraceabilityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class DocumenttraceabilityModelAssembler extends RepresentationModelAssemblerSupport<Documenttraceability, DocumenttraceabilityModel> {

    public DocumenttraceabilityModelAssembler() {
        super(DocumenttraceabilityController.class, DocumenttraceabilityModel.class);
    }

    @Override
    public DocumenttraceabilityModel toModel(Documenttraceability documenttraceability) {
        DocumenttraceabilityModel documenttraceabilityModel = instantiateModel(documenttraceability);
        documenttraceabilityModel.setStateId(documenttraceability.getStateId());
        documenttraceabilityModel.setCreateAt(documenttraceability.getCreateAt());
        documenttraceabilityModel.set_more(documenttraceability.getMoreAboutDocumenttraceability());
        return documenttraceabilityModel;
    }
}
