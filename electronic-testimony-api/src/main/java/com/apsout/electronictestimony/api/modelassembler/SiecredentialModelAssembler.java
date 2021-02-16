package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.SiecredentialController;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.entity.model.SiecredentialModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class SiecredentialModelAssembler extends RepresentationModelAssemblerSupport<Siecredential, SiecredentialModel> {

    public SiecredentialModelAssembler() {
        super(SiecredentialController.class, SiecredentialModel.class);
    }

    @Override
    public SiecredentialModel toModel(Siecredential siecredential) {
        SiecredentialModel siecredentialModel = instantiateModel(siecredential);
        siecredentialModel.setId(siecredential.getId());
        siecredentialModel.setUsername(siecredential.getUsername());
        siecredentialModel.setVersion(siecredential.getVersion());
        siecredentialModel.setCreateAt(siecredential.getCreateAt());
        siecredentialModel.setActive(siecredential.getActive());
        siecredentialModel.setObservation(siecredential.getObservation());
        siecredentialModel.set_more(siecredential.getMoreAboutSiecredential());
        return siecredentialModel;
    }
}
