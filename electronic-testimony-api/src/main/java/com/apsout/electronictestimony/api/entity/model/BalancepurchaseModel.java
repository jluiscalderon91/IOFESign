package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "balancepurchases")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalancepurchaseModel extends RepresentationModel<BalancepurchaseModel> {
    private Integer id;
    private Integer enterpriseId;
    private Integer personId;
    private BigDecimal quantity;
    private BigDecimal price;
    private Timestamp createAt;
    private String enterpriseName;
    private String applicantFullname;
    private Byte active;
}
