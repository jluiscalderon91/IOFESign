package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Deliverymail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "contentdeliverymails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentdeliverymailModel extends RepresentationModel<ContentdeliverymailModel> {
    private Integer id;
    private Integer deliverymailId;
    private String to;
    private String cc;
    private String subject;
    private String body;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Deliverymail deliverymailByDeliverymailId;
}
