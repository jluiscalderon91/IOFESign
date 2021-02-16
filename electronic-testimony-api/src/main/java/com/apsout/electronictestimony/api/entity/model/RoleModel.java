package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleModel extends RepresentationModel<RoleModel> {
    private Integer id;
    private String name;
    private String abbreviation;
    private String description;
    private Byte editable;
    private Timestamp createAt;
    private Byte active;
}
