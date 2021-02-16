package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JobAllocator {

    public static Job build(String description) {
        Job job = new Job();
        job.setDescription(description);
        ofPostMethod(job);
        return job;
    }

    public static Job build(Job job) {
        final String description = job.getDescription().trim();
        job.setDescription(description);
        ofPostMethod(job);
        return job;
    }

    public static void forUpdate(Job oldJob, Job newJob) {
        newJob.setEnterpriseId(oldJob.getEnterpriseId());
        newJob.setDescription(oldJob.getDescription());
        newJob.setActive(oldJob.getActive());
    }

    public static void ofPostMethod(Job job) {
        job.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        job.setActive(States.ACTIVE);
        job.setDeleted(States.EXISTENT);
    }

    public static Job buildInitialJob(Enterprise enterprise) {
        Job job = new Job();
        job.setEnterpriseId(enterprise.getId());
        job.setEnterpriseByEnterpriseId(enterprise);
        job.setDescription("GERENTE GENERAL");
        ofPostMethod(job);
        return job;
    }

    public static void forDelete(Job job) {
        job.setDeleted(States.DELETED);
    }
}
