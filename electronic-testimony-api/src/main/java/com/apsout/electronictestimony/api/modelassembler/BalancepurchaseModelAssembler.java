package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.BalancepurchaseController;
import com.apsout.electronictestimony.api.entity.Balancepurchase;
import com.apsout.electronictestimony.api.entity.model.BalancepurchaseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class BalancepurchaseModelAssembler extends RepresentationModelAssemblerSupport<Balancepurchase, BalancepurchaseModel> {

    public BalancepurchaseModelAssembler() {
        super(BalancepurchaseController.class, BalancepurchaseModel.class);
    }

    @Override
    public BalancepurchaseModel toModel(Balancepurchase balancepurchase) {
        BalancepurchaseModel model = instantiateModel(balancepurchase);
        model.setId(balancepurchase.getId());
        model.setEnterpriseId(balancepurchase.getEnterpriseId());
        model.setPersonId(balancepurchase.getPersonId());
        model.setQuantity(balancepurchase.getQuantity());
        model.setPrice(balancepurchase.getPrice());
        model.setCreateAt(balancepurchase.getCreateAt());
        model.setActive(balancepurchase.getActive());
        model.setEnterpriseName(balancepurchase.getEnterpriseByEnterpriseId().getName());
        model.setApplicantFullname(balancepurchase.getPersonByPersonId().getFullname());
        return model;
    }
}
