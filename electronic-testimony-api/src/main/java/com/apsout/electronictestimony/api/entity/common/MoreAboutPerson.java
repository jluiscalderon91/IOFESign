package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutPerson {
    private Integer jobId;
    private String jobDescription;
    private Integer participantType;
    private Integer orderParticipant;
    private String enterpriseName;
    private String enterpriseTradeName;
    private String enterpriseDocumentNumberView;
    private String enterpriseNameView;
    private String enterpriseTradeNameView;
    private String roles;
    private String rolesDescription;
    private Integer operationId;
    private String operationDescription;
    private String rolesNameView;
    private Byte operationCompleted;
    private String rubricFilename;
    private String base64RubricFile;
    private String rubricFileExtension;
    private Boolean uploadRubric;
    private Boolean digitalSignature;
}
