package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.entity.Stationcounter;
import com.apsout.electronictestimony.api.entity.model.StationcounterModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class StationcounterModelAssembler extends RepresentationModelAssemblerSupport<Stationcounter, StationcounterModel> {

    public StationcounterModelAssembler() {
        super(StationcounterModelAssembler.class, StationcounterModel.class);
    }

    @Override
    public StationcounterModel toModel(Stationcounter stationcounter) {
        StationcounterModel stationcounterModel = instantiateModel(stationcounter);
        stationcounterModel.setId(stationcounter.getId());
        stationcounterModel.setType(stationcounter.getType());
        stationcounterModel.setWorkflowId(stationcounter.getWorkflowId());
        stationcounterModel.setInitialAmount(stationcounter.getInitialAmount());
        stationcounterModel.setFinalAmount(stationcounter.getFinalAmount());
        stationcounterModel.setCompleted(stationcounter.getCompleted());
        stationcounterModel.setUpdateAt(stationcounter.getUpdateAt());
        stationcounterModel.setCreateAt(stationcounter.getCreateAt());
        return stationcounterModel;
    }
}
