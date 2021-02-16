package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.AuthorityController;
import com.apsout.electronictestimony.api.entity.model.AuthorityModel;
import com.apsout.electronictestimony.api.entity.security.Authority;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class AuthorityModelAssembler extends  RepresentationModelAssemblerSupport<Authority, AuthorityModel> {

    public AuthorityModelAssembler() {
        super(AuthorityController.class, AuthorityModel.class);
    }

    @Override
    public AuthorityModel toModel(Authority authority) {
        AuthorityModel authorityModel = instantiateModel(authority);
        authorityModel.setId(authority.getId());
        authorityModel.setModule(authority.getModule());
        authorityModel.setOnlySuperadmin(authority.getOnlySuperadmin());
        authorityModel.setCode(authority.getCode());
        authorityModel.setDescription(authority.getDescription());
        authorityModel.setCreateAt(authority.getCreateAt());
        authorityModel.setActive(authority.getActive());
        return authorityModel;
    }
}
