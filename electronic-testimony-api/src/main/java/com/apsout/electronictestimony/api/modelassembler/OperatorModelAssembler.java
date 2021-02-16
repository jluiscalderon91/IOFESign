package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.OperatorController;
import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.model.OperatorModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class OperatorModelAssembler extends RepresentationModelAssemblerSupport<Operator, OperatorModel> {
    public OperatorModelAssembler() {
        super(OperatorController.class, OperatorModel.class);
    }

    @Override
    public OperatorModel toModel(Operator operator) {
        OperatorModel operatorModel = instantiateModel(operator);
        operatorModel.add(linkTo(OperatorController.class).withRel("operators"));
        operatorModel.setId(operator.getId());
        operatorModel.setPersonId(operator.getPersonId());
        operatorModel.setOperationId(operator.getOperationId());
        operatorModel.setOrderOperation(operator.getOrderOperation());
        operatorModel.setUploadRubric(operator.getUploadRubric());
        operatorModel.setDigitalSignature(operator.getDigitalSignature());
        operatorModel.setTypeElectronicSignature(operator.getTypeElectronicSignature());
        operatorModel.setCreateAt(operator.getCreateAt());
        operatorModel.setActive(operator.getActive());
        operatorModel.setObservation(operator.getObservation());
        operatorModel.setEnterpriseId(operator.getEnterpriseId());
        operatorModel.set_more(operator.getMoreAboutOperator());
        return operatorModel;
    }
}
