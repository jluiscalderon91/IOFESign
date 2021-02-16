package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowstampimage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowstampimageRepository extends CrudRepository<Workflowstampimage, Integer> {

    @Query(value = "SELECT wsi " +
            "FROM Workflowstampimage wsi " +
            "WHERE wsi.workflowId = :workflowId " +
            "AND wsi.stampimageId = :stampimageId " +
            "AND wsi.active = 1 " +
            "AND wsi.deleted = 0 ")
    Optional<Workflowstampimage> findBy(int workflowId, int stampimageId);
}
