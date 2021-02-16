package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "documents")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordretrieverModel extends RepresentationModel<PasswordretrieverModel> {
    private Integer id;
    private String uuid;
    private String verificationCode;
    private Byte sent;
    private Timestamp sentAt;
    private Byte matchCode;
    private Timestamp matchCodeAt;
    private String hashFirstStep;
    private String hashSecondStep;
    private Byte finished;
    private Timestamp finishedAt;
}
