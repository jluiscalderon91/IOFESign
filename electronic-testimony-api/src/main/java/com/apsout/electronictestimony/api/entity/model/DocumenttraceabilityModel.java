package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumenttraceability;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "documenttraceabilities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumenttraceabilityModel extends RepresentationModel<DocumenttraceabilityModel> {
    private Integer id;
    private Integer documentId;
    private Integer stateId;
    private Integer personId;
    private Byte visible;
    private Timestamp createAt;
    private MoreAboutDocumenttraceability _more;
}
