package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.SieemailController;
import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.model.SieemailModel;
import com.apsout.electronictestimony.api.util.statics.States;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SieemailModelAssembler extends RepresentationModelAssemblerSupport<Sieemail, SieemailModel> {

    public SieemailModelAssembler() {
        super(SieemailController.class, SieemailModel.class);
    }

    @Override
    public SieemailModel toModel(Sieemail sieemail) {
        SieemailModel sieemailModel = instantiateModel(sieemail);
        sieemailModel.add(linkTo(methodOn(SieemailController.class).getBy(sieemail.getId(), null)).withSelfRel());
        sieemailModel.setId(sieemail.getId());
        sieemailModel.setSubject(sieemail.getSubject());
        sieemailModel.setSierecipientsById(sieemail.getSierecipientsById().stream().filter(s -> States.ACTIVE == s.getActive() && States.EXISTENT == s.getDeleted()).collect(Collectors.toList()));
        sieemailModel.setBody(sieemail.getBody());
        return sieemailModel;
    }
}
