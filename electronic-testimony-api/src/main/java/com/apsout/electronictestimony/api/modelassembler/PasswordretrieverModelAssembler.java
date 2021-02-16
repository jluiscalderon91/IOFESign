package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.PublicController;
import com.apsout.electronictestimony.api.entity.Passwordretriever;
import com.apsout.electronictestimony.api.entity.model.PasswordretrieverModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class PasswordretrieverModelAssembler extends RepresentationModelAssemblerSupport<Passwordretriever, PasswordretrieverModel> {
    public PasswordretrieverModelAssembler() {
        super(PublicController.class, PasswordretrieverModel.class);
    }

    @Override
    public PasswordretrieverModel toModel(Passwordretriever passwordretriever) {
        PasswordretrieverModel passwordretrieverModel = instantiateModel(passwordretriever);
        passwordretrieverModel.setUuid(passwordretriever.getUuid());
        passwordretrieverModel.setHashFirstStep(passwordretriever.getHashFirstStep());
        passwordretrieverModel.setHashSecondStep(passwordretriever.getHashSecondStep());
        return passwordretrieverModel;
    }
}
