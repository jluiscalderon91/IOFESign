package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutWorkflow;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "workflows")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowModel extends RepresentationModel<WorkflowModel> {
    private Integer id;
    private Integer enterpriseId;
    private String description;
    private Byte batch;
    private Integer maxParticipants;
    private Byte completed;
    private Byte ready2Use;
    private Byte deliver;
    private String code;
    private Integer type;
    private Byte dynamic;
    private Timestamp createAt;
    private Byte active;
    private String observation;
    private MoreAboutWorkflow _more;
}
