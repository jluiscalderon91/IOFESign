package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.IdentificationdocumentController;
import com.apsout.electronictestimony.api.entity.Identificationdocument;
import com.apsout.electronictestimony.api.entity.model.IdentificationdocumentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class OutsideIdentificationdocumentModelAssembler extends  RepresentationModelAssemblerSupport<Identificationdocument, IdentificationdocumentModel> {

    public OutsideIdentificationdocumentModelAssembler() {
        super(IdentificationdocumentController.class, IdentificationdocumentModel.class);
    }

    @Override
    public IdentificationdocumentModel toModel(Identificationdocument entity) {
        IdentificationdocumentModel identificationdocumentModel = instantiateModel(entity);
        identificationdocumentModel.setId(entity.getId());
        identificationdocumentModel.setLongDescription(entity.getLongdescription());
        identificationdocumentModel.setShortDescription(entity.getShortdescription());
        return identificationdocumentModel;
    }

}
