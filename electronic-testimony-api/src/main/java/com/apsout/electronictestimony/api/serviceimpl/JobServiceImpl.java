package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutJob;
import com.apsout.electronictestimony.api.exception.JobFoundedException;
import com.apsout.electronictestimony.api.exception.JobNotFoundException;
import com.apsout.electronictestimony.api.repository.JobRepository;
import com.apsout.electronictestimony.api.service.EnterpriseService;
import com.apsout.electronictestimony.api.service.JobService;
import com.apsout.electronictestimony.api.util.allocator.JobAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobRepository repository;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public List<Job> getAll() {
        return repository.findAllByNotDeleted(States.EXISTENT);
    }

    @Override
    public Job getBy(int jobId) {
        Optional<Job> optionalJob = repository.findById(jobId);
        if (optionalJob.isPresent()) {
            return optionalJob.get();
        }
        throw new JobNotFoundException(String.format("Job not found for jobId: %d", jobId));
    }

    public List<Job> findBy(int enterpriseId) {
        return findAllBy(Arrays.asList(enterpriseId));
    }

    public Page<Job> findBy(int enterpriseId, Pageable pageable) {
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        List<Integer> enterpriseIds = enterpriseService.getEnterpriseIdsBy(enterprise);
        Page<Job> jobs = repository.findAllByEnterpriseId(enterpriseIds, pageable);
        jobs.stream().forEach(job -> {
            MoreAboutJob moreAboutJob = new MoreAboutJob();
            Enterprise enterprise1 = job.getEnterpriseByEnterpriseId();
            loadMoreInfoBy(enterprise1, moreAboutJob);
            job.setMoreAboutJob(moreAboutJob);
        });
        return jobs;
    }

    private void loadMoreInfoBy(Enterprise enterprise, MoreAboutJob moreAboutJob) {
        moreAboutJob.setEnterpriseName(enterprise.getName());
    }

    public Optional<Job> findBy(int enterpriseId, String jobDescription) {
        return repository.findAllByEnterpriseAndDescription(enterpriseId, jobDescription);
    }

    public Job onlySave(Job job) {
        return repository.save(job);
    }

    public Job saveIfNotExist(int entepriseId, String jobDescription) {
        Optional<Job> optional = findBy(entepriseId, jobDescription);
        if (optional.isPresent()) {
            return optional.get();
        }
        Job job = JobAllocator.build(jobDescription);
        return onlySave(job);
    }

    public Job save(Job job) {
        job = JobAllocator.build(job);
        checkExistentJob(job);
        return this.onlySave(job);
    }

    public Job update(Job oldJob) {
        checkExistentJob(oldJob);
        Job newJob = getBy(oldJob.getId());
        JobAllocator.forUpdate(oldJob, newJob);
        return repository.save(newJob);
    }

    private void checkExistentJob(Job job) {
        int entepriseId = job.getEnterpriseId();
        String description = job.getDescription();
        Optional<Job> optional = findBy(entepriseId, description);
        if (optional.isPresent()) {
            throw new JobFoundedException("El cargo ya se encuentra registrado.");
        }
    }

    public Job delete(int jobId) {
        Job job = getBy(jobId);
        JobAllocator.forDelete(job);
        final Job saved = repository.save(job);
        logger.info(String.format("Job deleted with jobId: %d", jobId));
        return saved;
    }

    public List<Job> findAllBy(List<Integer> enterpriseIds) {
        return repository.findAllByEnterpriseId(enterpriseIds);
    }

    public Optional<Job> findByPerson(Person person) {
        return repository.findByPersonId(person.getId());
    }

    public Job getByPerson(Person person) {
        Optional<Job> optional = this.findByPerson(person);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new JobNotFoundException(String.format("Job not found for personId; %d", person.getId()));
    }
}
