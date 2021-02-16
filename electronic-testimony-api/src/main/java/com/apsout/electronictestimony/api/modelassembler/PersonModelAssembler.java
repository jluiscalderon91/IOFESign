package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.PersonController;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.PersonModel;
import com.apsout.electronictestimony.api.util.allocator.RoleAllocator;
import com.apsout.electronictestimony.api.util.allocator.ScopeAllocator;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {

    public PersonModelAssembler() {
        super(PersonController.class, PersonModel.class);
    }

    @Override
    public PersonModel toModel(Person person) {
        PersonModel personModel = instantiateModel(person);
        personModel.add(linkTo(methodOn(PersonController.class).getBy(person.getId(), null)).withSelfRel());
        personModel.add(linkTo(methodOn(PersonController.class).getAllBy(person.getPartnerId(), person.getEnterpriseId(), ScopeAllocator.getScopeIdOf(person), RoleAllocator.getRoleIdOf(person), null, null, null)).withSelfRel());
        personModel.setId(person.getId());
        personModel.setPartnerId(person.getPartnerId());
        personModel.setEnterpriseId(person.getEnterpriseId());
        personModel.setEnterpriseIdView(person.getEnterpriseIdView());
        personModel.setEnterpriseName(person.getEnterpriseName());
        personModel.setType(person.getType());
        personModel.setDocumentType(person.getDocumentType());
        personModel.setDocumentNumber(person.getDocumentNumber());
        personModel.setFirstname(person.getFirstname());
        personModel.setLastname(person.getLastname());
        personModel.setFullname(person.getFullname());
        personModel.setEmail(person.getEmail());
        personModel.setCellphone(person.getCellphone());
        personModel.setCreateAt(person.getCreateAt());
        personModel.setReplaceable(person.getReplaceable());
        personModel.setActive(person.getActive());
        personModel.setObservation(person.getObservation());
        personModel.set_more(person.getMoreAboutPerson());
        return personModel;
    }


    public PersonModel toModel2(Person person) {
        PersonModel personModel = instantiateModel(person);
        personModel.setEmail(person.getEmail());
        return personModel;
    }
}
