package com.apsout.electronictestimony.api.entity.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Operator {
    private Byte isOperator;
    private Integer operationId;
    private String operationDescription;
    private String operatorName;
    private String issuerCertName;
    private String tsa;
    private String certainDateTime;
    private Byte correctOperation;
    private String jobDescription;
    private String stateDescription;
    private String operatedAt;
    private String comment;
    private Timestamp createAt;
    private Integer orderOperation;
    private Byte isOldSignature;
    private Boolean digitalSignature;
    private Integer typeElectronicSignature;

    public _Operator() {
    }

    public _Operator(Integer operationId, String operatorName, String issuerCertName) {
        this.operationId = operationId;
        this.operatorName = operatorName;
        this.issuerCertName = issuerCertName;
    }

    public _Operator(Integer operationId, String operatorName, String issuerCertName, String tsa, String certainDateTime) {
        this(operationId, operatorName, issuerCertName);
        this.tsa = tsa;
        this.certainDateTime = certainDateTime;
    }

    public _Operator(Byte isOperator,
                     String operationDescription,
                     String operatorName,
                     String jobDescription,
                     String stateDescription,
                     String operatedAt,
                     String comment,
                     Integer orderOperation){
        this.isOperator = isOperator;
        this.operationDescription = operationDescription;
        this.operatorName = operatorName;
        this.jobDescription = jobDescription;
        this.stateDescription = stateDescription;
        this.operatedAt = operatedAt;
        this.comment = comment;
        this.orderOperation = orderOperation;
    }

}
