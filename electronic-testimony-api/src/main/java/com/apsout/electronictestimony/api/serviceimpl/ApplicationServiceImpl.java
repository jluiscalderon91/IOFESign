package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.pojo.*;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.AuthorityService;
import com.apsout.electronictestimony.api.service.security.RoleService;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.SiecertificateAllocator;
import com.apsout.electronictestimony.api.util.allocator.SiecredentialAllocator;
import com.apsout.electronictestimony.api.util.allocator.WorkflowAllocator;
import com.apsout.electronictestimony.api.util.allocator._ProfileAllocator;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.Constant;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.apsout.electronictestimony.api.util.statics.Roles.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private OperationService operationService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private JobService jobService;
    @Autowired
    private FontcolorService fontcolorService;
    @Autowired
    private FontsizeService fontsizeService;
    @Autowired
    private FonttypeService fonttypeService;
    @Autowired
    private PagestampService pagestampService;
    @Autowired
    private ContentpositionService contentpositionService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DeliverysettingService deliverysettingService;
    @Autowired
    private SiecredentialService siecredentialService;
    @Autowired
    private SiecertificateService siecertificateService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private WorkflowtypeService workflowtypeService;
    @Autowired
    private ParticipanttypeService participanttypeService;
    @Autowired
    private PersontypeService persontypeService;
    @Autowired
    private StateService stateService;
    @Autowired
    private IdentificationDocumentService identificationDocumentService;
    @Autowired
    private ServiceweightService serviceweightService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private PersonService personService;
    @Autowired
    private ShoppingcardService shoppingcardService;
    @Autowired
    private ServiceService serviceService;

    private HashMap<String, Object> hashMap = new HashMap<>();

    @Override
    public String add(int personId, String identifiers) {
        HashMap<String, String> innerMap = new HashMap<>();
        innerMap.put("identifiers", identifiers);
        innerMap.put("personId", String.valueOf(personId));
        String uuid = getUUID();
        hashMap.put(uuid, innerMap);
        logger.info(String.format("UUID generated for personId: %d, UUID: %s, identifiers: %s", personId, uuid, identifiers));
        return uuid;
    }

    @Override
    public HashMap<String, String> remove(String uuid) {
        return (HashMap) hashMap.remove(uuid);
    }

    @Override
    public HashMap<String, String> get(String uuid) {
        logger.info(String.format("Finding UUID: %s", uuid));
        return (HashMap) hashMap.get(uuid);
    }

    @Override
    public List<Integer> getIdentifiers(String uuid) {
        final HashMap<String, String> hashMap = this.get(uuid);
        if (hashMap == null) {
            return Collections.emptyList();
        }
        String joinedIds = hashMap.get("identifiers");
        return StringUtil.split2Integers(joinedIds, ",");
    }

    @Override
    public Integer getPersonIdBy(String uuid) {
        return Integer.parseInt(this.get(uuid).get("personId"));
    }

    @Override
    public _Embedded getStaticData(HttpServletRequest request) {
        _Embedded embedded = new _Embedded();
        String today = DateUtil.today("yyyy-MM-dd");
        User user = userService.getBy(request);
        Person person = user.getPersonByPersonId();
        Role role = roleService.getUpperRoleBy(user);
        Enterprise enterpriseView = user.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
        _Profile profile = loadProfileInfoBy(user);
        _Setting setting = loadSetting(user);
        List<Operation> operations = operationService.findAll();
        List<Workflow> workflows;
        List<Job> jobs;
        List<Module> modules = null;
        List<Enterprise> enterprises = null;
        if (hasRole(user, SUPERADMIN)) {
            enterprises = enterpriseService.findAll();
            workflows = workflowService.findAll();
            jobs = jobService.getAll();
            modules = moduleService.findAll();
        } else if (hasRole(user, PARTNER)) {
            enterprises = enterpriseService.findAllBy(person.getPartnerId());
            List<Integer> enterpriseIds = enterprises.stream().map(Enterprise::getId).collect(Collectors.toList());
            workflows = workflowService.findAllBy(enterpriseIds);
            jobs = jobService.findAllBy(enterpriseIds);
            modules = moduleService.findAll();
            // las demás listas
        } else {
            workflows = workflowService.findAllBy(enterpriseView.getId());
            jobs = jobService.findBy(enterpriseView.getId());
        }
        if (hasRole(user, ADMIN, PARTNER, SUPERADMIN)) {
            List<Workflow> workflows1 = workflowService.findAllBy(Default.ENTERPRISE_ID_VIEW);
            List<Workflow> joinedWorkflows = Stream.concat(workflows1.stream(), workflows.stream()).collect(Collectors.toList());
            List<Fontcolor> fontcolors = Localstorage.fontcolors;
            List<Fontsize> fontsizes = Localstorage.fontsizes;
            List<Fonttype> fonttypes = Localstorage.fonttypes;
            List<Pagestamp> pagesstamps = Localstorage.pagesstamps;
            List<Contentposition> contentpositions = Localstorage.contentpositions;
            List<Workflowtype> workflowtypes = Localstorage.workflowtypes;
            List<Authority> authorities = authorityService.findAllBy(role);
            List<Partner> partners = partnerService.findAll();
            List<com.apsout.electronictestimony.api.entity.Service> services = Localstorage.services;
            embedded.setFontcolors(fontcolors);
            embedded.setFontsizes(fontsizes);
            embedded.setFonttypes(fonttypes);
            embedded.setPagestamps(pagesstamps);
            embedded.setContentpositions(contentpositions);
            embedded.setWorkflowtypes(workflowtypes);
            embedded.setAuthorities(authorities);
            embedded.setWorkflows(joinedWorkflows);
            embedded.setPartners(partners);
            embedded.setServices(services);
        }
        List<Identificationdocument> identificationdocuments = Localstorage.identificationdocuments;
        List<Role> roles = Localstorage.roles
                .stream()
                .filter(role1 -> role1.getId() > role.getId())
                .sorted(Comparator.comparing(Role::getOrderRole))
                .collect(Collectors.toList());
        List<Participanttype> participanttypes = Localstorage.participanttypes;
        List<Persontype> persontypes = Localstorage.persontypes;
        List<State> states = Localstorage.states
                .stream()
                .filter(state -> States.VISIBLE == state.getVisible())
                .collect(Collectors.toList());
        this.setUpInfoBalance(embedded, enterpriseView);
        List<Shoppingcard> shoppingcards = shoppingcardService.findByPartnerId(enterpriseView.getPartnerId());
        embedded.setToday(today);
        embedded.setEnterprises(enterprises);
        embedded.setOperations(operations);
        embedded.setSetting(setting);
        embedded.setProfile(profile);
        embedded.setIdentificationDocuments(identificationdocuments);
        embedded.setRoles(roles);
        embedded.setParticipanttypes(participanttypes);
        embedded.setPersontypes(persontypes);
        embedded.setJobs(jobs);
        embedded.setModules(modules);
        embedded.setStates(states);
        _Others others = loadOtherParams();
        embedded.setShoppingcards(shoppingcards);
        embedded.setOthers(others);
        return embedded;
    }

    private _Others loadOtherParams() {
        _Others others = new _Others();
        _Document document = new _Document();
        document.setAddAlertMessage("<strong>¡Atención!</strong> Si usted carga un documento que tiene firmas digitales a un flujo que agrega elementos gráficos, la integridad de dicho documento será alterada.");
        others.setDocument(document);
        return others;
    }

    private _Profile loadProfileInfoBy(User user) {
        Person person = user.getPersonByPersonId();
        Enterprise enterprise = person.getEnterpriseByEnterpriseId();
        Enterprise enterpriseView = person.getEnterpriseByEnterpriseIdView();
        Job job = employeeService.getBy(person.getId()).getJobByJobId();
        Scope scope = scopeService.findBy(person.getId());
        List<Role> userRoles = roleService.findAllBy(user);
        List<Authority> userAuthorities;
        if (hasRole(user, SUPERADMIN)) {
            userAuthorities = authorityService.findAll();
        } else {
            userAuthorities = authorityService.findAllBy(user);
        }
        List<Workflow> workflows0 = workflowService.findAllBy(Default.ENTERPRISE_ID_VIEW);
        List<Workflow> workflows1 = workflowService.findAllWhereIsParticipantOrAssigned(person);
        List<Workflow> joinedWorkflows = Stream.concat(workflows0.stream(), workflows1.stream()).collect(Collectors.toList());
        Partner partner = person.getPartnerByPartnerId();
        return _ProfileAllocator.build(partner, person, job, enterprise, enterpriseView, scope, userRoles, userAuthorities, joinedWorkflows);
    }

    private _Setting loadSetting(User user) {
        Person person = user.getPersonByPersonId();
        Enterprise enterprise = person.getEnterpriseByEnterpriseIdView();
        Optional<Deliverysetting> optional = deliverysettingService.findByEnterprise(enterprise);
        _Setting setting = new _Setting();
        if (optional.isPresent()) {
            setting.setHasDeliverOption(States.HAS_DELIVER_OPTION);
        }
        return setting;
    }

    @Override
    public _Embedded getStaticData4Outside(HttpServletRequest request) {
        _Embedded embedded = new _Embedded();
        String moment = DateUtil.moment("yyyy-MM-dd HH:mm:ss");
        User user = userService.getBy(request);
        Enterprise enterprise = user.getPersonByPersonId().getEnterpriseByEnterpriseId();
        _Profile profile = loadProfileInfoBy4Outside(user);
        List<Fontcolor> fontcolors = fontcolorService.findAll();
        List<Fontsize> fontsizes = fontsizeService.findAll();
        List<Fonttype> fonttypes = fonttypeService.findAll();
        List<Pagestamp> pagesstamps = pagestampService.findAll();
        List<Contentposition> contentpositions = contentpositionService.findAll();
        Optional<Siecredential> optional = siecredentialService.findLastActiveBy(enterprise);
        if (optional.isPresent()) {
            final Siecredential siecredentialDb = optional.get();
            final Siecredential siecredential = SiecredentialAllocator.forReturn(siecredentialDb);
            embedded.setSiecredential(siecredential);
            Siecertificate siecertificateDb = siecertificateService.getBy(siecredentialDb);
            Siecertificate siecertificate = SiecertificateAllocator.forReturn(siecertificateDb);
            embedded.setSiecertificate(siecertificate);
        }
        embedded.setFontcolors(fontcolors);
        embedded.setFontsizes(fontsizes);
        embedded.setFonttypes(fonttypes);
        embedded.setPagestamps(pagesstamps);
        embedded.setContentpositions(contentpositions);
        embedded.setMoment(moment);
        embedded.setProfile(profile);
        return embedded;
    }

    private _Profile loadProfileInfoBy4Outside(User user) {
        Person person = user.getPersonByPersonId();
        Enterprise enterprise = person.getEnterpriseByEnterpriseId();
        Enterprise enterpriseView = person.getEnterpriseByEnterpriseIdView();
        Job job = employeeService.getBy(person.getId()).getJobByJobId();
        Scope scope = scopeService.findBy(person.getId());
        List<Role> userRoles = roleService.findAllBy(user);
        List<Authority> userAuthorities = authorityService.findAllBy(user);
        List<Workflow> workflows = workflowService.findAllWhereIsParticipantOrAssigned(person);
        WorkflowAllocator.forReturn2Station(workflows);
        return _ProfileAllocator.build(person, job, enterprise, enterpriseView, scope, userRoles, userAuthorities, workflows);
    }

    private boolean hasRole(User user, int... roleIds) {
        Role role = roleService.getUpperRoleBy(user);
        return Arrays.stream(roleIds).filter(roleId -> roleId == role.getId()).findFirst().isPresent();
    }

    public String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public void reloadStaticData() {
        logger.info("Starting load static data");
        Localstorage.fontcolors = fontcolorService.findInitialAll();
        Localstorage.fontsizes = fontsizeService.findInitialAll();
        Localstorage.fonttypes = fonttypeService.findInitialAll();
        Localstorage.pagesstamps = pagestampService.findInitialAll();
        Localstorage.contentpositions = contentpositionService.findInitialAll();
        Localstorage.workflowtypes = workflowtypeService.findInitialAll();
        Localstorage.roles = roleService.findInitialAll();
        Localstorage.participanttypes = participanttypeService.findInitialAll();
        Localstorage.persontypes = persontypeService.findInitialAll();
        Localstorage.states = stateService.findInitialAll();
        Localstorage.authorities = authorityService.findInitialAll();
        Localstorage.identificationdocuments = identificationDocumentService.findInitialAll();
        Localstorage.operations = operationService.findInitialAll();
        Localstorage.modules = moduleService.findInitialAll();
        Localstorage.serviceweights = serviceweightService.findInitialAll();
        Localstorage.services = serviceService.findInitialAll();
        logger.info("Finished load static data");
    }

    public _Embedded getActualBalance(HttpServletRequest request) {
        _Embedded embedded = new _Embedded();
        Person person = personService.getBy(request);
        Enterprise enterprise = person.getEnterpriseByEnterpriseIdView();
        this.setUpInfoBalance(embedded, enterprise);
        return embedded;
    }

    private void setUpInfoBalance(_Embedded embedded, Enterprise enterprise) {
        Optional<Headbalanceallocation> optionalHeadbalanceallocation = headbalanceallocationService.findFirstAvailableBy(enterprise);
        if (optionalHeadbalanceallocation.isPresent()) {
            Headbalanceallocation headbalanceallocation = optionalHeadbalanceallocation.get();
            embedded.setHasConfigBalance(Constant.HAS_CONFIG_BALANCE);
            embedded.setBalance(headbalanceallocation.getBalance().floatValue());
        } else {
            embedded.setHasConfigBalance(Constant.HAS_NOT_CONFIG_BALANCE);
        }
    }
}
