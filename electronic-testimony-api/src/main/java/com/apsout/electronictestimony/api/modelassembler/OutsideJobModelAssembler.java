package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.JobController;
import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.model.JobModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class OutsideJobModelAssembler extends  RepresentationModelAssemblerSupport<Job, JobModel> {

    public OutsideJobModelAssembler() {
        super(JobController.class, JobModel.class);
    }

    @Override
    public JobModel toModel(Job job) {
        JobModel jobModel = instantiateModel(job);
        jobModel.setId(job.getId());
        jobModel.setDescription(job.getDescription());
        jobModel.setEnterpriseId(job.getEnterpriseId());
        return jobModel;
    }
}
