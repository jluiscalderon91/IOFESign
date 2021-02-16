package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.Sierecipient;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@Relation(itemRelation = "sieemail", collectionRelation = "sieemails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SieemailModel extends RepresentationModel<SieemailModel> {
    private Integer id;
    private String subject;
    private Collection<Sierecipient> sierecipientsById;
    private String body;
    private Timestamp createAt;
}
