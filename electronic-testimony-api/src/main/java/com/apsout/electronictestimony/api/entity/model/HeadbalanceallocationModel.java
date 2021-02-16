package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "Headbalanceallocations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeadbalanceallocationModel extends RepresentationModel<HeadbalanceallocationModel> {
    private Integer id;
    private Integer enterpriseId;
    private BigDecimal quantity;
    private BigDecimal balance;
    private Timestamp lastUpdateAt;
    private Timestamp createAt;
    private Byte active;
}
