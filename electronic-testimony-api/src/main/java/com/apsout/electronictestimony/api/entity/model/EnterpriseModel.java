package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutEnterprise;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "enterprises")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseModel extends RepresentationModel<EnterpriseModel> {
    private Integer id;
    private Integer partnerId;
    private String documentType;
    private String documentNumber;
    private String name;
    private String tradeName;
    private Byte excluded;
    private Byte isPartner;
    private Boolean isCustomer;
    private Integer createdByPersonId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private MoreAboutEnterprise _more;
}
