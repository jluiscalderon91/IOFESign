package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDetailbalance;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "detailbalanceallocations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailbalanceallocationModel extends RepresentationModel<DetailbalanceallocationModel> {
    private Integer id;
    private Integer headbalanceallocationId;
    private Integer documentId;
    private Integer personId;
    private BigDecimal oldBalance;
    private Integer serviceweightId;
    private BigDecimal weight;
    private BigDecimal actualBalance;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Headbalanceallocation headbalanceallocationByHeadbalanceallocationId;
    private Document documentByDocumentId;
    private Person personByPersonId;
    private MoreAboutDetailbalance _more;
}
