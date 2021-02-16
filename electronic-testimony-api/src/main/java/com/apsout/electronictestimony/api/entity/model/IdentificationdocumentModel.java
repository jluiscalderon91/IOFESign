package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "identificationdocuments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentificationdocumentModel extends RepresentationModel<IdentificationdocumentModel> {
    private Integer id;
    private String longDescription;
    private String shortDescription;
}
