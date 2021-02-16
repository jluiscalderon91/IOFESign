package com.apsout.electronictestimony.api.service;

import javax.servlet.http.HttpServletRequest;

public interface AccessResourceService {
    void validateIfBelongEnterpriseId(HttpServletRequest request, int enterpriseId);

    void validate2participants(HttpServletRequest request, int workflowId);

    void validateIfBelong2EnterpriseOfDocumentId(HttpServletRequest request, int documentId);

    void validateIfBelong2EnterpriseOfResourceId(HttpServletRequest request, int resourceId);

    void validateIfBelong2EnterpriseOfPersonId(HttpServletRequest request, int personId);

    void validateIfPersonIdIsPersonOfRequest(HttpServletRequest request, int personId);

    void validateIfBelong2EnterpriseOfWorkflowId(HttpServletRequest request, int workflowId);

    void validateIfBelong2EnterpriseOfJobId(HttpServletRequest request, int jobId);

    void validateIfBelong2EnterpriseOfDocumentIdOperators(HttpServletRequest request, int documentId);

    void validateIfPersonRequestIsAuthorizedPartner(HttpServletRequest request, int partnerId);

    void validateIfPersonOfRequestIsSuperadmin(HttpServletRequest request);

    void validateIfPersonOfRequestIsSuperadminOrPartner(HttpServletRequest request);

    void validateIfBelong2PartnerIdOfEnterpriseId(HttpServletRequest request, int enterpriseId);

    void validateIfBelong2EnterpriseOfShoppingcardId(HttpServletRequest request, int shoppingcardId);

    void validateIfBelong2Partner(HttpServletRequest request, int partnerId);

    void validateIfPersonRequestIsAuthorizedAdmin(HttpServletRequest request, int partnerId);

    void validateIfPersonRequestBelong2PartnerOfEnterprise(HttpServletRequest request, int enterpriseId);

    void validateIfPersonRequestIsAuthorizedAdmin_(HttpServletRequest request, int enterpriseId);

//    void validateIfBelongEnterpriseIdOnly(HttpServletRequest request, int enterpriseId);
}
