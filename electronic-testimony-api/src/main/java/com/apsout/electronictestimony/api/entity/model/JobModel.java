package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutJob;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "jobs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobModel extends RepresentationModel<JobModel> {
    private Integer id;
    private Integer enterpriseId;
    private String description;
    private Timestamp createAt;
    private Byte active;
    private MoreAboutJob _more;
}
