package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.model.JobModel;
import com.apsout.electronictestimony.api.modelassembler.JobModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.OutsideJobModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private AccessResourceService accessResourceService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:job:get:enterprise')")
    @GetMapping(value = "/enterprises/{enterpriseId}/jobs")
    public ResponseEntity<CollectionModel<JobModel>> getBy(@PathVariable int enterpriseId, Pageable pageable, HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Page<Job> jobs = jobService.findBy(enterpriseId, pageable);
        JobModelAssembler assembler = new JobModelAssembler();
        PagedModel<JobModel> pagedModel = resourcesAssembler.toModel(jobs, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:job:add')")
    @PostMapping(value = "/jobs")
    public ResponseEntity<JobModel> save(@RequestBody Job job) {
        job = jobService.save(job);
        JobModelAssembler assembler = new JobModelAssembler();
        JobModel jobModel = assembler.toModel(job);
        return ResponseEntity.ok().body(jobModel);
    }

    @PreAuthorize("hasAuthority('own:job:edit')")
    @PutMapping(value = "/jobs")
    public ResponseEntity<JobModel> update(@RequestBody Job job) {
        job = jobService.update(job);
        JobModelAssembler assembler = new JobModelAssembler();
        JobModel jobModel = assembler.toModel(job);
        return ResponseEntity.ok(jobModel);
    }

    @PreAuthorize("hasAuthority('own:job:delete')")
    @DeleteMapping(value = "/jobs/{jobId}")
    public ResponseEntity<JobModel> delete(@PathVariable("jobId") int jobId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfJobId(request, jobId);
        Job job = jobService.delete(jobId);
        JobModelAssembler assembler = new JobModelAssembler();
        JobModel jobModel = assembler.toModel(job);
        return ResponseEntity.ok(jobModel);
    }

    @PreAuthorize("hasAuthority('outside:job:get:enterprise')")
    @GetMapping(value = "/outside/enterprises/{enterpriseId}/jobs")
    public ResponseEntity<CollectionModel<JobModel>> getBy2(@PathVariable int enterpriseId, HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        List<Job> jobs = jobService.findBy(enterpriseId);
        OutsideJobModelAssembler assembler = new OutsideJobModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(jobs));
    }
}
