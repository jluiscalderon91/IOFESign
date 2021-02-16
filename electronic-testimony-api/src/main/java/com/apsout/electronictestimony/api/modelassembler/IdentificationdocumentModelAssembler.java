package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.IdentificationdocumentController;
import com.apsout.electronictestimony.api.entity.Identificationdocument;
import com.apsout.electronictestimony.api.entity.model.IdentificationdocumentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class IdentificationdocumentModelAssembler extends  RepresentationModelAssemblerSupport<Identificationdocument, IdentificationdocumentModel> {

    public IdentificationdocumentModelAssembler() {
        super(IdentificationdocumentController.class, IdentificationdocumentModel.class);
    }

    @Override
    public IdentificationdocumentModel toModel(Identificationdocument entity) {
        IdentificationdocumentModel identificationdocumentModel = instantiateModel(entity);
        identificationdocumentModel.add(linkTo(methodOn(IdentificationdocumentController.class).getAll()).withRel("identificationdocuments"));
        identificationdocumentModel.setId(entity.getId());
        identificationdocumentModel.setLongDescription(entity.getLongdescription());
        identificationdocumentModel.setShortDescription(entity.getShortdescription());
        return identificationdocumentModel;
    }

}
