package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutParticipant {
    private String workflowDescription;
    private String operationDescription;
    private String personFullName;
    private Integer maxParticipants;
    private String jobDescription;
}
