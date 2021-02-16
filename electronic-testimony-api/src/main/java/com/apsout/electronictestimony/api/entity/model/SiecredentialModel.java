package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutSiecredential;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "siecredentials", itemRelation = "siecredential")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiecredentialModel extends RepresentationModel<SiecredentialModel> {
    private Integer id;
    private String username;
    private Double version;
    private Timestamp createAt;
    private Byte active;
    private String observation;
    private MoreAboutSiecredential _more;
}
