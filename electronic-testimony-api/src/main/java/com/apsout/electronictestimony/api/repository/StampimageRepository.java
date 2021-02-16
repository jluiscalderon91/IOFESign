package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stampimage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StampimageRepository extends CrudRepository<Stampimage, Integer> {

    @Query("SELECT si " +
            "FROM Stampimage si " +
            "WHERE si.id = :stampimageId " +
            "AND si.active = 1 " +
            "AND si.deleted = 0 ")
    Optional<Stampimage> findBy(@Param("stampimageId") int stampimageId);

    @Query(value = "SELECT wsi.stampimageByStampimageId " +
            "FROM Workflowstampimage wsi " +
            "WHERE wsi.workflowId = :workflowId " +
            "AND wsi.active = 1 " +
            "AND wsi.deleted = 0 " +
            "AND wsi.stampimageByStampimageId.active = 1 " +
            "AND wsi.stampimageByStampimageId.deleted = 0 ")
    List<Stampimage> findAllByWorkflow(int workflowId);
}
