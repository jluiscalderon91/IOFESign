package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.PersontypeController;
import com.apsout.electronictestimony.api.entity.Persontype;
import com.apsout.electronictestimony.api.entity.model.PersontypeModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class OutsidePersontypeModelAssembler extends  RepresentationModelAssemblerSupport<Persontype, PersontypeModel> {

    public OutsidePersontypeModelAssembler() {
        super(PersontypeController.class, PersontypeModel.class);
    }

    @Override
    public PersontypeModel toModel(Persontype entity) {
        PersontypeModel personTypeModel = instantiateModel(entity);
        personTypeModel.setId(entity.getId());
        personTypeModel.setDescription(entity.getDescription());
        return personTypeModel;
    }

}
