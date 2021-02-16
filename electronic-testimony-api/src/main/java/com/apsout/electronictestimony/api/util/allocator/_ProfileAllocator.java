package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.pojo._Profile;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;

import java.util.List;

public class _ProfileAllocator {

    public static _Profile build(Person person,
                                 Job job,
                                 Enterprise enterprise,
                                 Enterprise enterpriseView,
                                 Scope scope,
                                 List<Role> roles,
                                 List<Authority> authorities,
                                 List<Workflow> workflows) {
        _Profile profile = new _Profile();
        profile.setPersonId(person.getId());
        profile.setPartnerId(person.getPartnerId());
        profile.setPersonName(person.getFullname());
        profile.setEnterpriseId(enterprise.getId());
        profile.setEnterpriseDocumentNumber(enterprise.getDocumentNumber());
        profile.setEnterpriseName(enterprise.getName());
        profile.setEnterpriseIdView(enterpriseView.getId());
        profile.setEnterpriseNameView(enterpriseView.getName());
        profile.setJobDescription(job.getDescription());
        profile.setParticipantType(scope.getParticipantType());
        profile.setPersonType(person.getType());
        profile.setEnterpriseTradeNameView(enterpriseView.getTradeName());
        profile.setEnterpriseDocumentNumberView(enterpriseView.getDocumentNumber());
        profile.setRoles(roles);
        profile.setAuthorities(authorities);
        profile.setWorkflows(workflows);
        return profile;
    }

    public static _Profile build(Partner partner,
                                 Person person,
                                 Job job,
                                 Enterprise enterprise,
                                 Enterprise enterpriseView,
                                 Scope scope,
                                 List<Role> roles,
                                 List<Authority> authorities,
                                 List<Workflow> workflows) {
        _Profile profile = build(person, job, enterprise, enterpriseView, scope, roles, authorities, workflows);
        profile.setPartnerId(partner.getId());
        profile.setPartnerName(partner.getName());
        return profile;
    }
}
