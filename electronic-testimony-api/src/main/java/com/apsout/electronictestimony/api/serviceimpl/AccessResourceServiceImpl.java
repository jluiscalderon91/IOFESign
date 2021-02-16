package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.exception.UnauthorizedResourceException;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.apsout.electronictestimony.api.util.statics.Roles.*;

@Service
public class AccessResourceServiceImpl implements AccessResourceService {
    private static final Logger logger = LoggerFactory.getLogger(AccessResourceServiceImpl.class);

    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private JobService jobService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ShoppingcardService shoppingcardService;
    private String messageUnauthorized = "No tiene permisos sobre el recurso solicitado, por favor comuníquese con el administrador.";

    /**
     * Validate if the request's user has access to enterpriseId
     */
    @Override
    public void validateIfBelongEnterpriseId(HttpServletRequest request, int enterpriseId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Enterprise enterprise = enterpriseService.getBy(enterpriseId);
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                if (!person.getEnterpriseId().equals(enterprise.getId())) {
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to list participants of workflowId
     */
    @Override
    public void validate2participants(HttpServletRequest request, int workflowId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Workflow workflow = workflowService.getBy(workflowId);
            Enterprise enterprise = workflow.getEnterpriseByEnterpriseId();
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                if (person.getEnterpriseId() != workflow.getEnterpriseId()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, enterpriseId: %d, workflowId: %d", person.getId(), person.getEnterpriseId(), workflowId));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
                boolean isAdmin = userService.requestUserHasRole(request, ADMIN);
                if (!isAdmin) {
                    List<Workflow> workflows = workflowService.findAllWhereIsParticipantOrAssigned(person);
                    Optional<Workflow> optional = workflows.stream().filter(wflow -> workflowId == wflow.getId()).findFirst();
                    if (!optional.isPresent()) {
                        logger.warn(String.format("Unauthorized request by personId: %d, enterpriseId: %d, workflowId: %d", person.getId(), person.getEnterpriseId(), workflowId));
                        throw new UnauthorizedResourceException(messageUnauthorized);
                    }
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to document's enterpriseId
     */
    @Override
    public void validateIfBelong2EnterpriseOfDocumentId(HttpServletRequest request, int documentId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Document document = documentService.getByNoDeleted(documentId);
            Person personDoc = document.getPersonByPersonId();
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && personDoc.getPartnerId().equals(person.getPartnerId());
            if (!isPartnerAuthorized) {
                if (person.getEnterpriseId() != personDoc.getEnterpriseId()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, enterpriseId: %d, documentId: %d", person.getId(), person.getEnterpriseId(), documentId));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to resources's resourceId
     */
    @Override
    public void validateIfBelong2EnterpriseOfResourceId(HttpServletRequest request, int resourceId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Enterprise enterprise = enterpriseService.findOf(resourceId);
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                if (person.getEnterpriseId() != enterprise.getId()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, above resourceId: %d", person.getId(), resourceId));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to person's personId
     */
    @Override
    public void validateIfBelong2EnterpriseOfPersonId(HttpServletRequest request, int personId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person personReq = personService.getBy(request);
            Person personParam = personService.getBy(personId);
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && personReq.getPartnerId().equals(personParam.getPartnerId());
            if (!isPartnerAuthorized) {
                if (personReq.getEnterpriseId() != personParam.getEnterpriseId()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, above other personId: %d - 1", personReq.getId(), personParam.getId()));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    @Override
    public void validateIfPersonIdIsPersonOfRequest(HttpServletRequest request, int personId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person personReq = personService.getBy(request);
            Person personParam = personService.getBy(personId);
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && personReq.getPartnerId().equals(personParam.getPartnerId());
            if (!isPartnerAuthorized) {
                boolean isAdmin = userService.requestUserHasRole(request, ADMIN);
                if (isAdmin) {
                    if (personReq.getEnterpriseId() != personParam.getEnterpriseId()) {
                        logger.warn(String.format("Unauthorized request by personId: %d, above other personId: %d", personReq.getId(), personParam.getId()));
                        throw new UnauthorizedResourceException(messageUnauthorized);
                    }
                } else {
                    if (personReq.getId() != personParam.getId()) {
                        logger.warn(String.format("Unauthorized request by personId: %d, above other personId: %d", personReq.getId(), personParam.getId()));
                        throw new UnauthorizedResourceException(messageUnauthorized);
                    }
                }
            }
        }
    }

    /**
     * Validate if the request's user has access on workflowId
     *
     * @param request
     * @param workflowId
     */
    @Override
    public void validateIfBelong2EnterpriseOfWorkflowId(HttpServletRequest request, int workflowId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person personReq = personService.getBy(request);
            Workflow workflowParam = workflowService.getBy(workflowId);
            Enterprise enterprise = workflowParam.getEnterpriseByEnterpriseId();
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && personReq.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                if (personReq.getEnterpriseId() != workflowParam.getEnterpriseId()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, on workflowId: %d", personReq.getId(), workflowId));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to jobId
     */
    @Override
    public void validateIfBelong2EnterpriseOfJobId(HttpServletRequest request, int jobId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Job job = jobService.getBy(jobId);
            Enterprise enterprise = job.getEnterpriseByEnterpriseId();
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                if (person.getEnterpriseId() != job.getEnterpriseId()) {
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access to document's enterpriseId
     */
    @Override
    public void validateIfBelong2EnterpriseOfDocumentIdOperators(HttpServletRequest request, int documentId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            Document document = documentService.getByNoDeleted(documentId);
            Enterprise enterprise = person.getEnterpriseByEnterpriseId();
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isPartnerAuthorized) {
                Optional<Integer> optional = operatorService.getAllBy(document).stream()
                        .map(Operator::getPersonByPersonId)
                        .map(Person::getEnterpriseByEnterpriseId)
                        .map(Enterprise::getId)
                        .distinct()
                        .filter(integer -> integer.equals(enterprise.getId()))
                        .findFirst();
                if (!optional.isPresent()) {
                    logger.warn(String.format("Unauthorized request by personId: %d, enterpriseId: %d, documentId: %d", person.getId(), person.getEnterpriseId(), documentId));
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            }
        }
    }

    /**
     * Validate if the request's user has access partnerId and he's a partner
     */
    @Override
    public void validateIfPersonRequestIsAuthorizedPartner(HttpServletRequest request, int partnerId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            boolean isPartnerAuthorized = userService.requestUserHasRole(request, PARTNER) && person.getPartnerId().equals(partnerId);
            if (!isPartnerAuthorized) {
                throw new UnauthorizedResourceException(messageUnauthorized);
            }
        }
    }

    /**
     * Validate if the request's user has SUPERADMIN role
     */
    public void validateIfPersonOfRequestIsSuperadmin(HttpServletRequest request) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            throw new UnauthorizedResourceException(messageUnauthorized);
        }
    }

    /**
     * Validate if the request's user has SUPERADMIN or PARTNER role
     */
    public void validateIfPersonOfRequestIsSuperadminOrPartner(HttpServletRequest request) {
        boolean isSuperadminOrPartner = userService.requestUserHasRole(request, SUPERADMIN, PARTNER);
        if (!isSuperadminOrPartner) {
            throw new UnauthorizedResourceException(messageUnauthorized);
        }
    }

    /**
     * Validate if the request's user has access to enterpriseId
     */
    @Override
    public void validateIfBelong2PartnerIdOfEnterpriseId(HttpServletRequest request, int enterpriseId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (isSuperadmin) {
            return;
        }
        boolean isPartner = userService.requestUserHasRole(request, PARTNER, ADMIN);
        if (isPartner) {
            Enterprise enterprise = enterpriseService.getBy(enterpriseId);
            Person person = personService.getBy(request);
            boolean isAuthorized = person.getPartnerId().equals(enterprise.getPartnerId());
            if (!isAuthorized) {
                throw new UnauthorizedResourceException(messageUnauthorized);
            }
        } else {
            throw new UnauthorizedResourceException(messageUnauthorized);
        }
    }

    public void validateIfBelong2EnterpriseOfShoppingcardId(HttpServletRequest request, int shoppingcardId) {
        Shoppingcard shoppingcard = shoppingcardService.getBy(shoppingcardId);
        Person person = personService.getBy(request);
        final boolean isAuthorized = person.getPartnerId().equals(shoppingcard.getPartnerId());
        if (!isAuthorized) {
            throw new UnauthorizedResourceException(messageUnauthorized);
        }
    }

    /**
     * Validate if the request's user has access partnerId
     */
    @Override
    public void validateIfBelong2Partner(HttpServletRequest request, int partnerId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            boolean isPartnerAuthorized = person.getPartnerId().equals(partnerId);
            if (!isPartnerAuthorized) {
                throw new UnauthorizedResourceException(messageUnauthorized);
            }
        }
    }

    /**
     * Validate if the request's user has access partnerId
     */
    @Override
    public void validateIfPersonRequestIsAuthorizedAdmin(HttpServletRequest request, int partnerId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            Person person = personService.getBy(request);
            boolean isAuthorized = person.getPartnerId().equals(partnerId);
            if (!isAuthorized) {
                throw new UnauthorizedResourceException(messageUnauthorized);
            }
        }
    }

    /**
     * Validate if the user request's belong to enterprise's partner
     */
    @Override
    public void validateIfPersonRequestBelong2PartnerOfEnterprise(HttpServletRequest request, int enterpriseId) {
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        Person person = personService.getBy(request);
        boolean isAuthorized = person.getPartnerId().equals(enterprise.getPartnerId());
        if (!isAuthorized) {
            throw new UnauthorizedResourceException(messageUnauthorized);
        }
    }


    /**
     * Validate if the request's user has access partnerId
     */
    @Override
    public void validateIfPersonRequestIsAuthorizedAdmin_(HttpServletRequest request, int enterpriseId) {
        boolean isSuperadmin = userService.requestUserHasRole(request, SUPERADMIN);
        if (!isSuperadmin) {
            boolean isPartner = userService.requestUserHasRole(request, PARTNER);
            Person person = personService.getBy(request);
            Enterprise enterprise = enterpriseService.getBy(enterpriseId);
            if (isPartner) {
                if (!person.getEnterpriseByEnterpriseId().getPartnerId().equals(enterprise.getPartnerId())) {
                    throw new UnauthorizedResourceException(messageUnauthorized);
                }
            } else {
                boolean isAdmin = userService.requestUserHasRole(request, ADMIN);
                if (isAdmin) {
                    if (!person.getEnterpriseId().equals(enterpriseId)) {
                        throw new UnauthorizedResourceException(messageUnauthorized);
                    }
                }
            }
        }
    }
}
