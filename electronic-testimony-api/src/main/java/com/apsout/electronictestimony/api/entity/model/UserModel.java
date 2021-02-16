package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {
    private Integer id;
    private String username;
    private String password;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
}
