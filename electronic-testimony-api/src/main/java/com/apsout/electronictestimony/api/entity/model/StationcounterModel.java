package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "stationcounters")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationcounterModel extends RepresentationModel<StationcounterModel> {
    private Integer id;
    private Integer type;
    private Integer initialAmount;
    private Integer finalAmount;
    private Byte completed;
    private Timestamp updateAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Integer workflowId;
    private Integer personId;
}
