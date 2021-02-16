package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutParticipant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "participants")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipantModel extends RepresentationModel<ParticipantModel> {
    private Integer id;
    private Integer workflowId;
    private Integer operationId;
    private Integer personId;
    private Integer participantType;
    private Integer orderParticipant;
    private Boolean uploadRubric;
    private Boolean digitalSignature;
    private Integer typeElectronicSignature;
    private Timestamp createAt;
    private Byte addTsa;
    private Byte sendAlert;
    private Byte sendNotification;
    private Byte active;
    private String observation;
    private MoreAboutParticipant _more;
}
