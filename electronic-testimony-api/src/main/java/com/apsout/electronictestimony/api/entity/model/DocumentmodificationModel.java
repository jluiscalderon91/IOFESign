package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumentmodification;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "documentmodifications")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentmodificationModel extends RepresentationModel<DocumentmodificationModel> {
    private Integer id;
    private Integer personId;
    private String description;
    private Timestamp createAt;
    private MoreAboutDocumentmodification _more;
}
