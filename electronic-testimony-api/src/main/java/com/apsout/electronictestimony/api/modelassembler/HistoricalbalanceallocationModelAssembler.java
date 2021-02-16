package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.HistoricalbalanceallocationController;
import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.HistoricalbalanceallocationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class HistoricalbalanceallocationModelAssembler extends RepresentationModelAssemblerSupport<Historicalbalanceallocation, HistoricalbalanceallocationModel> {

    public HistoricalbalanceallocationModelAssembler() {
        super(HistoricalbalanceallocationController.class, HistoricalbalanceallocationModel.class);
    }

    @Override
    public HistoricalbalanceallocationModel toModel(Historicalbalanceallocation historicalbalanceallocation) {
        HistoricalbalanceallocationModel model = instantiateModel(historicalbalanceallocation);
        model.setId(historicalbalanceallocation.getId());
        model.setQuantity(historicalbalanceallocation.getQuantity());
        model.setBalance(historicalbalanceallocation.getBalance());
        model.setCreateAt(historicalbalanceallocation.getCreateAt());
        model.setActive(historicalbalanceallocation.getActive());
        model.setIsReturn(historicalbalanceallocation.getIsReturn());
        model.set_more(historicalbalanceallocation.getMoreAboutHistoricalbalanceallocation());
        return model;
    }
}
