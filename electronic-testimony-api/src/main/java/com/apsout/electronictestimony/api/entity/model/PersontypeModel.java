package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "persontypes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersontypeModel extends RepresentationModel<PersontypeModel> {
    private Integer id;
    private String description;
}
