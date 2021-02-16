package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Operator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "assigner")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignerModel extends RepresentationModel<AssignerModel> {
    private Integer id;
    private Integer documentId;
    private Integer operatorId;
    private Byte completed;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Document documentByDocumentId;
    private Integer orderOperation;
    private Operator operatorByOperatorId;
}
