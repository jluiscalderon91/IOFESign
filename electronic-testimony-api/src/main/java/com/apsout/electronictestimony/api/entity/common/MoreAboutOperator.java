package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutOperator {
    private String fullNamePerson;
    private String operationDescription;
}
