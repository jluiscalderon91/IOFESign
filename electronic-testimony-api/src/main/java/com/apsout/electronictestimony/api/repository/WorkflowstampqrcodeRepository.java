package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Workflowstampqrcode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowstampqrcodeRepository extends CrudRepository<Workflowstampqrcode, Integer> {

    @Query(value = "SELECT wsq " +
            "FROM Workflowstampqrcode wsq " +
            "WHERE wsq.workflowId = :workflowId " +
            "AND wsq.stampqrcodeId = :stampqrcodeId " +
            "AND wsq.active = 1 " +
            "AND wsq.deleted = 0 ")
    Optional<Workflowstampqrcode> findBy(int workflowId, int stampqrcodeId);
}
