package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> getAll();

    Job getBy(int jobId);

    List<Job> findBy(int enterpriseId);

    Page<Job> findBy(int enterpriseId, Pageable pageable);

    Optional<Job> findBy(int entepriseId, String jobDescription);

    Job saveIfNotExist(int entepriseId, String jobDescription);

    Job onlySave(Job job);

    Job save(Job job);

    Job update(Job job);

    Job delete(int jobId);

    List<Job> findAllBy(List<Integer> enterpriseIds);

    Optional<Job> findByPerson(Person person);

    Job getByPerson(Person person);
}
