package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends PagingAndSortingRepository<Job, Integer> {
    @Query(value = "SELECT j " +
            "FROM Job j " +
            "WHERE j.deleted = 0")
    List<Job> findAllByNotDeleted(byte deleted);

    @Query(value = "SELECT j " +
            "FROM Job j " +
            "WHERE j.enterpriseId IN :enterpriseIds " +
            "AND j.active = 1 " +
            "AND j.deleted = 0")
    List<Job> findAllByEnterpriseId(List<Integer> enterpriseIds);

    @Query(value = "SELECT j " +
            "FROM Job j " +
            "WHERE j.enterpriseId =:enterpriseId " +
            "AND j.description = :jobDescription " +
            "AND j.active = 1 " +
            "AND j.deleted = 0")
    Optional<Job> findAllByEnterpriseAndDescription(int enterpriseId, String jobDescription);

    @Query(value = "SELECT j " +
            "FROM Job j " +
            "WHERE j.enterpriseId IN :enterpriseIds " +
            "AND j.deleted = 0 " +
            "ORDER BY j.enterpriseByEnterpriseId.id ASC, j.description ASC")
    Page<Job> findAllByEnterpriseId(List<Integer> enterpriseIds, Pageable pageable);

    @Query(value = "SELECT e.jobByJobId " +
            "FROM Employee e " +
            "WHERE e.personId = :personId " +
            "AND e.active = 1 " +
            "AND e.deleted = 0 ")
    Optional<Job> findByPersonId(int personId);
}
