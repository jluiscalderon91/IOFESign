package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.HeadbalanceallocationController;
import com.apsout.electronictestimony.api.controller.JobController;
import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.entity.model.HeadbalanceallocationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class HeadbalanceallocationModelAssembler extends RepresentationModelAssemblerSupport<Headbalanceallocation, HeadbalanceallocationModel> {

    public HeadbalanceallocationModelAssembler() {
        super(HeadbalanceallocationController.class, HeadbalanceallocationModel.class);
    }

    @Override
    public HeadbalanceallocationModel toModel(Headbalanceallocation headbalanceallocation) {
        HeadbalanceallocationModel model = instantiateModel(headbalanceallocation);
        model.add(linkTo(JobController.class).withRel("headbalanceallocations"));
        model.setId(headbalanceallocation.getId());
        model.setEnterpriseId(headbalanceallocation.getEnterpriseId());
        model.setQuantity(headbalanceallocation.getQuantity());
        model.setBalance(headbalanceallocation.getBalance());
        model.setLastUpdateAt(headbalanceallocation.getLastUpdateAt());
        model.setCreateAt(headbalanceallocation.getCreateAt());
        model.setActive(headbalanceallocation.getActive());
        return model;
    }
}
