package com.apsout.electronictestimony.api.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "shoppingcards")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingcardModel extends RepresentationModel<ShoppingcardModel> {
    private Integer id;
    private Integer partnerId;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private Integer orderCard;
    private Timestamp createAt;
    private Byte active;
}
