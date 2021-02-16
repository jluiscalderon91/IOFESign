package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "resources")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceModel extends RepresentationModel<ResourceModel> {
    private Integer id;
    private Integer documentId;
    private String type;
    private String path;
    private String originalName;
    private String newName;
    private String extension;
    private Long length;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
}
