package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutDocumenttraceability {
    private String stateDescription;
    private String fullnameOperator;
}
