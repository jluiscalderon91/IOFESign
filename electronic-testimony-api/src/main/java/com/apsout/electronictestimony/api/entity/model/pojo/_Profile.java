package com.apsout.electronictestimony.api.entity.model.pojo;

import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Profile {
    private Integer personId;
    private Integer partnerId;
    private String partnerName;
    private String personName;
    private String jobDescription;
    private Integer enterpriseId;
    private String enterpriseDocumentNumber;
    private Integer enterpriseIdView;
    private String enterpriseName;
    private String enterpriseNameView;
    private String enterpriseDocumentNumberView;
    private String enterpriseTradeNameView;
    private Integer participantType;
    private Short personType;
    private String enterpriseTradeName;
    private List<Role> roles;
    private List<Authority> authorities;
    private List<Workflow> workflows;
}
