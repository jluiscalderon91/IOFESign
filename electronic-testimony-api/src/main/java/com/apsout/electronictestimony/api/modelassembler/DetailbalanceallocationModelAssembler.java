package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.DetailbalanceallocationController;
import com.apsout.electronictestimony.api.entity.Detailbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.DetailbalanceallocationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class DetailbalanceallocationModelAssembler extends RepresentationModelAssemblerSupport<Detailbalanceallocation, DetailbalanceallocationModel> {

    public DetailbalanceallocationModelAssembler() {
        super(DetailbalanceallocationController.class, DetailbalanceallocationModel.class);
    }

    @Override
    public DetailbalanceallocationModel toModel(Detailbalanceallocation detailbalanceallocation) {
        DetailbalanceallocationModel model = instantiateModel(detailbalanceallocation);
        model.setId(detailbalanceallocation.getId());
        model.setServiceweightId(detailbalanceallocation.getServiceweightId());
        model.setOldBalance(detailbalanceallocation.getOldBalance());
        model.setWeight(detailbalanceallocation.getWeight());
        model.setActualBalance(detailbalanceallocation.getActualBalance());
        model.setCreateAt(detailbalanceallocation.getCreateAt());
        model.setActive(detailbalanceallocation.getActive());
        model.set_more(detailbalanceallocation.getMoreAboutDetailbalance());
        return model;
    }
}
