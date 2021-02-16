package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutObservationcancel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "observationcancels")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationcancelModel extends RepresentationModel<ObservationcancelModel> {
    private Integer id;
    private Integer personId;
    private String description;
    private Timestamp createAt;
    private MoreAboutObservationcancel _more;
}
