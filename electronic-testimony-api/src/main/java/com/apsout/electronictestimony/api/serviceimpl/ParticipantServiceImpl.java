package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutParticipant;
import com.apsout.electronictestimony.api.entity.model.pojo._GroupParticipant;
import com.apsout.electronictestimony.api.exception.AssignerNotFoundException;
import com.apsout.electronictestimony.api.exception.MaxPaticipantException;
import com.apsout.electronictestimony.api.exception.ParticipantNotFoundException;
import com.apsout.electronictestimony.api.exception.PersonNotFoundException;
import com.apsout.electronictestimony.api.repository.ParticipantRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    @Autowired
    private ParticipantRepository repository;
    @Autowired
    private PersonService personService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private AssignerService assignerService;
    @Autowired
    private NotificationService notificationService;

    @Transactional(rollbackFor = Exception.class)
    public List<Participant> save(_GroupParticipant groupParticipant) {
        Integer workflowId = groupParticipant.getWorkflowId();
        Workflow workflow = workflowService.getBy(workflowId);
        List<Participant> participants = groupParticipant.getParticipants();
        if (!participants.isEmpty()) {
            List<Participant> participantsSaved = participants.stream().map(participant -> {
                ParticipantAllocator.build(workflow, participant);
                return this.onlySave(participant);
            }).collect(Collectors.toList());
            this.updateCompletedAndReady2UseFields(workflow);
            return participantsSaved;
        }
        return Collections.emptyList();
    }

    private void updateCompletedAndReady2UseFields(Workflow workflow) {
        final boolean completeParticipants = areCompleteParticipants(workflow);
        if (completeParticipants) {
            WorkflowAllocator.forUpdateCompleted(workflow, States.COMPLETED);
        } else {
            WorkflowAllocator.forUpdateCompleted(workflow, States.INCOMPLETE);
        }
        final boolean isReady2Use = workflowService.ready2Use(workflow);
        if (isReady2Use) {
            WorkflowAllocator.forUpdateReady2Use(workflow, States.READY_TO_USE);
        } else {
            WorkflowAllocator.forUpdateReady2Use(workflow, States.NOT_READY_TO_USE);
        }
        workflowService.onlySave(workflow);
    }

    private boolean areCompleteParticipants(Workflow workflow) {
        List<Participant> participantList = getAllBy(workflow.getId());
        return participantList.size() == workflow.getMaxParticipants();
    }

    @Override
    public List<Participant> getAllBy(int workflowId) {
        List<Participant> participants = repository.findAllBy(workflowId);
        participants.forEach(participant -> {
            prepare(participant);
        });
        return participants;
    }

    @Override
    public List<Participant> getAllBy(Workflow workflow) {
        return getAllBy(workflow.getId());
    }

    @Override
    public Page<Participant> getAllBy(int workflowId, Pageable pageable) {
        Page<Participant> participantPage = repository.findByWorkflowIdAndDeleted(workflowId, States.EXISTENT, pageable);
        participantPage.forEach(participant -> {
            prepare(participant);
        });
        return participantPage;
    }

    private void prepare(Participant participant) {
        Workflow workflow = workflowService.getBy(participant.getWorkflowId());
        Operation operation = operationService.getBy(participant.getOperationId());
        Person person = personService.getBy(participant.getPersonId());
        Optional<Employee> optional = employeeService.findBy(person.getId());
        MoreAboutParticipant moreAboutParticipant = new MoreAboutParticipant();
        if (optional.isPresent()) {
            final Job job = optional.get().getJobByJobId();
            ParticipantAllocator.loadMoreInfo(moreAboutParticipant, workflow, operation, person, job);
        } else {
            ParticipantAllocator.loadMoreInfo(moreAboutParticipant, workflow, operation, person);
        }
        participant.setMoreAboutParticipant(moreAboutParticipant);
    }

    @Override
    public List<Participant> update(_GroupParticipant groupParticipant) {
        Integer workflowId = groupParticipant.getWorkflowId();
        Workflow workflow = workflowService.getBy(workflowId);
        List<Participant> participants = groupParticipant.getParticipants();
        List<Participant> participantsSaved = participants.stream().map(participant -> {
            final Integer participantId = participant.getId();
            if (participantId == null) {
                ParticipantAllocator.build(workflow, participant);
                return this.onlySave(participant);
            } else {
                Participant participantDb = this.getBy(participantId);
                if (States.INACTIVE == participant.getActive()) {
                    ParticipantAllocator.forDelete(participantDb);
                } else {
                    ParticipantAllocator.forUpdate(participantDb, participant);
                }
                return this.onlySave(participantDb);
            }
        }).collect(Collectors.toList());
        this.updateCompletedAndReady2UseFields(workflow);
        return participantsSaved;
    }

    @Override
    public Participant delete(Participant participant) {
        ParticipantAllocator.forDelete(participant);
        repository.save(participant);
        Workflow workflow = workflowService.getBy(participant.getWorkflowId());
        if (!areCompleteParticipants(workflow)) {
            workflow.setCompleted(States.INCOMPLETE);
            workflowService.save(workflow);
        }
        logger.info(String.format("Participant deleted by participantId: %d", participant.getId()));
        return participant;
    }

    @Override
    public Participant getBy(int participantId) {
        Optional<Participant> optional = repository.findById(participantId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ParticipantNotFoundException(String.format("Participant not found by participantId: %d", participantId));
    }

    @Override
    public Optional<Participant> findBy(int workflowId, int personId, int orderParticipant) {
        return repository.findByWorkflowIdAndPersonIdAndOrderParticipant(workflowId, personId, orderParticipant);
    }

    @Override
    public Optional<Participant> findBy(int workflowId, int orderParticipant) {
        return repository.findByWorkflowIdAndOrderParticipant(workflowId, orderParticipant);
    }

    public Participant getBy(int workflowId, int orderParticipant){
        Optional<Participant> optional = findBy(workflowId, orderParticipant);
        if (optional.isPresent()){
            return optional.get();
        }
        throw new ParticipantNotFoundException(String.format("Participant not found by workflowId: %d, orderParticipant: %d", workflowId, orderParticipant));
    }

    private boolean participantOrderPermitted(Participant participant) {
        Workflow workflow = workflowService.getBy(participant.getWorkflowId());
        if (participant.getOrderParticipant() <= workflow.getMaxParticipants()) {
            return true;
        } else {
            throw new MaxPaticipantException(String.format("The number participants accepted for this workflow are: %d", workflow.getMaxParticipants()));
        }
    }

    public Participant onlySave(Participant participant) {
        return repository.save(participant);
    }

    @Override
    public List<Participant> getAllBy(int workflowId, boolean onlyReplaceable) {
        List<Participant> participants;
        if (onlyReplaceable) {
            participants = repository.findAllOnlyReplaceablesBy(workflowId);
        } else {
            participants = repository.findAllBy(workflowId);
        }
        participants.forEach(participant -> prepare(participant));
        return participants;
    }

    @Override
    public List<Participant> getAllReplaceablesBy4Outside(int workflowId) {
        final List<Participant> replaceables = repository.findAllOnlyReplaceablesBy(workflowId);
        ParticipantAllocator.forReturn(replaceables);
        return replaceables;
    }

    public Participant updateSieConfig(Participant participant, byte sieConfigured) {
        ParticipantAllocator.forSieConfig(participant, sieConfigured);
        return this.onlySave(participant);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Operator> update(int documentId, String reasignIdentifiers) {
        String[] reasIdentifiers = reasignIdentifiers.split(Pattern.quote("|"));
        Document document = documentService.getByNoDeleted(documentId);
        List<Operator> operators = operatorService.getAllBy(document);
        List<Assigner> assigners = assignerService.getAllBy(document);
        List<Operator> operatorsCompleted = getOperatorsCompletedOf(assigners);
        List<Operator> operatorsNotCompleted = operators;
        if (!operatorsCompleted.isEmpty()) {
            boolean wasRemovedOperatorsCompleted = operators.removeAll(operatorsCompleted);
            if (!wasRemovedOperatorsCompleted) {
                throw new RuntimeException("Error procesing reassign operators");
            }
        }
        if (operators.size() <= Default.UNIQUE_ELEMENT) {
            return operators;
        }
        List<Operator> newOperators = buildNewOrderOperators(reasIdentifiers, operatorsNotCompleted, operatorsCompleted);
        Optional<Assigner> optionalAssignerNotCompleted = getAssignerNotCompletedOf(assigners);
        if (!optionalAssignerNotCompleted.isPresent()) {
            throw new AssignerNotFoundException("There is not assigners waiting to wear");
        }
        Assigner assigner2Disable = optionalAssignerNotCompleted.get();
        assignerService.invalidate(assigner2Disable);
        // Disable active notification for send
        Operator operator2Disable = assigner2Disable.getOperatorByOperatorId();
        notificationService.invalidateBy(operator2Disable);
        // Assign next operator
        Optional<Operator> optionalNextOperator = operatorService.findNextBy(document);
        if (optionalNextOperator.isPresent()) {
            final Operator nextOperator = optionalNextOperator.get();
            Assigner newAssigner = AssignerAllocator.build2(document, nextOperator, assigner2Disable.getOrderOperation());
            assignerService.save(newAssigner);
            notificationService.invalidateBy(nextOperator);
            Notification notification;
            if (States.SEND_ALERT == nextOperator.getSendAlert()) {
                notification = NotificationAllocator.build4MarkAsNotSent(nextOperator);
            } else {
                notification = NotificationAllocator.build4MarkAsSent(nextOperator);
            }
            notificationService.save(notification);
        }
        return newOperators;
    }

    private List<Operator> getOperatorsCompletedOf(List<Assigner> assigners) {
        List<Assigner> assignersCompleted = assigners.stream()
                .filter(assigner -> States.COMPLETED == assigner.getCompleted())
                .collect(Collectors.toList());
        return assignersCompleted.stream()
                .map(Assigner::getOperatorByOperatorId)
                .collect(Collectors.toList());
    }

    private Optional<Assigner> getAssignerNotCompletedOf(List<Assigner> assigners) {
        return assigners.stream()
                .filter(assigner -> States.INCOMPLETE == assigner.getCompleted())
                .findFirst();
    }

    private List<Operator> buildNewOrderOperators(String[] resIdentifiers, List<Operator> operatorsNotCompleted, List<Operator> operatorsCompleted) {
        final int idPosition = 0;
        final int orderOperationPosition = 1;
        return Arrays.asList(resIdentifiers).stream()
                .filter(pairValues -> !isOperationCompletedBy(pairValues, operatorsCompleted))
                .map(s -> {
                    final String[] splited = s.split(Pattern.quote(","));
                    int personId = Integer.parseInt(splited[idPosition]);
                    int orderOperation = Integer.parseInt(splited[orderOperationPosition]);
                    Operator operator = findOperatorByOf(personId, operatorsNotCompleted);
                    OperatorAllocator.forUpdate2(operator, orderOperation);
                    return operatorService.onlySave(operator);
                }).collect(Collectors.toList());
    }

    private Operator findOperatorByOf(int personId, List<Operator> operators) {
        Optional<Operator> optional = operators.stream().filter(operator -> operator.getPersonId().equals(personId)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PersonNotFoundException(String.format("Person not found by personId: %d", personId));
    }

    private boolean isOperationCompletedBy(String pairValues, List<Operator> operators) {
        final int idPosition = 0;
        final String[] splited = pairValues.split(Pattern.quote(","));
        int personId = Integer.parseInt(splited[idPosition]);
        Person person = personService.getBy(personId);
        return operators.stream().filter(operator -> operator.getPersonId().equals(person.getId())).findFirst().isPresent();
    }
}
