package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.ObservationcancelController;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.model.ObservationcancelModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class ObservationcancelModelAssembler extends RepresentationModelAssemblerSupport<Observationcancel, ObservationcancelModel> {

    public ObservationcancelModelAssembler() {
        super(ObservationcancelController.class, ObservationcancelModel.class);
    }

    @Override
    public ObservationcancelModel toModel(Observationcancel observationcancel) {
        ObservationcancelModel observationcancelModel = instantiateModel(observationcancel);
        observationcancelModel.setDescription(observationcancel.getDescription());
        observationcancelModel.setCreateAt(observationcancel.getCreateAt());
        observationcancelModel.set_more(observationcancel.getMoreAboutObservationcancel());
        return observationcancelModel;
    }
}
