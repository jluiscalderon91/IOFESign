package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Personrubric;
import com.apsout.electronictestimony.api.entity.Stampimage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonrubricRepository extends CrudRepository<Personrubric, Integer> {

    @Query("SELECT pr " +
            "FROM Personrubric pr " +
            "WHERE pr.personByPersonId = :person " +
            "AND pr.active = 1 " +
            "AND pr.deleted = 0 ")
    Optional<Personrubric> findBy(@Param("person") Person person);
//
//    @Query(value = "SELECT wsi.stampimageByStampimageId " +
//            "FROM Workflowstampimage wsi " +
//            "WHERE wsi.workflowId = :workflowId " +
//            "AND wsi.active = 1 " +
//            "AND wsi.deleted = 0 " +
//            "AND wsi.stampimageByStampimageId.active = 1 " +
//            "AND wsi.stampimageByStampimageId.deleted = 0 ")
//    List<Personrubric> findAllByWorkflow(int workflowId);

}
