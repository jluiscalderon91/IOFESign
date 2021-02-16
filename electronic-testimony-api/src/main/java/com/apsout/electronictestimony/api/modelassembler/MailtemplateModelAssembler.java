package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.MailtemplateController;
import com.apsout.electronictestimony.api.entity.Mailtemplate;
import com.apsout.electronictestimony.api.entity.model.MailtemplateModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class MailtemplateModelAssembler extends RepresentationModelAssemblerSupport<Mailtemplate, MailtemplateModel> {

    public MailtemplateModelAssembler() {
        super(MailtemplateController.class, MailtemplateModel.class);
    }

    @Override
    public MailtemplateModel toModel(Mailtemplate document) {
        MailtemplateModel mailtemplateModel = instantiateModel(document);
        mailtemplateModel.setId(document.getId());
        mailtemplateModel.setEnterpriseId(document.getEnterpriseId());
        mailtemplateModel.setType(document.getType());
        mailtemplateModel.setSubject(document.getSubject());
        mailtemplateModel.setBody(document.getBody());
        mailtemplateModel.setRecipientType(document.getRecipientType());
        mailtemplateModel.setCreateAt(document.getCreateAt());
        mailtemplateModel.setActive(document.getActive());
        mailtemplateModel.setObservation(document.getObservation());
        return mailtemplateModel;
    }
}
