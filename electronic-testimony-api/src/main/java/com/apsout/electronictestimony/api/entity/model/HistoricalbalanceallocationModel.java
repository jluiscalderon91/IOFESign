package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutHistoricalbalanceallocation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "historicalbalanceallocations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricalbalanceallocationModel extends RepresentationModel<HistoricalbalanceallocationModel> {
    private Integer id;
    private Integer headbalanceallocationId;
    private Integer personId;
    private BigDecimal quantity;
    private BigDecimal balance;
    private Timestamp createAt;
    private Byte isReturn;
    private Byte active;
    private Byte deleted;
    private String observation;
    private MoreAboutHistoricalbalanceallocation _more;
}
