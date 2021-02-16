package com.apsout.electronictestimony.api.entity.model.pojo;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Embedded {
    private String today;
    private float balance;
    private byte hasConfigBalance;
    private String moment;
    private _Setting setting;
    private _Profile profile;
    private List<Role> roles;
    private List<_Operator> operatorsInfo;
    private List<Job> jobs;
    private List<Operation> operations;
    private List<Enterprise> enterprises;
    private List<Workflow> workflows;
    private List<Authority> authorities;
    private List<Identificationdocument> identificationDocuments;
    private List<Participanttype> participanttypes;
    private List<Persontype> persontypes;
    private List<Participant> participants;
    private List<Fontcolor> fontcolors;
    private List<Fontsize> fontsizes;
    private List<Fonttype> fonttypes;
    private List<Pagestamp> pagestamps;
    private List<Contentposition> contentpositions;
    private List<Module> modules;
    private List<State> states;
    private Siecredential siecredential;
    private Siecertificate siecertificate;
    private List<Workflowtype> workflowtypes;
    private List<Partner> partners;
    private _Others others;
    private List<Shoppingcard> shoppingcards;
    private Document document;
    private List<Service> services;
    private List<Documentmodification> documentmodifications;
    private String urlLastDocumentResource;

    public _Embedded() {
    }

    public _Embedded(List<_Operator> operatorsInfo) {
        this.operatorsInfo = operatorsInfo;
    }
}
