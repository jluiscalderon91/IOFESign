package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.EnterpriseController;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.model.EnterpriseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class EnterpriseModelAssembler extends RepresentationModelAssemblerSupport<Enterprise, EnterpriseModel> {

    public EnterpriseModelAssembler() {
        super(EnterpriseController.class, EnterpriseModel.class);
    }

    @Override
    public EnterpriseModel toModel(Enterprise enterprise) {
        EnterpriseModel enterpriseModel = instantiateModel(enterprise);
        enterpriseModel.add(linkTo(methodOn(EnterpriseController.class).getBy(enterprise.getId())).withSelfRel());
        enterpriseModel.setId(enterprise.getId());
        enterpriseModel.setPartnerId(enterprise.getPartnerId());
        enterpriseModel.setDocumentType(enterprise.getDocumentType());
        enterpriseModel.setDocumentNumber(enterprise.getDocumentNumber());
        enterpriseModel.setName(enterprise.getName());
        enterpriseModel.setExcluded(enterprise.getExcluded());
        enterpriseModel.setIsPartner(enterprise.getIsPartner());
        enterpriseModel.setIsCustomer(enterprise.getIsCustomer());
        enterpriseModel.setTradeName(enterprise.getTradeName());
        enterpriseModel.setCreatedByPersonId(enterprise.getCreatedByPersonId());
        enterpriseModel.setCreateAt(enterprise.getCreateAt());
        enterpriseModel.setActive(enterprise.getActive());
        enterpriseModel.setDeleted(enterprise.getDeleted());
        enterpriseModel.setObservation(enterprise.getObservation());
        enterpriseModel.set_more(enterprise.getMoreAboutEnterprise());
        return enterpriseModel;
    }
}
