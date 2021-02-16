package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "documents")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentModel extends RepresentationModel<DocumentModel> {
    private Integer id;
    private Integer personId;
    private Integer stateId;
    private Integer type;
    private String enterpriseDocumentNumber;
    private String hashIdentifier;
    private Byte finished;
    private String subject;
    private String description;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Byte hasMultipleAttachments;
    private Boolean closedStamping;
    private MoreAboutDocument _more;
}
