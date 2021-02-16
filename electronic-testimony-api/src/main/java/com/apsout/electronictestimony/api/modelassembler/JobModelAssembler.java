package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.JobController;
import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.model.JobModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class JobModelAssembler extends RepresentationModelAssemblerSupport<Job, JobModel> {

    public JobModelAssembler() {
        super(JobController.class, JobModel.class);
    }

    @Override
    public JobModel toModel(Job job) {
        JobModel jobModel = instantiateModel(job);
        jobModel.add(linkTo(JobController.class).withRel("jobs"));
        jobModel.setId(job.getId());
        jobModel.setDescription(job.getDescription());
        jobModel.setCreateAt(job.getCreateAt());
        jobModel.setActive(job.getActive());
        jobModel.setEnterpriseId(job.getEnterpriseId());
        jobModel.set_more(job.getMoreAboutJob());
        return jobModel;
    }
}
