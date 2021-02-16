package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.config.ResourceProperties;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;
import com.apsout.electronictestimony.api.entity.model.pojo._Body;
import com.apsout.electronictestimony.api.entity.model.pojo._Comment;
import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;
import com.apsout.electronictestimony.api.entity.model.pojo._Operator;
import com.apsout.electronictestimony.api.exception.*;
import com.apsout.electronictestimony.api.repository.DocumentRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.crypto.Hash;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.enums.Pages2Stamp;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.*;
import com.inversionesrc.dsignature.SignatureResult;
import com.inversionesrc.dsignature.pdf.PdfSigner;
import org.bouncycastle.tsp.TimeStampToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.apsout.electronictestimony.api.util.statics.Roles.*;

@Service
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    @Autowired
    private DocumentRepository repository;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PersonService personService;
    @Autowired
    private StateService stateService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private AssignerService assignerService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DoneService doneService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ParticipantService participantService;
    // TODO Improvement repetitive code of fileStoragePath
    private final Path fileStoragePath;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private HistoricalHashService historicalHashService;
    @Autowired
    private DocumentresourceService documentresourceService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private SieemailService sieemailService;
    @Autowired
    private SieemailoperatorService sieemailoperatorService;
    @Autowired
    private RelatedpersonService relatedpersonService;
    @Autowired
    private StampdatetimeService stampdatetimeService;
    @Autowired
    private StamperService stamperService;
    @Autowired
    private PagestampService pagestampService;
    @Autowired
    private ObservationcancelService observationcancelService;
    @Autowired
    private CancelnotificationService cancelnotificationService;
    @Autowired
    private DocumenttraceabilityService traceability;
    @Autowired
    private NumbersignatureService numbersignatureService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private TemporarysessionService temporarysessionService;
    @Autowired
    private StamprubricService stamprubricService;
    @Autowired
    private DocumentmodificationService documentmodificationService;
    @Autowired
    private HistoricaldocumentmodificationService historicaldocumentmodificationService;

    @Autowired
    public DocumentServiceImpl(ResourceProperties resourceProperties) {
        fileStoragePath = Paths.get(resourceProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public Page<Document> getAll(Pageable pageable) {
        logger.info(String.format("Looking for documents by page: ", pageable));
        return repository.findAllByDeleted(States.EXISTENT, pageable);
    }

    @Override
    public Optional<Document> findBy(int documentId) {
        return repository.findByIdAndActiveAndDeleted(documentId, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public Document getBy(int documentId) {
        final Optional<Document> optional = this.findBy(documentId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new DocumentNotFoundException(String.format("Document not found for documentId: %d", documentId));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Document save(Person person, int workflowId, int type, String subject, String replacements, MultipartFile[] multipartFiles) {
        int stateId = 1;
        String description = "";
        Workflow workflow = workflowService.getBy(workflowId);
        boolean isStatically = workflow.getDynamic() == States.STATICALLY;
        State state = stateService.getBy(stateId);
        List<Participant> participants = null;
        int numberOperators;
        String[] stringReplacements = null;
        if (isStatically) {
            participants = participantService.getAllBy(workflowId);
            numberOperators = participants.size();
            int numberReplaceables = getSizeReplaceable(participants);
            int numberReplacements = getSizeReplacements(replacements);
            verifySizeReplaceableAndReplacements(numberReplaceables, numberReplacements);
        } else {
            stringReplacements = replacements.split(Pattern.quote("|"));
            numberOperators = stringReplacements.length;
        }
        String hashIdentifier = buildHashIdentifier(subject);
        Document document = DocumentAllocator.build(person, workflow, state, type, subject, hashIdentifier, description, numberOperators);
        Enterprise enterprise = person.getEnterpriseByEnterpriseIdView();
        this.save(document);
        Historicalhash historicalhash = HistoricalhashAllocator.build(document);
        historicalHashService.save(historicalhash);
        if (isStatically) {
            participants.stream().forEach(participant -> {
                Person personWf = participant.getPersonByPersonId();
                int orderParticipant = participant.getOrderParticipant();
                if (personWf.getReplaceable() == States.REPLACEABLE) {
                    if (!replacements.isEmpty()) {
                        int newPersonId = getPersonIdOf(replacements, orderParticipant);
                        personWf = personService.getBy(newPersonId);
                    }
                }
                Operation operationWf = participant.getOperationByOperationId();
                Enterprise enterpriseWf = person.getEnterpriseByEnterpriseIdView();
                Operator operatorWf = OperatorAllocator.build(enterpriseWf, personWf, participant, operationWf, document);
                operatorService.onlySave(operatorWf);
                //Save on sieemailoperator
                this.assignSieemail2Operator(participant, operatorWf);
            });
        } else {
            final int orderPosition = 0;
            final int personIdPosition = 1;
            final int tsaPosition = 2;
            Arrays.asList(stringReplacements).stream().forEach(replacement -> {
                final String[] splited = replacement.split(Pattern.quote(","));
                Integer orderOperation = Integer.parseInt(splited[orderPosition]);
                Integer personIdx = Integer.parseInt(splited[personIdPosition]);
                Byte addTsa = Byte.parseByte(splited[tsaPosition]);

                Operation operationWf = operationService.getBy(OperationType.SIGN);
                Enterprise enterpriseWf = person.getEnterpriseByEnterpriseIdView();
                Person personWf = personService.getBy(personIdx);
                Operator operatorWf = OperatorAllocator.build(enterpriseWf, personWf, operationWf, document, orderOperation, addTsa);
                operatorService.onlySave(operatorWf);
            });
        }
        Optional<Operator> nextOptionalOperator = operatorService.findNextBy(enterprise, document);
        if (nextOptionalOperator.isPresent()) {
            final Operator operator = nextOptionalOperator.get();
            Assigner newAssigner = AssignerAllocator.build(document, operator);
            assignerService.save(newAssigner);
            Notification notification;
            if (States.SEND_ALERT == operator.getSendAlert()) {
                notification = NotificationAllocator.build4MarkAsNotSent(operator);
            } else {
                notification = NotificationAllocator.build4MarkAsSent(operator);
            }
            notificationService.save(notification);
        }
        DocumentAllocator.forUpdate2(document, multipartFiles.length);
        this.save(document);
        IntStream.range(0, multipartFiles.length).forEach(index -> {
            MultipartFile multipartFile = multipartFiles[index];
            //TODO Revisar el almacenamiento de documentos
            final int orderResource = index + 1;
            String path = resourceService.storeFile(document, orderResource, multipartFile);
            final String rpath = path.replaceFirst(Pattern.quote(File.separator), "");
            Path filePath = this.fileStoragePath.resolve(rpath).normalize();
            List<SignatureResult> signatureResults = this.verifySignatures(filePath.toFile());
            final int numberSignatures = signatureResults.size();
            Resource resource = ResourceAllocator.build(multipartFile, rpath, orderResource);
            resourceService.save(resource);
            this.registerNumberSignaturesBy(document, resource, numberSignatures);
            Documentresource documentresource = DocumentresourceAllocator.build(document, resource);
            documentresourceService.save(documentresource);
            logger.info(String.format("Document received: %s, operatorId: %d", resource, nextOptionalOperator.get().getId()));
        });
        return document;
    }

    private void registerNumberSignaturesBy(Document document, Resource resource, int quantity) {
        if (quantity != 0) {
            Numbersignature numbersignature = NumbersignatureAllocator.build(document, resource, quantity);
            numbersignatureService.save(numbersignature);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Document save(int workflowId, int type, String subject, String replacements, MultipartFile[] multipartFiles, HttpServletRequest request) {
        Person person = personService.getBy(request);
        final Document document = this.save(person, workflowId, type, subject, replacements, multipartFiles);
        traceability.save(person, document, DocumentState.PENDING, TraceabilityType.MANDATORY);
        return document;
    }

    private void assignSieemail2Operator(Participant participant, Operator operatorWf) {
        Optional<Sieemail> optional = sieemailService.findBy(participant);
        if (optional.isPresent()) {
            Sieemail sieemail = optional.get();
            Sieemailoperator sieemailoperator = SieemailoperatorAllocator.build(sieemail, operatorWf);
            sieemailoperatorService.save(sieemailoperator);
        }
    }

    public Integer getPersonIdOf(String replacements, int orderFlow) {
        String[] splitedIdentifiers = replacements.split(Pattern.quote("|"));
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        Arrays.stream(splitedIdentifiers)
                .forEach(identifier -> {
                    String[] splitedIdentifiers3 = identifier.split(Pattern.quote(","));
                    final int orderParticipantIndex = 0;
                    final int personIdIndex = 1;
                    hashMap.put(Integer.parseInt(splitedIdentifiers3[orderParticipantIndex]), Integer.parseInt(splitedIdentifiers3[personIdIndex]));
                });
        final Integer personIdOfReplacement = hashMap.get(orderFlow);
        if (personIdOfReplacement != null) {
            return personIdOfReplacement;
        }
        throw new RuntimeException(String.format("Participante requerido en la posición %d del flujo, no fue encontrado.", orderFlow));
    }

    public Integer getSizeReplacements(String replacements) {
        if (replacements.isEmpty()) {
            return 0;
        }
        return replacements.split(Pattern.quote("|")).length;
    }

    public Integer getSizeReplaceable(List<Participant> participants) {
        return (int) participants
                .stream()
                .filter(participant -> participant.getPersonByPersonId().getReplaceable() == States.REPLACEABLE)
                .count();
    }

    private void verifySizeReplaceableAndReplacements(int replaceables, int replacements) {
        if (replaceables != replacements) {
            logger.error("Both replacements and replaceable size does not match");
            throw new SizeReplacementException("El tamaño de la lista de participantes no es la misma que la del flujo de trabajo.");
        }
    }

    public List<Document> getBy(List<Integer> identifiers, int personId) {
        return repository.findAllByInAndPersonId(identifiers, personId);
    }

    @Transactional
    public Document save(Document document) {
        final Document saved = repository.save(document);
        logger.info(String.format("Document saved with documentId: %d, hashIdentifier: %s", document.getId(), document.getHashIdentifier()));
        return saved;
    }

    public Document endProcess(Document document) {
        DocumentAllocator.forUpdateWithState(document, DocumentState.FINISHED);
        document.setFinished(States.FINISHED);
        return this.save(document);
    }

    private void loadMoreInfoBy(int personId, Document document, MoreAboutDocument moreAboutDocument) {
        Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
        final List<_Comment> comments = loadCommentsBy(document).stream().filter(comment -> !comment.getDescription().equals("")).collect(Collectors.toList());
        final byte hasComment = comments.isEmpty() ? States.HAS_NOT_COMMENT : States.HAS_COMMENT;
        final byte observed = wasObserved(comments) ? States.OBSERVED : States.NOT_OBSERVED;
        List<Resource> resources = resourceService.findAllBy(document);
        if (States.FINISHED == document.getFinished()) {
            String assignmentProgress = getAssignmentProgressAfterFinished(document);
            DocumentAllocator.loadMoreInfo01(moreAboutDocument, document, resources, assignmentProgress, hasComment, comments, observed);
        } else {
            Operator concreteOperator = operatorService.getNextConcreteOperatorBy(enterprise, document);
            boolean willBeClosedStamping = this.willBeClosedStamping(personId, concreteOperator, document);
            final Person person = concreteOperator.getPersonByPersonId();
            Scope scope = scopeService.getBy(person.getId());
            Assigner assigner = assignerService.getBy(person.getId(), document.getId());
            String assignmentProgress = getAssignmentProgressBeforeFinished(document, assigner);
            Integer orderNextSigner = assigner.getOrderOperation();
            final String urlSignOperator = buildUrl(person, document);
            Optional<Stampdatetime> optional = loadStampdatetimeBy(document);
            boolean haveFirstSignature = haveFirstSignature(document);
            byte dynamicWorkflow = document.getWorkflowByWorkflowId().getDynamic();
            if (!haveFirstSignature && optional.isPresent()) {
                Stampdatetime stampdatetimeDb = optional.get();
                Resource resource = resources.stream().findFirst().get();
                int stampOn = getStampOnBy(document, resource, stampdatetimeDb);
                Stampdatetime stampdatetime = StampdatetimeAllocator.forReturn(stampdatetimeDb, stampOn);
                DocumentAllocator.loadMoreInfo02(moreAboutDocument,
                        document,
                        concreteOperator,
                        resources,
                        orderNextSigner,
                        person,
                        urlSignOperator,
                        scope,
                        assignmentProgress,
                        hasComment,
                        comments,
                        observed,
                        stampdatetime,
                        dynamicWorkflow,
                        willBeClosedStamping);
            } else {
                DocumentAllocator.loadMoreInfo02(moreAboutDocument,
                        document,
                        concreteOperator,
                        resources,
                        orderNextSigner,
                        person,
                        urlSignOperator,
                        scope,
                        assignmentProgress,
                        hasComment,
                        comments,
                        observed,
                        dynamicWorkflow,
                        willBeClosedStamping);
            }
        }
        document.setMoreAboutDocument(moreAboutDocument);
    }

    @Override
    public int getStampOnBy(Document document, Resource resource, Stampdatetime stampdatetime) {
        final Integer pageStampId = stampdatetime.getPageStamp();
        Pagestamp pagestamp = pagestampService.getBy(pageStampId);
        Pages2Stamp pages2Stamp = stamperService.translate2Pages2Stamp(pagestamp);
        Path filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
        int numberPages = stamperService.getNumberPages(filePath);
        final int firstPage = 1;
        switch (pages2Stamp) {
            case LAST:
                return numberPages;
            case OTHER:
                final Integer stampOn = stampdatetime.getStampOn();
                if (numberPages < stampOn) {
                    return numberPages;
                } else {
                    return stampOn;
                }
            case FIRST:
            default:
                return firstPage;
        }
    }

    private String getAssignmentProgressAfterFinished(Document document) {
        switch (document.getStateId()) {
            case DocumentState.DELIVERED:
                return "Enviado";
            case DocumentState.ATTENDED:
                return "Atendido";
            case DocumentState.CANCELLED:
                return "Anulado";
            case DocumentState.ATTACH_SENT:
                return "Enviado a correo";
            case DocumentState.MODIFIED:
                return "Modificado";
            default:
                return "Finalizado";
        }
    }

    private String getAssignmentProgressBeforeFinished(Document document, Assigner assigner) {
        if (States.INACTIVE == document.getActive()) {
            return "Anulado";
        }
        switch (document.getStateId()) {
            case DocumentState.CANCELLED:
                return "Anulado";
            default:
                return new StringBuilder(String.valueOf(assigner.getOrderOperation())).append(" de ").append(document.getNumberOperators()).toString();
        }
    }

    private List<_Comment> loadCommentsBy(Document document) {
        List<Done> dones = doneService.findAllBy(document.getId());
        return dones.stream().map(done -> {
            String author = done.getAssignerByAssignerId().getOperatorByOperatorId().getPersonByPersonId().getFullname();
            String description = done.getComment();
            byte observed = done.getObserved();
            Timestamp createAt = done.getCreateAt();
            return new _Comment(author, description, observed, createAt);
        }).collect(Collectors.toList());
    }

    private Optional<Stampdatetime> loadStampdatetimeBy(Document document) {
        final Workflow workflow = document.getWorkflowByWorkflowId();
        List<Stampdatetime> stampdatetimes = stampdatetimeService.findBy(workflow);
        return stampdatetimes.stream().findFirst();
    }

    private boolean wasObserved(List<_Comment> comments) {
        return comments.stream().anyMatch(comment -> comment.getObserved() == States.OBSERVED);
    }


    //TODO agregar campo para no estampar rúbrica cuando no lo tenga configurado
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Document getDocumentInfoBy(int personId, String hashIdentifier) {
        Person person = personService.getBy(personId);
        Optional<Document> optional = repository.findByHashIdentifierAndActiveAndDeleted(hashIdentifier, States.ACTIVE, States.EXISTENT);
        if (optional.isPresent()) {
            final Document document = optional.get();
            MoreAboutDocument moreAboutDocument = new MoreAboutDocument();
            Operator concreteOperator = operatorService.getNextConcreteOperatorBy(document);
            if (!concreteOperator.getPersonByPersonId().getId().equals(personId)) {
                throw new DocumentNotFoundException(String.format("Document not found for personId: %d", personId));
            }
            Optional<Stamprubric> optionalStamprubric = stamprubricService.findBy(personId, document.getId());
            this.loadMoreInfoResources(moreAboutDocument, concreteOperator, document);
            this.loadMoreInfoDocument(moreAboutDocument, concreteOperator, optionalStamprubric.isPresent(), document);
            Temporarysession temporarysession = TemporarysessionAllocator.build(person, applicationService.getUUID());
            temporarysessionService.save(temporarysession);
            this.loadMoreInfoTemporarySession(moreAboutDocument, temporarysession);
            return document;
        }
        logger.warn(String.format("Inexistent document with hashIdentifier: %s", hashIdentifier));
        throw new DocumentNotFoundException("El posible que usted ya haya realizado la firma o que el documento esté siendo firmado por otro usuario o que el enlace sea incorrecto. Por favor, comuníquese con el administrador.");
    }

    //TODO verificar el parámetro personId
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Document getBy(String hashIdentifier) {
        Optional<Document> optional = repository.findByHashIdentifierAndActiveAndDeleted(hashIdentifier, States.ACTIVE, States.EXISTENT);
        if (optional.isPresent()) {
            return optional.get();
        }
        logger.warn(String.format("Inexistent document with hashIdentifier: %s", hashIdentifier));
        throw new DocumentNotFoundException("El posible que usted ya haya realizado la firma o que el documento esté siendo firmado por otro usuario o que el enlace sea incorrecto. Por favor, comuníquese con el administrador.");
    }

    private void loadMoreInfoResources(MoreAboutDocument moreAboutDocument, int personId, Document document) {
        List<Resource> resources = resourceService.findAllBy(document);
        DocumentAllocator.loadMoreInfoResources(moreAboutDocument, personId, document, resources);
        document.setMoreAboutDocument(moreAboutDocument);
    }

    private void loadMoreInfoResources(MoreAboutDocument moreAboutDocument, Operator operator, Document document) {
        List<Resource> resources = resourceService.findAllBy(document);
        DocumentAllocator.loadMoreInfoResources(moreAboutDocument, operator.getPersonId(), document, resources);
//        moreAboutDocument.setDigitalSignature(operator.getDigitalSignature());
        document.setMoreAboutDocument(moreAboutDocument);
    }

    private void loadMoreInfoDocument(MoreAboutDocument moreAboutDocument, Operator operator, boolean hasConfigRubric, Document document) {
        moreAboutDocument.setDigitalSignature(operator.getDigitalSignature());
        moreAboutDocument.setHasRubricSettings(hasConfigRubric);
        document.setMoreAboutDocument(moreAboutDocument);
    }

    private void loadMoreInfoTemporarySession(MoreAboutDocument moreAboutDocument, Temporarysession temporarysession) {
        moreAboutDocument.setUuid(temporarysession.getUuid());
    }

    @Override
    public _Embedded getVerificationInfoBy(String hashIdentifier) {
        return getVerificationInfoBy(hashIdentifier, States.PUBLIC);
    }

    @Override
    public _Embedded getVerificationInfoBy(String hashIdentifier, byte accessLevel) {
        Historicalhash historicalhash = historicalHashService.getBy(hashIdentifier);
        final Document document = historicalhash.getDocumentByDocumentId();
        if (States.PUBLIC == accessLevel) {
            if (States.NOT_FINISHED == document.getFinished()) {
                throw new DocumentVerifyException("Verificación de documento aún no disponible.");
            }
        }
        Optional<Done> optional = doneService.getLastBy(document.getId());
        Resource resource = optional.get().getResourceByResourceId();
        return loadVerifyData(document, resource);
    }

    @Override
    public _Embedded getVerificationInfoBy(String hashIdentifier, String hashResource) {
        return getVerificationInfoBy(hashIdentifier, hashResource, States.PUBLIC);
    }

    @Override
    public _Embedded getVerificationInfoBy(String hashIdentifier, String hashResource, byte accessLevel) {
        Historicalhash historicalhash = historicalHashService.getBy(hashIdentifier);
        final Document document = historicalhash.getDocumentByDocumentId();
        if (States.PUBLIC == accessLevel) {
            if (States.NOT_FINISHED == document.getFinished()) {
                throw new DocumentVerifyException("Verificación de documento aún no disponible.");
            }
        }
        Optional<Done> optional = doneService.getLastBy(document.getId(), hashResource);
        Resource resource = optional.get().getResourceByResourceId();
        return loadVerifyData(document, resource);
    }

    private _Embedded loadVerifyData(Document document, Resource resource) {
        Optional<Numbersignature> optionalNumbersignature = numbersignatureService.findBy(document, resource.getHash());
        int numberSignature = optionalNumbersignature.isPresent() ? optionalNumbersignature.get().getQuantity() : 0;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Path filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
        File signedFile = new File(filePath.toUri());
        List<SignatureResult> signatureResults = this.verifySignatures(signedFile);
        /**
         * Cargar información de los firmantes (digital)
         */
        List<_Operator> _operators = signatureResults.stream().map(signatureResult -> {
            _Operator _operator = new _Operator();
            if (atomicInteger.getAndIncrement() < numberSignature) {
                _operator.setIsOldSignature(Constant.OLD_SIGNATURE);
            } else {
                _operator.setIsOldSignature(Constant.NEW_SIGNATURE);
            }
            Operation operation = operationService.getBy(OperationType.SIGN);
            if (signatureResult.isCorrect()) {
                X509Certificate x509Certificate = signatureResult.getSignerCertificate();
                TimeStampToken timeStampToken = signatureResult.getTimeStampToken();
                DocumentAllocator.loadSignInfo(x509Certificate, timeStampToken, _operator, operation, States.CORRECT_OPERATION);
            } else {
                _operator.setOperatorName("DESCONOCIDO");
                _operator.setOperationId(operation.getId());
                _operator.setIssuerCertName("-");
                _operator.setCertainDateTime("-");
                _operator.setOperationDescription(operation.getDescription());
                _operator.setTsa("-");
                _operator.setCorrectOperation(States.INCORRECT_OPERATION);
                _operator.setDigitalSignature(true);
                _operator.setTypeElectronicSignature(ElectronicSignature.ANYTHING);
            }
            return _operator;
        }).collect(Collectors.toList());
        /**
         * Cargar información de los firmantes (electrónicas) y revisores
         */
        loadOtheOperatorsInfo(document, _operators);
        _Embedded embedded = new _Embedded(_operators);
        loadDocumentInfo4Verification(embedded, document);
        Optional<Historicaldocumentmodification> optional = historicaldocumentmodificationService.findBy(document);
        if (optional.isPresent()) {
            final Historicaldocumentmodification historicalmodification = optional.get();
            List<Documentmodification> documentmodifications = documentmodificationService.findHistoricalGreaterThan(historicalmodification.getCreateAt());
            final Document lastDocumentNew = documentmodifications
                    .stream()
                    .reduce((first, second) -> second)
                    .orElse(null).getDocumentByDocumentIdNew();
            final String urlLastDocumentResource = resourceService.buildURLVerifier4FirstResource(lastDocumentNew);
            documentmodifications = documentmodifications.stream().map(documentmodification -> {
                Documentmodification documentmodificationOptimized = new Documentmodification();
                Document documentOld = documentmodification.getDocumentByDocumentIdOld();
                Document documentNew = documentmodification.getDocumentByDocumentIdNew();
                Document documentOldOptimized = new Document();
                documentOldOptimized.setId(documentOld.getId());
                documentOldOptimized.setCreateAt(documentOld.getCreateAt());
                documentOldOptimized.setFinished(documentOld.getFinished());
                documentOldOptimized.setStateId(documentOld.getStateId());
                Document documentNewOptimized = new Document();
                documentNewOptimized.setId(documentNew.getId());
                documentNewOptimized.setCreateAt(documentNew.getCreateAt());
                documentNewOptimized.setFinished(documentNew.getFinished());
                documentNewOptimized.setStateId(documentNew.getStateId());
                documentmodificationOptimized.setId(documentmodification.getId());
                documentmodificationOptimized.setDescription(documentmodification.getDescription());
                documentmodificationOptimized.setCreateAt(documentmodification.getCreateAt());
                documentmodificationOptimized.setDocumentByDocumentIdOld(documentOldOptimized);
                documentmodificationOptimized.setDocumentByDocumentIdNew(documentNewOptimized);
                return documentmodificationOptimized;
            }).collect(Collectors.toList());
            embedded.setDocumentmodifications(documentmodifications);
            embedded.setUrlLastDocumentResource(urlLastDocumentResource);
        }
        return embedded;
    }

    private void loadOtheOperatorsInfo(Document document, List<_Operator> operators) {
        Operation operation = operationService.getBy(OperationType.REVIEW);
        List<Done> dones;
        if (States.FINISHED == document.getFinished()) {
            List<Integer> operationIds = Arrays.asList(OperationType.SIGN, OperationType.REVIEW);
            dones = doneService.findByFinishedDocument(document.getId(), operationIds);
        } else {
            dones = doneService.findBy(document.getId(), operation.getId());
        }
        List<Done> filteredDones = new ArrayList<>();
        dones.stream().forEach(done -> {
            final Integer assignerId = done.getAssignerId();
            final Operator operator = done.getAssignerByAssignerId().getOperatorByOperatorId();
            if (!isAddedDoneByAssignerId(filteredDones, assignerId) && !operator.getDigitalSignature()) {
                filteredDones.add(done);
            }
        });
        filteredDones.stream().forEach(done -> {
            _Operator operator = new _Operator();
            final Operator operatorByOperatorId = done.getAssignerByAssignerId().getOperatorByOperatorId();
            final String operatorName = operatorByOperatorId.getPersonByPersonId().getFullname();
            final int operationId = operatorByOperatorId.getOperationId();
            operator.setOperatorName(operatorName);
            operator.setOperationId(operationId);
            operator.setCertainDateTime(DateUtil.build(done.getCreateAt(), "dd/MM/yyyy HH:mm:ss"));
            operator.setIssuerCertName("-");
            operator.setTsa("-");
            final String signatureTypeDescription = this.signatureTypeDescription(operatorByOperatorId);
            operator.setOperationDescription(signatureTypeDescription);
            operator.setCorrectOperation(States.CORRECT_OPERATION);
            operator.setTypeElectronicSignature(operatorByOperatorId.getTypeElectronicSignature());
            operator.setDigitalSignature(false);
            operators.add(operator);
        });
    }

    private String signatureTypeDescription(Operator operator) {
        switch (operator.getOperationId()) {
            case OperationType.SIGN:
                if (operator.getDigitalSignature()) {
                    return "Firma digital";
                } else {
                    final Integer typeElectronicSignature = operator.getTypeElectronicSignature();
                    switch (typeElectronicSignature) {
                        case ElectronicSignature.GRAPHIC_SIGNATURE:
                            return "Firma gráfica";
                        default:
                            return "-";
                    }
                }
            case OperationType.REVIEW:
                return "Revisión";
            default:
                return "*";
        }
    }

    private boolean isAddedDoneByAssignerId(List<Done> dones, int assignerId) {
        return dones.stream().filter(done -> assignerId == done.getAssignerId()).findFirst().isPresent();
    }

    @Override
    public Page<Document> getAllBy4User(int enterpriseId, int workflowId, int personId, String term2Search, String states, Pageable pageable) {
        logger.info(String.format("Looking for documents by enterpriseId: %d, personId: %d, page: %s", enterpriseId, personId, pageable));
        List<Integer> workflowIds = getWorkflowIdsBy(personId, workflowId);
        List<Integer> stateIds = StringUtil.split2Integers(states, ",");
        Page<Document> documentPage;
        /**
         * No se está usando el 'enterpriseId' en las consultas como inicialmente se hacía debido a que, el usuario
         * requiere ver en su bandeja documentos que le fueron enviados siendo parte del flujo de otra empresa. Es
         * decir; cuando el usuario es un 'Usuario invitado' de alguna empresa.
         */
        if (term2Search.isEmpty()) {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, pageable);
            } else {
                documentPage = repository.findAllBy4User(workflowIds, stateIds, pageable);
            }
        } else {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, term2Search, pageable);
            } else {
                documentPage = repository.findAllBy4User(workflowIds, stateIds, term2Search, pageable);
            }
        }
        this.loadMoreInfoDocumentBy(personId, documentPage);
        return documentPage;
    }

    private boolean requireOnlyMyPendings(List<Integer> stateIds) {
        return stateIds.size() == 1 && stateIds.contains(DocumentState.MY_PENDINGS);
    }

    private void loadMoreInfoDocumentBy(int personId, Page<Document> documentPage) {
        documentPage.stream().forEach(document -> {
            MoreAboutDocument moreAboutDocument = new MoreAboutDocument();
            this.loadMoreInfoBy(personId, document, moreAboutDocument);
            this.loadMoreInfoResources(moreAboutDocument, personId, document);
            document.setMoreAboutDocument(moreAboutDocument);
        });
    }

    private List<Integer> getWorkflowIdsBy(int personId, int workflowId) {
        boolean optionAllWorkflowsSelected = this.optionAllWorkflowsSelectedBy(workflowId);
        if (optionAllWorkflowsSelected) {
            List<Workflow> workflows = workflowService.findAllWhereIsParticipantOrAssigned(personId);
            return workflows.stream()
                    .map(workflow -> workflow.getId())
                    .collect(Collectors.toList());
        } else {
            return Collections.singletonList(workflowId);
        }
    }

    private boolean optionAllWorkflowsSelectedBy(int workflowId) {
        Workflow workflow = workflowService.getBy(workflowId);
        return Default.ENTERPRISE_ID_VIEW == workflow.getEnterpriseId();
    }

    private List<Integer> getPersonIdsBy(int personId) {
        int personIdRelated = personId;// For readability
        Optional<Relatedperson> optional = relatedpersonService.findByPersonIdRelated(personIdRelated);
        if (!optional.isPresent()) {
            return Collections.singletonList(personId);
        } else {
            int personId_ = optional.get().getPersonByPersonId().getId();
            return Arrays.asList(personIdRelated, personId_);
        }
    }

    public Document findByOperatorId(int operatorId) {
        return repository.findBy(operatorId);
    }

    public Document findByWithoutConditions(int operatorId) {
        return repository.findByWithoutConditions(operatorId);
    }

    public Document getBy(int documentId, String hashIdentifier) {
        final Optional<Document> optional = repository.findFirstByIdAndHashIdentifierAndActiveAndDeleted(documentId, hashIdentifier, States.ACTIVE, States.EXISTENT);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new DocumentNotFoundException(String.format("Document not found for documentId: %d, hashIdentifier: %s", documentId, hashIdentifier));
        }
    }

    @Override
    public Page<Document> getAllBy4Admins(int enterpriseId, int workflowId, String term2Search, String states, Pageable pageable, HttpServletRequest request) {
        logger.info(String.format("Looking for documents by enterpriseId: %d, workflowId: %d, term2Search: %s, page: %s", enterpriseId, workflowId, term2Search, pageable));
        Person person = personService.getBy(request);
        int personId = person.getId();
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        List<Integer> enterpriseIds = enterpriseService.getEnterpriseIdsBy(enterprise);
        List<Integer> workflowIds = getWorkflowIdsBy2(enterprise, person, workflowId);
        List<Integer> stateIds = StringUtil.split2Integers(states, ",");
        Page<Document> documentPage;
        if (term2Search.isEmpty()) {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, pageable);
            } else {
                documentPage = repository.findAllBy4Admins(enterpriseIds, workflowIds, stateIds, pageable);
            }
        } else {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, term2Search, pageable);
            } else {
                documentPage = repository.findAllBy4Admins(enterpriseIds, workflowIds, stateIds, term2Search, pageable);
            }
        }
        this.loadMoreInfoDocumentBy(personId, documentPage);
        return documentPage;
    }

    private List<Integer> getWorkflowIdsBy2(Enterprise enterprise, Person person, int workflowId) {
        if (userService.hasRole(person, SUPERVISOR)) {
            return getWorkflowIdsBy(person.getId(), workflowId);
        }
        boolean optionAllWorkflowsSelected = this.optionAllWorkflowsSelectedBy(workflowId);
        if (States.EXCLUDED == enterprise.getExcluded() && optionAllWorkflowsSelected) {
            return workflowService.findAll()
                    .stream()
                    .map(workflow -> workflow.getId())
                    .collect(Collectors.toList());
        }
        if (optionAllWorkflowsSelected) {
            return workflowService.findAllBy(enterprise.getId())
                    .stream()
                    .map(workflow -> workflow.getId())
                    .collect(Collectors.toList());
        } else {
            return Collections.singletonList(workflowId);
        }
    }

    public List<Document> getBy(List<Integer> identifiers) {
        return repository.findAllByIndetifiers(identifiers);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Document> save(List<Document> documents) {
        return documents.stream().map(this::save).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Document> update(int personId, int stateId, String docIdentifiers) {
        List<Integer> identifiers = StringUtil.split2Integers(docIdentifiers, ",");
        List<Document> documents = this.getBy(identifiers);
        State state = stateService.getBy(stateId);
        final int documentState = state.getId() == DocumentState.ATTENDED ? DocumentState.ATTENDED : DocumentState.FINISHED;
        List<Document> candidateDocuments = documents
                .stream()
                .filter(document -> DocumentState.FINISHED == document.getStateId() || DocumentState.DELIVERED == document.getStateId() || DocumentState.ATTENDED == document.getStateId())
                .map(document -> {
                    document.setStateId(documentState);
                    return document;
                })
                .collect(Collectors.toList());
        final List<Document> documentsUpdated = this.save(candidateDocuments);
        traceability.save(personId, candidateDocuments, documentState, TraceabilityType.MANDATORY);
        logger.info(String.format("Documents updated for personId: %d, with statedId: %d, to docIdentifiers: %s", personId, stateId, docIdentifiers));
        return documentsUpdated;
    }

    public String buildUrl(Person person, Document document) {
        return new StringBuilder(Global.ROOT_FRONT)
                .append("/document/")
                .append(document.getHashIdentifier())
                .append("/people/")
                .append(person.getId())
                .append("/sign").toString();
    }

    public String buildHashIdentifier(String subject) {
        return Hash.sha256(LocalDateTime.now().toString() + subject);
    }

    public Document getBy(Resource resource) {
        Optional<Document> optional = repository.findBy(resource);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new DocumentNotFoundException(String.format("Document not found for resource by resourceId: %d", resource.getId()));
    }

    public void checkPermissionsOnFlow(int workflowId, HttpServletRequest request) {
        Person person = personService.getBy(request);
        boolean optionAllWorkflowsSelected = this.optionAllWorkflowsSelectedBy(workflowId);
        if (optionAllWorkflowsSelected || userService.requestUserHasRole(request, SUPERADMIN) || userService.requestUserHasRole(request, ADMIN)) {
            return;
        }
        List<Workflow> workflows = workflowService.findAllWhereIsParticipantOrAssigned(person);
        Optional<Workflow> optional = workflows.stream().filter(workflow -> workflowId == workflow.getId()).findFirst();
        if (!optional.isPresent()) {
            throw new UnauthorizedResourceException("No tiene permisos sobre el flujo seleccionado, por favor comuníquese con su administrador.");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Document save(Person person, _Body body) {
        List<Participant> participants = body.getParticipants();
        Enterprise enterprise = person.getEnterpriseByEnterpriseId();
        int partnerId = enterprise.getPartnerId();
        participants.forEach(participant -> {
            final Person person1 = participant.getPersonByPersonId();
            person1.setPartnerId(partnerId);
            validateMail(person1);
        });
        Integer workflowId = body.getWorkflowId();
        String subject = body.getSubject();
        Integer type = body.getType();
        String replacements = buildReplacements(participants, enterprise);
        MultipartFile[] multipartFiles = FileUtil.buildMultipartFiles(body.getFiles());
        final Document document = this.save(person, workflowId, type, subject, replacements, multipartFiles);
        traceability.save(person, document, DocumentState.PENDING, TraceabilityType.MANDATORY);
        return document;
    }

    private void validateMail(Person person) {
        if (person.getEmail() == null || person.getEmail().isEmpty()) {
            String fullname = person.getFirstname() + " " + person.getLastname();
            throw new IllegalArgumentException(String.format("El correo electrónico del participante: %s debe ser enviado .", fullname));
        }
    }

    private String buildReplacements(List<Participant> participants, Enterprise enterprise) {
        return participants.stream().map(participant -> {
            Person person = participant.getPersonByPersonId();
            PersonAllocator.ofApi2Clean(person);
            Enterprise enterpriseView;
            if (PersonType.NATURAL == person.getType()) {
                enterpriseView = new Enterprise();
                enterpriseView.setId(Default.ENTERPRISE_ID_VIEW);
            } else {
                String enterpriseDocumentNumber = person.getEnterpriseDocumentNumber();
                enterpriseView = enterpriseService.getBy(enterpriseDocumentNumber);
            }
            PersonAllocator.ofApiMethod(person, enterprise, enterpriseView);
            personService.onlySave(person);
            Job job;
            if (PersonType.NATURAL != person.getType()) {
                String jobDescription = person.getJobDescription();
                job = jobService.saveIfNotExist(person.getEnterpriseIdView(), jobDescription);
            } else {
                job = new Job(Default.JOB_ID);
            }
            Employee employee = EmployeeAllocator.build(person, job);
            employeeService.save(employee);
            person.setScope(ParticipantType.INVITED);
            Scope scope = ScopeAllocator.build(person);
            scopeService.save(scope);
            return new StringBuilder(String.valueOf(participant.getOrderParticipant())).append(",").append(person.getId()).toString();
        }).collect(Collectors.joining("|"));
    }

    @Override
    public Page<Document> getAll4Assistant(int enterpriseId, int workflowId, int personId, String term2Search, String states, Pageable pageable) {
        logger.info(String.format("Looking for documents by enterpriseId: %d, workflowId: %d, term2Search: %s, page: %s", enterpriseId, workflowId, term2Search, pageable));
        List<Integer> workflowIds = getWorkflowIdsBy(personId, workflowId);
        List<Integer> personIds = getPersonIdsBy(personId);
        List<Integer> stateIds = StringUtil.split2Integers(states, ",");
        Page<Document> documentPage;
        if (term2Search.isEmpty()) {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, pageable);
            } else {
                documentPage = repository.findAllBy4Assistant(enterpriseId, workflowIds, personIds, stateIds, pageable);
            }
        } else {
            if (requireOnlyMyPendings(stateIds)) {
                stateIds.clear();
                stateIds.add(DocumentState.PENDING);
                documentPage = repository.findPendingsBy4User(workflowIds, stateIds, personId, term2Search, pageable);
            } else {
                documentPage = repository.findAllBy4Assistant(enterpriseId, workflowIds, personIds, stateIds, term2Search, pageable);
            }
        }
        this.loadMoreInfoDocumentBy(personId, documentPage);
        return documentPage;
    }

    public Document getBy4Outside(int documentId) {
        Document document = this.getByNoDeleted(documentId);
        List<_Operator> _operators = loadOperatorInfo(document);
        Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
        MoreAboutDocument moreAboutDocument = new MoreAboutDocument();
        loadMoreInfoBy4Outside(enterprise, document, _operators, moreAboutDocument);
        return document;
    }

    private List<_Operator> loadOperatorInfo(Document document) {
        AtomicBoolean cancelPersonWasFounded = new AtomicBoolean(false);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Optional<Observationcancel> optObscancel = observationcancelService.findByDocument(document);
        List<Operator> operators = operatorService.getAllBy(document);
        List<Done> dones = doneService.findAllBy(document.getId());
        List<_Operator> responseOperators = operators.stream().map(operator -> {
            _Operator responseOperator = new _Operator(States.IS_OPERATOR, "-", "-", "-", "-", "-", "-", -1);
            final Operation operation = operationService.getBy(operator.getOperationId());
            final Person person = operator.getPersonByPersonId();
            final Optional<Job> optionalJob = jobService.findByPerson(person);
            responseOperator.setOperationDescription(operation.getDescription());
            responseOperator.setOperatorName(person.getFullname());
            responseOperator.setOrderOperation(atomicInteger.getAndIncrement());
            if (optionalJob.isPresent()) {
                Job job = optionalJob.get();
                responseOperator.setJobDescription(job.getDescription());
            }
            Optional<Assigner> optionalAssigner = assignerService.findBy(operator.getId());
            if (optionalAssigner.isPresent()) {
                Assigner assigner = optionalAssigner.get();
                if (States.COMPLETED == assigner.getCompleted()) {
                    responseOperator.setStateDescription("Finalizado");
                } else {
                    if (DocumentState.CANCELLED != document.getStateId()) {
                        responseOperator.setStateDescription("Pendiente");
                    }
                }
                Optional<Done> optionalDone = doneService.findUniqueBy(assigner);
                if (optionalDone.isPresent()) {
                    Done done = optionalDone.get();
                    responseOperator.setOperatedAt(DateUtil.build(done.getCreateAt(), "dd-MM-yyyy"));
                } else {
                    responseOperator.setOperatedAt("-");
                }
                if (optObscancel.isPresent() && isCanceller(operator, optObscancel.get())) {
                    this.assignCancellationComment(operator, optObscancel.get(), responseOperator, cancelPersonWasFounded);
                } else {
                    Optional<Done> existentDoneObservation = dones.stream()
                            .filter(done -> operator.getPersonId().equals(done.getAssignerByAssignerId().getOperatorByOperatorId().getPersonId()))
                            .findFirst();
                    if (existentDoneObservation.isPresent()) {
                        Done done = existentDoneObservation.get();
                        if (States.OBSERVED == done.getObserved()) {
                            responseOperator.setStateDescription("Observado");
                        } else {
                            responseOperator.setStateDescription("Comentado");
                        }
                        responseOperator.setComment(done.getComment());
                    }
                }
            } else {
                if (!optObscancel.isPresent()) {
                    responseOperator.setStateDescription("Pendiente");
                }
            }
            return responseOperator;
        }).collect(Collectors.toList());
        if (optObscancel.isPresent() && !cancelPersonWasFounded.get()) {
            this.assignCancellationCommentOfExternalPerson(optObscancel.get(), responseOperators);
        }
        responseOperators.stream().forEach(operator -> operator.setCreateAt(null));
        return responseOperators;
    }

    private boolean isCanceller(Operator operator, Observationcancel observationcancel) {
        return operator.getPersonId().equals(observationcancel.getPersonId());
    }

    private void assignCancellationComment(Operator operator, Observationcancel observationcancel, _Operator responseOperator, AtomicBoolean cancelPersonWasFounded) {
        responseOperator.setStateDescription("Anulado");
        responseOperator.setComment(observationcancel.getDescription());
        cancelPersonWasFounded.set(true);
    }

    private void assignCancellationCommentOfExternalPerson(Observationcancel observationcancel, List<_Operator> responseOperators) {
        Person person = observationcancel.getPersonByPersonId();
        Job job = jobService.getByPerson(person);
        _Operator responseOperator = _OperatorAllocator.build(person, observationcancel, job);
        Optional<_Operator> optionalOperator = responseOperators.stream().filter(_operator0 -> "Anulado".equals(_operator0.getStateDescription())).findFirst();
        if (optionalOperator.isPresent()) {
            _Operator _operator1 = optionalOperator.get();
            int index = _operator1.getOrderOperation();
            responseOperators.add(index, responseOperator);
        } else {
            responseOperators.add(responseOperator);
        }
    }

    private void loadMoreInfoBy4Outside(Enterprise enterprise, Document document, List<_Operator> operators, MoreAboutDocument moreAboutDocument) {
        if (States.FINISHED == document.getFinished()) {
            String assignmentProgress = getAssignmentProgressAfterFinished(document);
            DocumentAllocator.loadMoreInfo01Outside(moreAboutDocument, assignmentProgress, operators);
        } else {
            Operator concreteOperator = operatorService.getNextConcreteOperatorBy(enterprise, document);
            final Person person = concreteOperator.getPersonByPersonId();
            Assigner assigner = assignerService.getBy(person.getId(), document.getId());
            String assignmentProgress = new StringBuilder(String.valueOf(assigner.getOrderOperation())).append(" de ").append(document.getNumberOperators()).toString();
            DocumentAllocator.loadMoreInfo02Outside(moreAboutDocument, person, assignmentProgress, assigner.getOrderOperation(), document.getNumberOperators(), operators);
        }
        document.setMoreAboutDocument(moreAboutDocument);
    }

    /*
    private void loadMoreInfoBy4Outside(Enterprise enterprise, Document document, MoreAboutDocument moreAboutDocument) {
        if (States.FINISHED == document.getFinished()) {
            String assignmentProgress = getAssignmentProgressAfterFinished(document);
            DocumentAllocator.loadMoreInfo01Outside(moreAboutDocument, assignmentProgress);
        } else {
            Operator concreteOperator = operatorService.getNextConcreteOperatorBy(enterprise, document);
            final Person person = concreteOperator.getPersonByPersonId();
            Assigner assigner = assignerService.getBy(person.getId(), document.getId());
            String assignmentProgress = new StringBuilder(String.valueOf(assigner.getOrderOperation())).append(" de ").append(document.getNumberOperators()).toString();
            DocumentAllocator.loadMoreInfo02Outside(moreAboutDocument, person, assignmentProgress, assigner.getOrderOperation(), document.getNumberOperators());
        }
        document.setMoreAboutDocument(moreAboutDocument);
    }*/

    @Override
    public _Embedded getVerificationInfoByResumeHashOfResource(String resumeHashResource) {
        Optional<Resource> optional = resourceService.findBy(resumeHashResource);
        if (optional.isPresent()) {
            Resource resource = optional.get();
            Documentresource documentresource = documentresourceService.getBy(resource);
            Document document = documentresource.getDocumentByDocumentId();
            final _Embedded embedded = getVerificationInfoBy(document.getHashIdentifier(), resource.getHash());
            loadDocumentInfo4Verification(embedded, document);
            return embedded;
        } else {
            throw new ResourceNotFoundException(String.format("Recurso no encontrado con el código de verificación: %s", resumeHashResource));
        }
    }

    public Optional<Document> findByNoDeleted(int documentId) {
        return repository.findByIdAndDeleted(documentId, States.EXISTENT);
    }

    public Document getByNoDeleted(int documentId) {
        final Optional<Document> optional = this.findByNoDeleted(documentId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new DocumentNotFoundException(String.format("Document not found for delete with documentId: %d", documentId));
        }
    }

    public Document delete(int documentId) {
        Document documentdb = this.getByNoDeleted(documentId);
        DocumentAllocator.forDelete(documentdb, States.DELETED);
        return this.save(documentdb);
    }

    public boolean haveFirstSignature(Document document) {
        Optional<Assigner> optional = assignerService.findFirstBy(document.getId());
        if (optional.isPresent()) {
            Assigner assigner = optional.get();
            return States.COMPLETED == assigner.getCompleted();
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Document cancellation(Observationcancel observationcancel, HttpServletRequest request) {
        Person person = personService.getBy(request);
        int documentId = observationcancel.getDocumentId();
        Document document = this.getBy(documentId);
        String comment = observationcancel.getDescription();
        return cancel(person, document, comment);
    }

    public Document cancel(Person person, Document document, String comment) {
        Observationcancel observationcancel = ObservationcancelAllocator.build(person, document, comment);
        observationcancelService.save(observationcancel);
        DocumentAllocator.forUpdate(document, States.INACTIVE);
        this.save(document);
        logger.info(String.format("Document was cancelled by personId: %d, documentId: %d", person.getId(), document.getId()));
        traceability.save(person, document, DocumentState.CANCELLED, TraceabilityType.MANDATORY);
        Cancelnotification cancelnotification = CancelnotificationAllocator.build(document, person);
        cancelnotificationService.save(cancelnotification);
        logger.info(String.format("Cancelnotification was saved by personId: %d, documentId: %d", person.getId(), document.getId()));
        return document;
    }

    public boolean isAllowedDeleted(HttpServletRequest request, int documentId) {
        Document document = getBy(documentId);
        Person person = personService.getBy(request);
        Enterprise enterprise = document.getWorkflowByWorkflowId().getEnterpriseByEnterpriseId();
        Operator operator = operatorService.getNextConcreteOperatorBy(enterprise, document);
        boolean isFirstSigner = Default.FIRST_SIGNER == operator.getOrderOperation();
        return document.getPersonId().equals(person.getId()) || isFirstSigner;
    }

    public boolean isAllowedCancelled(HttpServletRequest request, Observationcancel observationcancel) {
        int documentId = observationcancel.getDocumentId();
        Document document = getBy(documentId);
        Enterprise enterprise = document.getWorkflowByWorkflowId().getEnterpriseByEnterpriseId();
        Person person = personService.getBy(request);
        Operator operator = operatorService.getNextConcreteOperatorBy(enterprise, document);
        return operator.getPersonId().equals(person.getId());
    }

    public List<SignatureResult> verifySignatures(File file) {
        Path trustStore = FileUtil.copyInputStreamOfResources("truststore.jks");
        char[] passwordTruststore = "1234".toCharArray();
        try {
            return PdfSigner.verify(file, trustStore.toFile(), passwordTruststore);
        } catch (RuntimeException e) {
            throw new AlteredDocumentException("La verificación del documento no se encuentra disponible, probablemente usted cargó un documento firmado digitalmente en un flujo que agrega al documento un código QR alterando el mismo e invalidando la firmas digitales anteriores.");
        }
    }

    @Override
    public boolean isFinished(Document document) {
        return States.FINISHED == document.getFinished();
    }

    @Override
    public boolean isCanceled(Document document) {
        return States.CANCELLED == document.getStateId();
    }

    //TODO Al unir el código el método falta completar
    @Override
    public Document getDocumentInfoBy(String resumeHashResource) {
        Optional<Resource> optional = resourceService.findBy(resumeHashResource);
        if (optional.isPresent()) {
            Resource resource = optional.get();
            Documentresource documentresource = documentresourceService.getBy(resource);
            Document document = documentresource.getDocumentByDocumentId();
            Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
            List<_Operator> _operators = loadOperatorInfo(document);
            MoreAboutDocument moreAboutDocument = new MoreAboutDocument();
            loadMoreInfoBy4Outside(enterprise, document, _operators, moreAboutDocument);
            return document;
        } else {
            throw new ResourceNotFoundException(String.format("Documento no encontrado para el código de verificación: %s", resumeHashResource));
        }
    }

    public boolean willBeClosedStamping(int personId, Document document) {
        Operator operator = operatorService.getNextConcreteOperatorBy(document);
        return willBeClosedStamping(personId, operator, document);
    }

    public boolean willBeClosedStamping(int personId, Operator operator, Document document) {
        if (personId != operator.getPersonId()) {
            return false;
        }
        final Boolean isDigitalSignature = operator.getDigitalSignature();
        if (isDigitalSignature) {
            return true;
        } else {
            return document.getClosedStamping();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void signElectronically(int personId, int subOperationType, String docIdentifiers) {
        List<Integer> identifiers = StringUtil.split2Integers(docIdentifiers, ",");
        List<Document> documents = this.getBy(identifiers);
        documents.stream().forEach(document -> {
            List<Resource> resources = resourceService.findAllBy(document);
            resources.stream().forEach(resource -> {
                org.springframework.core.io.Resource streamResource = resourceService.getStreamBy2(personId, document.getId(), document.getHashIdentifier(), resource.getOrderResource(), ScopeResource.PUBLIC);
                try {
                    final boolean willBeClosedStamping = this.willBeClosedStamping(personId, document);
                    resourceService.save(personId, subOperationType, document.getId(), document.getHashIdentifier(), resource.getOrderResource(), streamResource.getInputStream(), willBeClosedStamping);
                } catch (IOException e) {
                    logger.error("Saving signed electronically file", e);
                }
            });
//            traceability.save(personId, document, DocumentState.SIGNED, TraceabilityType.MANDATORY);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void signElectronically(int personId, int subOperationType, String docIdentifiers, String uuid) {
        Person person = personService.getBy(personId);
        Optional<Temporarysession> optional1 = temporarysessionService.findBy(personId, uuid);
        if (optional1.isPresent()) {
            this.signElectronically(personId, subOperationType, docIdentifiers);
        } else {
            throw new TemporaryNotFoundException(String.format("Temporary not found by personId: %d, UUID: %s", personId, uuid));
        }
    }

//    private void signElectronically(String docIdentifiers, int personId) {
//        List<Integer> identifiers = StringUtil.split2Integers(docIdentifiers, ",");
//        List<Document> documents = this.getBy(identifiers);
//        documents.stream().forEach(document -> {
//            List<Resource> resources = resourceService.findAllBy(document);
//            resources.stream().forEach(resource -> {
//                org.springframework.core.io.Resource streamResource = resourceService.getStreamBy2(personId, document.getId(), document.getHashIdentifier(), resource.getOrderResource(), ScopeResource.PUBLIC);
//                try {
//                    final boolean willBeClosedStamping = this.willBeClosedStamping(personId, document);
//                    resourceService.save(personId, document.getId(), streamResource.getInputStream(), document.getHashIdentifier(), resource.getOrderResource(), willBeClosedStamping);
//                } catch (IOException e) {
//                    logger.error("Saving signed electronically file", e);
//                }
//            });
//        });
//    }

    public String getURLStreamBy(Document document) {
        return new StringBuilder(Global.ROOT_API_V1)
                .append("/public/outside/documents/")
                .append(document.getId())
                .append("/")
                .append(document.getHashIdentifier())
                .append("/stream").toString();
    }

    public Document getByNoDeleted(int documentId, String hashIdentifier) {
        final Optional<Document> optionalDocument = repository.findByIdAndHashIdentifierAndDeleted(documentId, hashIdentifier, States.EXISTENT);
        if (optionalDocument.isPresent()) {
            return optionalDocument.get();
        } else {
            throw new DocumentNotFoundException(String.format("Document not found with documentId: %d, hashIdentifier: %s", documentId, hashIdentifier));
        }
    }

    private void loadDocumentInfo4Verification(_Embedded embedded, Document document) {
        Document document1 = new Document();
        document1.setFinished(document.getFinished());
        document1.setStateId(document.getStateId());
        embedded.setDocument(document1);
    }

    @Transactional(rollbackFor = Exception.class)
    public Document modify(int workflowId, int type, String subject, String replacements, MultipartFile[] multipartFiles, int documentIdModified, String reasonModification, HttpServletRequest request) {
        Document documentOld = this.getBy(documentIdModified);
        boolean finished = States.FINISHED == documentOld.getFinished();
        if (!finished) {
            throw new DocumentNotFinishedException("No está permitido modificar un documento que aún no fue finalizado.");
        }
        boolean canceled = DocumentState.CANCELLED == documentOld.getStateId();
        if (canceled) {
            throw new DocumentNotFinishedException("No está permitido modificar un documento que se encuentra anulado.");
        }
        boolean modified = DocumentState.MODIFIED == documentOld.getStateId();
        if (modified) {
            throw new DocumentNotFinishedException("No está permitido modificar un documento que ya fue modificado.");
        }
        Person person = personService.getBy(request);
        final Document documentNew = this.save(person, workflowId, type, subject, replacements, multipartFiles);
        Documentmodification documentmodification = DocumentmodificationAllocator.build(person, documentNew, documentOld, reasonModification);
        documentmodificationService.save(documentmodification);
        Historicaldocumentmodification historicalmodification;
        Optional<Historicaldocumentmodification> optional = historicaldocumentmodificationService.findBy(documentOld);
        if (optional.isPresent()) {
            Document document4Historical = optional.get().getDocumentByDocumentId();
            historicalmodification = HistoricaldocumentmodificationAllocator.build(document4Historical, documentmodification);
        } else {
            historicalmodification = HistoricaldocumentmodificationAllocator.build(documentOld, documentmodification);
        }
        historicaldocumentmodificationService.save(historicalmodification);
        traceability.save(person, documentNew, DocumentState.PENDING, TraceabilityType.MANDATORY);
        DocumentAllocator.forUpdateWithState(documentOld, DocumentState.MODIFIED);
        this.save(documentOld);
        logger.info(String.format("Document was modified by personId: %d, documentId: %d", person.getId(), documentOld.getId()));
        traceability.save(person, documentOld, DocumentState.MODIFIED, TraceabilityType.MANDATORY);
        return documentNew;
    }
}
