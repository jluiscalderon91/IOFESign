package com.apsout.electronictestimony.api.entity.model;

import com.apsout.electronictestimony.api.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Data
@Relation(collectionRelation = "workflowtemplatedesigns")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowTemplateDesignModel extends RepresentationModel<WorkflowTemplateDesignModel> {
    private Integer workflowId;
    private Stamptestfile stamptestfile;
    private List<Stamplegend> stamplegends;
    private List<Stampimage> stampimages;
    private List<Stampqrcode> stampqrcodes;
    private List<Stampdatetime> stampdatetimes;
    private Sieemail sieemail;
    private Stamplayoutfile stamplayoutfile;
    private List<Stamprubric> stamprubrics;
}
