package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Operation;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutOperator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "operators")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatorModel extends RepresentationModel<OperatorModel> {
    private Integer id;
    private Integer personId;
    private Integer operationId;
    private Integer orderOperation;
    private Boolean uploadRubric;
    private Boolean digitalSignature;
    private Integer typeElectronicSignature;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Person personByPersonId;
    private Operation operationByOperationId;
    private Integer enterpriseId;
    private MoreAboutOperator _more;
}
