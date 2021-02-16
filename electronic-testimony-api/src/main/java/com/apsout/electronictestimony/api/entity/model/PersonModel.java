package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.common.MoreAboutPerson;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Data
@Relation(collectionRelation = "people")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonModel extends RepresentationModel<PersonModel> {
    private Integer id;
    private Integer partnerId;
    private Integer enterpriseId;
    private Integer enterpriseIdView;
    private String enterpriseName;
    private Short type;
    private String documentType;
    private String documentNumber;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String cellphone;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Byte replaceable;
    private MoreAboutPerson _more;
}
