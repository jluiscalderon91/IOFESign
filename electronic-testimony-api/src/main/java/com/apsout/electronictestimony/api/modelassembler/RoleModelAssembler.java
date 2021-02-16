package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.RoleController;
import com.apsout.electronictestimony.api.entity.model.RoleModel;
import com.apsout.electronictestimony.api.entity.security.Role;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class RoleModelAssembler extends RepresentationModelAssemblerSupport<Role, RoleModel> {

    public RoleModelAssembler() {
        super(RoleController.class, RoleModel.class);
    }

    @Override
    public RoleModel toModel(Role role) {
        RoleModel roleModel = instantiateModel(role);
        roleModel.setId(role.getId());
        roleModel.setName(role.getName());
        roleModel.setAbbreviation(role.getAbbreviation());
        roleModel.setDescription(role.getDescription());
        roleModel.setEditable(role.getEditable());
        roleModel.setCreateAt(role.getCreateAt());
        roleModel.setActive(role.getActive());
        return roleModel;
    }
}
