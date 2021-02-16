package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "authorities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailtemplateModel extends RepresentationModel<MailtemplateModel> {
    private Integer id;
    private Integer enterpriseId;
    private Integer type;
    private String subject;
    private String body;
    private Integer recipientType;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
}
