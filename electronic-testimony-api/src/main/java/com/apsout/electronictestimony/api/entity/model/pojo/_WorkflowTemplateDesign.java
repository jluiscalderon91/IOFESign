package com.apsout.electronictestimony.api.entity.model.pojo;

import com.apsout.electronictestimony.api.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _WorkflowTemplateDesign {
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
