package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutPerson;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import com.apsout.electronictestimony.api.entity.security.*;
import com.apsout.electronictestimony.api.exception.MaxUpdatePasswordAttemptException;
import com.apsout.electronictestimony.api.exception.PersonNotFoundException;
import com.apsout.electronictestimony.api.exception.TemporaryNotFoundException;
import com.apsout.electronictestimony.api.exception.WrongActualPasswordException;
import com.apsout.electronictestimony.api.repository.PersonRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.*;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.allocator.security.UserAllocator;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.security.JWTTokenUtil;
import com.apsout.electronictestimony.api.util.statics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonRepository repository;
    @Autowired
    private JobService jobService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private AssignerService assignerService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserroleService userroleService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private PersonworkflowService personworkflowService;
    @Autowired
    private JWTTokenUtil jwtTokenUtil;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserauthorityService userauthorityService;
    @Autowired
    private ParticipanttypeService participanttypeService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileupdateattemptService profileupdateattemptService;
    @Autowired
    private PersonrubricService personrubricService;
    @Autowired
    private TemporarysessionService temporarysessionService;

    @Override
    public Page<Person> getAllBy2(int partnerId, int enterpriseId, int roleId, int participantType, String term2Search, Pageable pageable, HttpServletRequest request) {
        Page<Person> personPage;
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        List<Integer> enterpriseIds = enterpriseService.getEnterpriseIdsBy(enterprise);
        Participanttype participanttype = participanttypeService.getBy(participantType);
        if (ParticipantType.INVITED != participanttype.getId()) {
            Role role = roleService.getExistentBy(roleId);
            List<Integer> roleIds = getRoleIdsBy(role);
            if (term2Search.isEmpty()) {
                personPage = repository.findNotInvited(partnerId, enterpriseIds, roleIds, participantType, pageable);
            } else {
                personPage = repository.findNotInvited(partnerId, enterpriseIds, roleIds, participantType, term2Search, pageable);
            }
        } else {
            if (term2Search.isEmpty()) {
                personPage = repository.findAllInvited(partnerId, enterpriseIds, participantType, pageable);
            } else {
                personPage = repository.findAllInvited(partnerId, enterpriseIds, participantType, term2Search, pageable);
            }
        }
        personPage.forEach(person -> {
            loadMoreInfo(person);
        });
        return personPage;
    }

    private List<Integer> getRoleIdsBy(Role role) {
        if (Roles.ALL == role.getId()) {
            return roleService.findAll()
                    .stream()
                    .filter(role1 -> Roles.ALL != role1.getId())
                    .map(role1 -> role1.getId())
                    .collect(Collectors.toList());
        }
        return Arrays.asList(role.getId());
    }

    private void loadMoreInfo(Person person) {
        Employee employee = employeeService.getBy(person.getId());
        Job job = jobService.getBy(employee.getJobId());
        Scope scope = scopeService.findBy(person.getId());
        MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
        List<Role> roles = roleService.findAllBy(person);
        Enterprise enterprise = person.getEnterpriseByEnterpriseId();
        Enterprise enterpriseView = person.getEnterpriseByEnterpriseIdView();
        Optional<Personrubric> optional = personrubricService.findBy(person);
        if (optional.isPresent()) {
            Personrubric personrubric = optional.get();
            PersonAllocator.loadMoreInfo(enterprise, enterpriseView, scope, job, roles, personrubric, moreAboutPerson);
        } else {
            PersonAllocator.loadMoreInfo(enterprise, enterpriseView, scope, job, roles, moreAboutPerson);
        }
        person.setMoreAboutPerson(moreAboutPerson);
    }

    @Override
    public Person getBy(int personId) {
        Optional<Person> optional = this.findBy(personId);
        if (optional.isPresent()) {
            final Person person = optional.get();
            loadMoreInfo(person);
            return person;
        }
        throw new PersonNotFoundException(String.format("Person not found for personId: %d", personId));
    }

    @Override
    public Optional<Person> findBy(int personId) {
        return repository.findById(personId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Person save(Person person, HashMap<String, Boolean> foundMap) {
        Optional<Person> optionalPerson = this.findBy(person.getEnterpriseId(), person.getEnterpriseIdView(), person.getScope(), person.getType(), person.getDocumentType(), person.getDocumentNumber(), person.getJobId());
        if (!optionalPerson.isPresent()) {
            Enterprise enterpriseView = enterpriseService.getBy(person.getEnterpriseId());
            Person newPerson = PersonAllocator.build(person, enterpriseView);
            repository.save(newPerson);
            Employee employee = EmployeeAllocator.build(person);
            employeeService.save(employee);
            Scope scope = ScopeAllocator.build(person);
            scopeService.save(scope);
            if (ParticipantType.INVITED != scope.getParticipantType()) {
                List<Integer> intRoles = getRoleIds(person);
                String usernameBase = UserAllocator.buildUserName(person);
                Optional<User> optionalUser = userService.find4Create(usernameBase);
                User newUser = UserAllocator.build(person, usernameBase, optionalUser);
                userService.save(newUser);
                intRoles.stream().forEach(integer -> {
                    Role role = roleService.getBy(integer);
                    Userrole userrole = UserroleAllocator.build(newUser, role);
                    userroleService.save(userrole);
                });
            }
            this.updateRubricOf(person);
            logger.info(String.format("Person save with personId: %d ", person.getId()));
            foundMap.put("found", false);
            return newPerson;
        }
        logger.info(String.format("Person found by personId: %d", person.getId()));
        foundMap.put("found", true);
        return optionalPerson.get();
    }

    private boolean hasRubric(Person person) {
        return person.getRubricBase64Data() != null && !person.getRubricBase64Data().isEmpty();
    }

    private List<Integer> getRoleIds(Person person) {
        String roles = person.getRoles();
        return StringUtil.split2Integers(roles, "|");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Person update(Person person) {
        Person personDb = this.getBy(person.getId());
        Enterprise enterprise = enterpriseService.getBy(person.getEnterpriseId());
        PersonAllocator.forUpdate(personDb, person, enterprise);
        repository.save(personDb);
        Employee employeeDb = employeeService.getBy(person.getId());
        EmployeeAllocator.forUpdate(employeeDb, person);
        employeeService.save(employeeDb);
        Scope scopeDb = scopeService.getBy(person.getId());
        ScopeAllocator.forUpdate(scopeDb, person);
        scopeService.save(scopeDb);
        if (!person.getRoles().isEmpty()) {
            Role role = roleService.findBy(person.getRoles());
            User user = userService.getBy(person.getId());
            Userrole userrole = userroleService.getBy(user);
            UserroleAllocator.forUpdate(userrole, role);
            userroleService.save(userrole);
        }
        this.updateRubricOf(person);
        logger.info(String.format("Person updated with personId: %d", person.getId()));
        return personDb;
    }

    @Override
    public Optional<Person> findBy(int enterpriseId, int enterpriseIdView, int participantType, int type, String documentType, String documentNumber, int jobId) {
        final Optional<Person> optional = repository.findByTypeAndEnterpriseIdAndDocTypeAndDocNumber(enterpriseId, enterpriseIdView, participantType, type, documentType, documentNumber, jobId);
        if (optional.isPresent()) {
            loadMoreInfo(optional.get());
        }
        return optional;
    }

    @Override
    @Transactional
    public Person delete(Person person) {
        person.setDeleted(States.DELETED);
        logger.info(String.format("Person deleted by personId: %d", person.getId()));
        return repository.save(person);
    }

    @Override
    public Optional<Person> findByDocumentId(int documentId) {
        return repository.findBy(documentId);
    }

    @Override
    public Person getByDocumentId(int documentId) {
        Optional<Person> optional = findByDocumentId(documentId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PersonNotFoundException(String.format("Person doesn't exist for documentId: %d", documentId));
    }

    @Override
    public List<Person> findAllBy(int workflowId) {
        List<Participant> participants = participantService.getAllBy(workflowId);
        return participants.stream().map(participant -> {
            Person person = PersonAllocator.clone(participant.getPersonByPersonId());
            Operation operation = participant.getOperationByOperationId();
            MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
            PersonAllocator.loadMoreInfo(moreAboutPerson, participant, operation);
            person.setMoreAboutPerson(moreAboutPerson);
            return person;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Person> findBy(int enterpriseId, int participantType, String fullName) {
        List<Person> personList;
        if (ParticipantType.INVITED_USER == participantType) {
            personList = repository.findByParticipantTypeAndFullNameAndDistinctAssistent(fullName);
        } else {
            personList = repository.findByEnterpriseIdAndParticipantTypeAndFullNameAndDistinctAssistent(enterpriseId, participantType, fullName);
        }
        personList.forEach(person -> {
            Enterprise enterprise = enterpriseService.getBy(person.getEnterpriseIdView());
            Employee employee = employeeService.getBy(person.getId());
            Job job = jobService.getBy(employee.getJobId());
            MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
            PersonAllocator.loadMoreInfo(enterprise, job, moreAboutPerson);
            person.setMoreAboutPerson(moreAboutPerson);
        });
        return personList;
    }

    @Override
    public Person searchBy(int type, int enterpriseId, int documentType, String documentNumber) {
        // TODO aquí corregir
        Optional<Person> optional = findBy(type, enterpriseId, documentType, documentNumber, 1);
        if (optional.isPresent()) {
            Person person = optional.get();
            Enterprise enterprise = enterpriseService.getBy(person.getEnterpriseIdView());
            Employee employee = employeeService.getBy(person.getId());
            Job job = jobService.getBy(employee.getJobId());
            MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
            PersonAllocator.loadMoreInfo(enterprise, job, moreAboutPerson);
            person.setMoreAboutPerson(moreAboutPerson);
            return person;
        }
        throw new PersonNotFoundException(String.format("Person doesn't exist with documentNumber: %s", documentNumber));
    }

    private Optional<Person> findBy(int type, int enterpriseId, int documentType, String documentNumber, int jobId) {
        if (PersonType.NATURAL == type) {
            return repository.findByTypeAndDocumentTypeAndDocumentNumberAndDistinctAssistant(type, documentType, documentNumber);
        }
        return repository.findByTypeAndEnterpriseIdAndDocumentTypeAndDocumentNumberAndDistinctAssistant(type, enterpriseId, documentType, documentNumber);
    }

    @Override
    public Person findBy(int personId, String hashIdentifier) {
        Assigner assigner = assignerService.findBy(personId, hashIdentifier);
        Person person = assigner.getOperatorByOperatorId().getPersonByPersonId();
        Scope scope = scopeService.getBy(person.getId());
        Operator operator = assigner.getOperatorByOperatorId();
        MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
        PersonAllocator.loadMoreInfo(moreAboutPerson, scope, person, operator);
        person.setMoreAboutPerson(moreAboutPerson);
        return person;
    }

    public Person saveWorkflows(Person person) {
        final int idPosition = 0;
        final int activePosition = 1;
        Person persondb = this.getBy(person.getId());
        String workflows = person.getWorkflows();
        Arrays.stream(workflows.split(Pattern.quote("|"))).forEach(s -> {
            Integer[] data = StringUtil.split2Integers2(s, ",");
            int workflowId = data[idPosition];
            byte active = (byte) data[activePosition].intValue();
            Workflow workflow = workflowService.getBy(workflowId);
            Personworkflow personworkflow = PersonworkflowAllocator.build(persondb, workflow);
            Optional<Personworkflow> optional = personworkflowService.findBy(personworkflow);
            if (optional.isPresent()) {
                Personworkflow personworkflowdb = optional.get();
                if (personworkflowdb.getActive() != active) {
                    personworkflowdb.setActive(active);
                    personworkflowService.save(personworkflowdb);
                }
            } else {
                if (States.ACTIVE == active) {
                    personworkflowService.save(personworkflow);
                }
            }
        });
        return persondb;
    }

    public Person getBy(HttpServletRequest request) {
        String username = jwtTokenUtil.getSubjectOf(request);
        User user = userService.getBy(username);
        return user.getPersonByPersonId();
    }

    public List<Person> findAllPeopleBy(int documentId) {
        final List<Person> people = repository.findAllByDocumentId(documentId);
        people.stream().forEach(person -> {
            MoreAboutPerson moreAboutPerson = new MoreAboutPerson();
            Operator operator = operatorService.getBy(person.getId(), documentId);
            Operation operation = operator.getOperationByOperationId();
            Optional<Assigner> optional = assignerService.findBy(person.getId(), documentId);
            PersonAllocator.loadMoreInfo(moreAboutPerson, person, operation, optional);
            person.setMoreAboutPerson(moreAboutPerson);
        });
        return people;
    }

    @Override
    public Person onlySave(Person person) {
        return repository.save(person);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Person resetUserPassword(int personId) {
        Person person = this.getBy(personId);
        User user = userService.getBy(person);
        UserAllocator.forResetPassword(user);
        userService.save(user);
        return person;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Person updateAuthorities(int personId, _AuthoritiesList authoritiesList) {
        Person person = this.getBy(personId);
        User user = userService.getBy(person);
        List<Authority> authorities = authoritiesList.getAuthorities();
        authorities.stream().forEach(authority -> {
            Authority authoritydb = authorityService.getBy(authority.getId());
            Optional<Userauthority> optional = userauthorityService.findBy(user, authority);
            if (optional.isPresent()) {
                if (States.INACTIVE == authority.getActive()) {
                    Userauthority userauthority = optional.get();
                    UserauthorityAllocator.forDelete(userauthority);
                    userauthorityService.save(userauthority);
                }
            } else {
                if (States.ACTIVE == authority.getActive()) {
                    Userauthority roleauthority = UserauthorityAllocator.build(user, authority);
                    userauthorityService.save(roleauthority);
                }
            }
        });
        return person;
    }

    public List<Person> findAllBy(List<Integer> personIds) {
        return repository.findAllByIdInAndActiveAndDeleted(personIds, States.ACTIVE, States.EXISTENT);
    }

    public Person updateProfile(Person person, int personId) {
        if (personId != person.getId()) {
            logger.error(String.format("Attempt to send credentials by someone else, by personId: %d of person.getId(): %d", personId, person.getId()));
            throw new RuntimeException(String.format("Inconsistencia de datos enviados por personId: %d para person.getId(): %d", personId, person.getId()));
        }
        String oldPassword = person.getOldPassword();
        User user = userService.getBy(person);
        String encodedPassword = user.getPassword();
        boolean matchPassword = bCryptPasswordEncoder.matches(oldPassword, encodedPassword);
        if (!matchPassword) {
            logger.warn(String.format("Actual password sent doesn't match for personId: %d", personId));
            this.saveWrongUpdateAttempt(personId);
            List<Profileupdateattempt> profileupdateattempts = profileupdateattemptService.findLastTwoBy(personId);
            if (UpdateAttempt.MAX_ATTEMPTS == profileupdateattempts.size()) {
                final int LAST_INDEX = profileupdateattempts.size() - 1;
                Profileupdateattempt first = profileupdateattempts.get(UpdateAttempt.FIRST_INDEX);
                Profileupdateattempt second = profileupdateattempts.get(LAST_INDEX);
                Duration duration = Duration.ofMillis(UpdateAttempt.INTERVAL_VALIDITY);
                final Timestamp before = second.getLastAttemptAt();
                final Timestamp after = first.getLastAttemptAt();
                if (DateUtil.hasIntervalIn(before, after, duration)) {
                    throw new MaxUpdatePasswordAttemptException("La contraseña actual no coincide con la registrada, por favor verifíquelo e intente nuevamente.");
                }
            }
            throw new WrongActualPasswordException("La contraseña actual no coincide con la registrada, por favor verifíquelo e intente nuevamente.");
        }
        Person personDb = getBy(personId);
        PersonAllocator.forUpdateProfile(personDb, person);
        final String newPassword = person.getNewPassword();
        if (newPassword != null) {
            userService.updatePassword(user, newPassword);
        }
        this.updateRubricOf(person);
        return onlySave(personDb);
    }

    private void updateRubricOf(Person person) {
        if (hasRubric(person)) {
            Optional<Personrubric> optional = personrubricService.findBy(person);
            Personrubric personrubric;
            if (optional.isPresent()) {
                personrubric = optional.get();
                PersonrubricAllocator.forUpdate(person, personrubric);
            } else {
                personrubric = PersonrubricAllocator.build(person);
            }
            personrubricService.save(personrubric);
            logger.info(String.format("Rubric person updated for personId: %d", person.getId()));
        }
    }

    private void saveWrongUpdateAttempt(int personId) {
        Profileupdateattempt profileupdateattempt = ProfileupdateattemptAllocator.build(personId);
        profileupdateattemptService.save(profileupdateattempt);
    }

    public Person updateRubric(int personId, String uuid, String rubricFilename, String rubricFileBase64) {
        Person person = this.getBy(personId);
        Optional<Temporarysession> optional1 = temporarysessionService.findBy(personId, uuid);
        if (optional1.isPresent()) {
            if (!rubricFilename.isEmpty() && !rubricFileBase64.isEmpty()) {
                Optional<Personrubric> optional = personrubricService.findBy(person);
                Personrubric personrubric;
                if (optional.isPresent()) {
                    personrubric = optional.get();
                    PersonrubricAllocator.forUpdate(personrubric, rubricFilename, rubricFileBase64);
                } else {
                    personrubric = PersonrubricAllocator.build(person, rubricFilename, rubricFileBase64);
                }
                personrubricService.save(personrubric);
            }
            return person;
        } else {
            throw new TemporaryNotFoundException(String.format("Temporary not found by personId: %d, UUID: %s", personId, uuid));
        }
    }
}
