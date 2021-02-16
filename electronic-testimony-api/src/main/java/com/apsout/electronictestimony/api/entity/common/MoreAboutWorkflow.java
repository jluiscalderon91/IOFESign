package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutWorkflow {
    private String enterpriseName;
    private String enterpriseTradeName;
    private byte requiredSieConfig;
    private byte sieConfigured;
    private byte ready2Use;
}
