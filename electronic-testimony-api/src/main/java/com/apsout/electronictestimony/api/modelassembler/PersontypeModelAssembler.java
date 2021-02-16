package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.PersontypeController;
import com.apsout.electronictestimony.api.entity.Persontype;
import com.apsout.electronictestimony.api.entity.model.PersontypeModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PersontypeModelAssembler extends  RepresentationModelAssemblerSupport<Persontype, PersontypeModel> {

    public PersontypeModelAssembler() {
        super(PersontypeController.class, PersontypeModel.class);
    }

    @Override
    public PersontypeModel toModel(Persontype entity) {
        PersontypeModel personTypeModel = instantiateModel(entity);
        personTypeModel.add(linkTo(methodOn(PersontypeController.class).getAll()).withRel("persontypes"));
        personTypeModel.setId(entity.getId());
        personTypeModel.setDescription(entity.getDescription());
        return personTypeModel;
    }

}
