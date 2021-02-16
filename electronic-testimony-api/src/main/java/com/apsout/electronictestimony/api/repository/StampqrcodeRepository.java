package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Stampqrcode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StampqrcodeRepository extends CrudRepository<Stampqrcode, Integer> {

    @Query(value = "SELECT wsq.stampqrcodeByStampqrcodeId " +
            "FROM Workflowstampqrcode wsq " +
            "WHERE wsq.workflowId = :workflowId " +
            "AND wsq.active = 1 " +
            "AND wsq.deleted = 0 " +
            "AND wsq.stampqrcodeByStampqrcodeId.active = 1 " +
            "AND wsq.stampqrcodeByStampqrcodeId.deleted = 0 ")
    List<Stampqrcode> findAllByWorkflow(int workflowId);

    @Query("SELECT sqr " +
            "FROM Stampqrcode sqr " +
            "WHERE sqr.id = :stampqrcodeId " +
            "AND sqr.active = 1 " +
            "AND sqr.deleted = 0 ")
    Optional<Stampqrcode> findBy(@Param("stampqrcodeId") int stampqrcodeId);
}
