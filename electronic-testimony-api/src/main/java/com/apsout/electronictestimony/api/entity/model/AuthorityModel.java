package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "authorities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorityModel extends RepresentationModel<AuthorityModel> {
    private Integer id;
    private Integer module;
    private Byte onlySuperadmin;
    private String code;
    private String description;
    private Timestamp createAt;
    private Byte active;
}
