package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutOperator;
import com.apsout.electronictestimony.api.exception.OperationNotFoundException;
import com.apsout.electronictestimony.api.exception.OperatorNotFoundException;
import com.apsout.electronictestimony.api.repository.OperatorRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.OperatorAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorServiceImpl implements OperatorService {
    private static final Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

    @Autowired
    OperatorRepository repository;
    @Autowired
    PersonService personService;
    @Autowired
    OperationService operationService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    AssignerService assignerService;

    @Override
    @Transactional
    public Operator save(Operator operator) {
        OperatorAllocator.ofPostMethod(operator);
        int personId = operator.getPersonId();
        Integer operationId = operator.getOperationId();
        int enterpriseId = operator.getEnterpriseId();
        Optional<Person> optionalPerson = personService.findBy(personId);
        Optional<Operation> optionalOperation = operationService.findBy(operationId);
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        if (optionalPerson.isPresent()) {
            if (optionalOperation.isPresent()) {
                Optional<Operator> optionalOperator = findByPerson(personId);
                if (!optionalOperator.isPresent()) {
                    Optional<Integer> orderOperation = getMaxOrderOperation(enterprise.getId());
                    if (orderOperation.isPresent()) {
                        final int NEXT_ORDER = orderOperation.get() + 1;
                        operator.setOrderOperation(NEXT_ORDER);
                    } else {
                        final int INITIAL_ORDER = 1;
                        operator.setOrderOperation(INITIAL_ORDER);
                    }
                    this.onlySave(operator);
                    logger.info(String.format("Operator registered successfully: %d", operator.getId()));
                } else {
                    logger.warn(String.format("Operator found with operatorId: %d", operator.getId()));
                }
            } else {
                throw new OperationNotFoundException(String.format("Operation not found for operationId: %d ", operator.getOperationId()));
            }
        } else {
            throw new OperationNotFoundException(String.format("Person not found for personId: %d", personId));
        }
        return operator;
    }

    @Override
    @Transactional
    public Operator update(Operator operator) {
        Operator operatorDb = getBy(operator.getId());
        Optional<Operation> optional = operationService.findBy(operator.getOperationId());
        OperatorAllocator.forUpdate(operatorDb, operator);
        if (optional.isPresent()) {
            this.onlySave(operatorDb);
            logger.info(String.format("Operator updated with operatorId: %d", operator.getId()));
            return operatorDb;
        } else {
            throw new OperationNotFoundException(String.format("Operation not found for operationId: %d ", operatorDb.getOperationId()));
        }
    }

    @Override
    public Page<Operator> getAllBy(int enterpriseId, Pageable pageable) {
        Page<Operator> operatorPage = repository.findAllByEnterpriseIdAndDeleted(enterpriseId, States.EXISTENT, pageable);
        operatorPage.forEach(operator -> {
            MoreAboutOperator moreAboutOperator = new MoreAboutOperator();
            loadMoreInfo(moreAboutOperator, operator);
            operator.setMoreAboutOperator(moreAboutOperator);
        });
        return operatorPage;
    }

    private void loadMoreInfo(MoreAboutOperator moreAboutOperator, Operator operator) {
        Person person = personService.getBy(operator.getPersonId());
        Operation operation = operationService.getBy(operator.getOperationId());
        moreAboutOperator.setFullNamePerson(person.getFullname());
        moreAboutOperator.setOperationDescription(operation.getDescription());
    }

    @Override
    public Optional<Integer> getMaxOrderOperation(int enterpriseId) {
        return repository.getMaxOrderOperationBy(enterpriseId);
    }

    @Override
    public Optional<Operator> findByPerson(int personId) {
        return repository.findBy(personId);
    }

    public Optional<Operator> findNextBy(Enterprise enterprise, Document document) {
        Optional<Assigner> optional = assignerService.findFirstByAndOrderOperationDesc(enterprise.getId(), document.getId());
        if (optional.isPresent()) {
            Assigner assigner = optional.get();
            if (assigner.getCompleted() == States.INCOMPLETE)
                return Optional.of(assigner.getOperatorByOperatorId());
            else
                return this.getFirstBy(enterprise, document, assigner.getOperatorByOperatorId().getOrderOperation());
        } else {
            Optional<Operator> optionalOperator = this.getFirstBy(enterprise, document);
            if (optionalOperator.isPresent()) {
                return optionalOperator;
            } else {
                throw new OperatorNotFoundException(String.format("Operators not found to assign first time for enterpriseId: %d", enterprise.getId()));
            }
        }
    }

    @Override
    public Optional<Operator> getFirstBy(Enterprise enterprise, Document document, int orderOperation) {
        return repository.findFirstByEnterpriseIdAndMajorOfOrderOperation(enterprise.getId(), document.getId(), orderOperation);
    }

    @Override
    public Operator getBy(Document document, int orderOperation) {
        final Optional<Operator> optional = repository.findByDocumentIdAndOrderOperation(document.getId(), orderOperation);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new OperatorNotFoundException(String.format("Operator not found for documentId: %d, orderOperation: %d", document.getId(), orderOperation));
    }


    @Override
    public Optional<Operator> getFirstBy(Enterprise enterprise, Document document) {
        return repository.findFirstByEnterpriseIdAsc(enterprise.getId(), document.getId());
    }


    @Override
    public Operator getBy(int operatorId) {
        Optional<Operator> optional = repository.findByIdAndActiveAndDeleted(operatorId, States.ACTIVE, States.EXISTENT);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new OperatorNotFoundException(String.format("Operator not found with operatorId: %d", operatorId));
    }

    @Override
    @Transactional
    public Operator delete(Operator operator) {
        operator.setDeleted(States.DELETED);
        logger.info(String.format("Operator deleted with operatorId: %d", operator.getId()));
        return this.onlySave(operator);
    }

    @Override
    public Integer getCountBy(int enterpriseId) {
        return repository.getCountOperatorsBy(enterpriseId).get();
    }

    @Override
    public Operator onlySave(Operator operator) {
        return repository.save(operator);
    }

    @Override
    public Operator getBy(int personId, int documentId) {
        Optional<Operator> optional = repository.findBy(personId, documentId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new OperatorNotFoundException(String.format("Operator not found for personId: %d, documentId: %d", personId, documentId));
    }

    @Override
    public Optional<Operator> getOptionalBy(int personId, int documentId) {
        return repository.findBy(personId, documentId);
    }

    public Operator getNextConcreteOperatorBy(Enterprise enterprise, Document document) {
        Optional<Operator> optional = findNextBy(enterprise, document);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new OperatorNotFoundException(String.format("Operator not found for enterpriseId: %d, documentId: %d", enterprise.getId(), document.getId()));
        }
    }

    public List<Operator> getAllBy(Document document) {
        return repository.getAllBy(document);
    }

    public Optional<Operator> findNextBy(Document document) {
        Enterprise enterprise = document.getWorkflowByWorkflowId().getEnterpriseByEnterpriseId();
        return findNextBy(enterprise, document);
    }

    public Operator getNextConcreteOperatorBy(Document document) {
        Optional<Operator> optional = findNextBy(document);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new OperatorNotFoundException(String.format("Operator not found for documentId: %d", document.getId()));
        }
    }
}
