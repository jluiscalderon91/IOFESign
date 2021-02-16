package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "deliverymails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliverymailModel extends RepresentationModel<DeliverymailModel> {
    private Integer id;
    private Integer documentId;
    private Integer priority;
    private String deliverymailcol;
    private Boolean sent;
    private Timestamp sentAt;
    private Timestamp lastAttemptAt;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Contentdeliverymail contentdeliverymail;
    private Document documentByDocumentId;
}
