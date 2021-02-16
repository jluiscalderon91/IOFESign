package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.config.ResourceProperties;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.pojo._ReviewInfo;
import com.apsout.electronictestimony.api.entity.model.pojo._Revision;
import com.apsout.electronictestimony.api.exception.AssignerNotFoundException;
import com.apsout.electronictestimony.api.exception.DocumentNotFoundException;
import com.apsout.electronictestimony.api.exception.FileStorageException;
import com.apsout.electronictestimony.api.exception.ResourceNotFoundException;
import com.apsout.electronictestimony.api.repository.ResourceRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.file.Compressor;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.*;
import com.inversionesrc.dsignature.pdf.PdfSigner;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.apsout.electronictestimony.api.util.statics.FileType.ZIP;

@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceRepository repository;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AssignerService assignerService;
    @Autowired
    private DoneService doneService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private StamperService stamperService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private HistoricalHashService historicalHashService;
    @Autowired
    private SienotificationService sienotificationService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private DocumentresourceService documentresourceService;
    @Autowired
    private StamptestfileService stamptestfileService;
    @Autowired
    private StampdatetimeService stampdatetimeService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private StamplayoutfileService stamplayoutfileService;
    @Autowired
    private DocumenttraceabilityService traceability;
    @Autowired
    private PersonService personService;
    @Autowired
    private ServiceweightService serviceweightService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private DetailbalanceallocationService detailbalanceallocationService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ParticipantService participantService;
    private final Path fileStoragePath;

    @Autowired
    public ResourceServiceImpl(ResourceProperties resourceProperties) {
        fileStoragePath = Paths.get(resourceProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(Document document, int orderResource, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long now = Instant.now().getEpochSecond();
        final int SOURCE_FOLDER = 0;
        String directoryTarget = buildDirectoryTargetPath(document, SOURCE_FOLDER, orderResource);
        try {
            return createFile(directoryTarget, file.getInputStream(), file.getSize(), fileName);
        } catch (IOException e) {
            logger.error("Receiving stream file ", e);
            throw new FileStorageException(String.format("Could not receive file %s. Please try again!", fileName), e);
        }
    }

    private String createFile(String directoryTarget, InputStream stream, long size, String fileName) {
        try {
            Path directoryPath = fileStoragePath.resolve(directoryTarget);
            Files.createDirectories(directoryPath);
            Path targetLocation = directoryPath.resolve(fileName);
            long copiedSize = Files.copy(stream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            if (size != -1)
                if (copiedSize != size) {
                    throw new FileStorageException("Sorry! file copy could not be completed " + fileName);
                }
            return new StringBuilder(File.separator).append(directoryTarget).append(File.separator).append(fileName).toString();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private String createFile(String directoryTarget, InputStream stream, String fileName) {
        return createFile(directoryTarget, stream, -1, fileName);
    }

    @Override
    public Resource save(Resource resource) {
        return repository.save(resource);
    }

    public Optional<Resource> finBy(int resourceId) {
        return repository.findById(resourceId);
    }

    // TODO REVIEW
    @Override
    public Optional<Resource> getOriginalResourceBy(int documentId) {
        return repository.getFirstByDocumentIdOrderAsc(documentId);
    }

    @Override
    public Optional<Resource> getOriginalResourceBy(int documentId, int orderResource) {
        return repository.getFirstByDocumentIdOrderAsc(documentId, orderResource);
    }

    @Deprecated
    public org.springframework.core.io.Resource getStreamResourceBy(int documentId) {
        Optional<Done> optionalDone = doneService.getLastBy(documentId);
        Resource resource;
        if (optionalDone.isPresent()) {
            resource = optionalDone.get().getResourceByResourceId();
        } else {
            Optional<Resource> optionalResource = this.getOriginalResourceBy(documentId);
            if (!optionalResource.isPresent()) {
                throw new ResourceNotFoundException("Resource not found for documentId: " + documentId);
            }
            resource = optionalResource.get();
        }
        try {
            Path filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
            org.springframework.core.io.Resource resourceStream = new UrlResource(filePath.toUri());
            if (resourceStream.exists()) {
                return resourceStream;
            } else {
                throw new ResourceNotFoundException("File not found " + resource.getNewName());
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + resource.getNewName(), ex);
        }
    }

    public org.springframework.core.io.Resource getStreamBy(int resourceId) {
        Optional<Resource> optional = repository.findById(resourceId);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Resource not found with resourceId: " + resourceId);
        }
        Resource resource = optional.get();
        Path filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
        return extractStream(filePath);
    }

    /**
     * @param documentId
     * @param hashIdentifier
     * @param scopeResource
     * @return Stream by document and resource, if it is signed, its name has suffix -signed. Examp. abcd-sign.zip, fghi-sign.pdf
     */
    @Override
    public org.springframework.core.io.Resource getStreamBy(int documentId, String hashIdentifier, ScopeResource scopeResource) {
        Document document = documentService.getByNoDeleted(documentId, hashIdentifier);
        if (States.HAS_MULTIPLE_ATTACH == document.getHasMultipleAttachments()) {
            List<Resource> resources = this.findAllBy(documentId);
            List<File> files = this.findAllFilesBy(document, scopeResource);
            String zipname = States.FINISHED == document.getFinished() ? document.getSubject() + "-signed" : document.getSubject();
            Path zipPath = Compressor.zip(files, zipname);
            try {
                return new UrlResource(zipPath.toUri());
            } catch (MalformedURLException e) {
                logger.error(String.format("Returnning zip file for documentId: %d, zipname: %s", documentId, zipPath.getFileName()));
                throw new ResourceNotFoundException(String.format("Returnning zip file for documentId: %d, zipname: %s", documentId, zipPath.getFileName()));
            }
        }
        Optional<Done> optional = doneService.getLastBy(documentId);
        Resource resource;
        if (optional.isPresent()) {
            logger.info(String.format("Resource get via Done with idDone: %d", optional.get().getId()));
            resource = optional.get().getResourceByResourceId();
        } else {
            Optional<Resource> optionalResource = repository.getResourceByDocumentIdAndHashIdentifier(documentId, hashIdentifier);
            if (!optionalResource.isPresent()) {
                throw new ResourceNotFoundException(String.format("Resource not found with documentId: %d, hashIdentifier: %s", documentId, hashIdentifier));
            }
            logger.info(String.format("Resource get via Document with idDocument: %d", documentId));
            resource = optionalResource.get();
        }
        Path filePath;
        switch (scopeResource) {
            case PUBLIC:
                if (optional.isPresent()) {
                    filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                } else {
                    Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
                    Workflow workflow = document.getWorkflowByWorkflowId();
                    filePath = stamperService.stamp(enterprise, workflow, resource);
                }
                break;
            default:
                filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                break;
        }
        if (States.FINISHED == document.getFinished()) {
            String filename = filePath.toFile().getName();
            String basename = FilenameUtils.getBaseName(filename);
            String fileExtension = FilenameUtils.getExtension(filename);
            Path newfilePath = FileUtil.createPathOnTemp(basename + "-sign." + fileExtension);
            try {
                Files.copy(filePath, newfilePath);
            } catch (IOException e) {
                logger.error("Building file to download", e);
                throw new RuntimeException("Problemas al construir el archivo firmado.");
            }
            return extractStream(newfilePath);
        }
        return extractStream(filePath);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public org.springframework.core.io.Resource getStreamBy(int documentId, String hashIdentifier, ScopeResource scopeResource, HttpServletRequest request) {
        final org.springframework.core.io.Resource stream = getStreamBy(documentId, hashIdentifier, scopeResource);
        Person person = personService.getBy(request);
        traceability.save(person, documentId, DocumentState.DOWNLOADED, TraceabilityType.MANDATORY);
        return stream;
    }

    public org.springframework.core.io.Resource getStreamOutsideBy(int documentId, String hashIdentifier, ScopeResource scopeResource) {
        final org.springframework.core.io.Resource streamBy = getStreamBy(documentId, hashIdentifier, scopeResource);
        Document document = documentService.getByNoDeleted(documentId);
        if (DocumentState.FINISHED == document.getStateId() && States.FINISHED == document.getFinished()) {
            DocumentAllocator.forUpdateWithState(document, DocumentState.DELIVERED);
            documentService.save(document);
        }
        if (DocumentState.PENDING == document.getStateId()) {
            traceability.save(Default.OUTSIDE_PERSON, documentId, DocumentState.DOWNLOADED, States.INVISIBLE, TraceabilityType.SIGNATURE_COMPONENT);
        } else {
            traceability.save(Default.OUTSIDE_PERSON, documentId, DocumentState.DOWNLOADED, TraceabilityType.MANDATORY);
        }
        return streamBy;
    }

    /***
     * Get all attachment files for a document
     * @param document
     * @param scopeResource
     * @return List of attachment files
     */
    public List<File> findAllFilesBy(Document document, ScopeResource scopeResource) {
        List<Resource> resources = this.findAllBy(document.getId());
        return resources.stream().map(resource -> {
            Optional<Done> optionalDone = doneService.getLastBy(document.getId(), resource.getOrderResource());
            Resource resourceX;
            if (optionalDone.isPresent()) {
                logger.info(String.format("Resource get via Done with idDone: %d", optionalDone.get().getId()));
                resourceX = optionalDone.get().getResourceByResourceId();
            } else {
                Optional<Resource> optionalResource = repository.getResourceByDocumentIdAndHashIdentifier(document.getId(), document.getHashIdentifier(), resource.getOrderResource());
                if (!optionalResource.isPresent()) {
                    throw new ResourceNotFoundException(String.format("Resource not found with documentId: %d, hashIdentifier: %s", document.getId(), document.getHashIdentifier()));
                }
                logger.info(String.format("Resource get via Document with idDocument: %d", document.getId()));
                resourceX = optionalResource.get();
            }
            Path filePath;
            switch (scopeResource) {
                case PUBLIC:
                    if (optionalDone.isPresent()) {
                        filePath = this.fileStoragePath.resolve(resourceX.getPath()).normalize();
                    } else {
                        Document documentdb = documentService.getBy(document.getId());
                        Enterprise enterprise = documentdb.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
                        Workflow workflow = documentdb.getWorkflowByWorkflowId();
                        filePath = stamperService.stamp(enterprise, workflow, resource);
                    }
                    break;
                default:
                    filePath = this.fileStoragePath.resolve(resourceX.getPath()).normalize();
                    break;
            }
            return filePath.toFile();
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public org.springframework.core.io.Resource getStreamBy2(int personId, int documentId, String hashIdentifier, int orderResource, ScopeResource scopeResource) {
        return getStreamByWithStamps(personId, documentId, hashIdentifier, orderResource, scopeResource, true);
    }

    /**
     * @param documentId
     * @param hashIdentifier
     * @param orderResource
     * @param scopeResource
     * @param addGraphicElements Indica si se agregarán los elementos gráficos adicionales (false: cuando se trate de visualizar en la trazabilidad)
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public org.springframework.core.io.Resource getStreamByWithStamps(int personId, int documentId, String hashIdentifier, int orderResource, ScopeResource scopeResource, boolean addGraphicElements) {
        Optional<Done> optionalDone = doneService.getLastBy(documentId, orderResource);
        Resource resource;
        if (optionalDone.isPresent()) {
            logger.info(String.format("Resource get via Done with idDone: %d", optionalDone.get().getId()));
            resource = optionalDone.get().getResourceByResourceId();
        } else {
            Optional<Resource> optionalResource = repository.getResourceByDocumentIdAndHashIdentifier(documentId, hashIdentifier, orderResource);
            if (!optionalResource.isPresent()) {
                throw new ResourceNotFoundException(String.format("Resource not found with documentId: %d, hashIdentifier: %s, order: %d", documentId, hashIdentifier, orderResource));
            }
            logger.info(String.format("Resource get via Document with idDocument: %d, order: %d", documentId, orderResource));
            resource = optionalResource.get();
        }
        Path filePath;
        switch (scopeResource) {
            case PUBLIC:
            case PRIVATE:
                Document document = documentService.getBy(documentId);
                Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
                Workflow workflow = document.getWorkflowByWorkflowId();
                if (document.getClosedStamping() || States.FINISHED == document.getFinished() || personId == 0) {
                    filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                } else {
                    if (addGraphicElements) {
                        filePath = this.stampGraphics(personId, document, resource);
                    } else {
                        filePath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                    }
                }
                break;
            default:
                throw new RuntimeException("Ámbito de solicitud de recurso inexistente");
        }
        final org.springframework.core.io.Resource resource2Return = extractStream(filePath);
        if (ScopeResource.PUBLIC == scopeResource) {
            traceability.save(Default.SIGNATURE_COMPONENT, documentId, DocumentState.DOWNLOADED, States.INVISIBLE, TraceabilityType.SIGNATURE_COMPONENT);
        }
        return resource2Return;
    }

    private Path stampGraphics(int personId, Document document, Resource resource) {
        Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
        Workflow workflow = document.getWorkflowByWorkflowId();
        Operator operator = operatorService.getNextConcreteOperatorBy(enterprise, document);
        if (!operator.getPersonId().equals(personId)) {
            throw new DocumentNotFoundException(String.format("Document not found for personId: %d", personId));
        }
        final Boolean isDigitalSignature = operator.getDigitalSignature();
        if (isDigitalSignature) {
            if (Default.MIN_ORDER_OPERATION == operator.getOrderOperation()) {
                return stamperService.stampAllElements(enterprise, workflow, resource, null, false);
            } else {
                final Boolean closedStamping = document.getClosedStamping();
                if (closedStamping) {
                    return this.fileStoragePath.resolve(resource.getPath()).normalize();
                } else {
                    Path srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                    int orderOperation = operator.getOrderOperation();
                    List<Participant> participants = participantService.getAllBy(workflow)
                            .stream()
                            .filter(participant -> participant.getOrderParticipant() >= orderOperation)
                            .collect(Collectors.toList());
                    for (Participant participant : participants) {
                        srcPath = stamperService.stampRubric(document, participant, srcPath, false);
                    }
                    return srcPath;
                }
            }
        } else {
            final Boolean closedStamping = document.getClosedStamping();
            if (closedStamping) {
                return this.fileStoragePath.resolve(resource.getPath()).normalize();
            } else {
                Path srcPath = null;
                if (Default.MIN_ORDER_OPERATION == operator.getOrderOperation()) {
                    srcPath = stamperService.stampBeforeRubric(enterprise, workflow, document, resource, null, false);
                } else {
                    srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
                }
                int orderOperation = operator.getOrderOperation();
                Participant participant = participantService.getBy(workflow.getId(), orderOperation);
                srcPath = stamperService.stampRubric(document, participant, srcPath, false);
                return srcPath;
            }
        }
    }

    private org.springframework.core.io.Resource extractStream(Path filePath) {
        try {
            org.springframework.core.io.Resource resourceStream = new UrlResource(filePath.toUri());
            if (resourceStream.exists()) {
                return resourceStream;
            } else {
                throw new ResourceNotFoundException("File not found " + filePath.getFileName());
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + filePath.getFileName(), ex);
        }
    }

    @Override
    @Transactional(rollbackFor = FileStorageException.class)
    public Resource save(int personId, int subOperationType, int documentId, String hashIdentifier, int orderResource, InputStream stream, boolean willBeClosedStamping) {
        return this.save(personId, OperationType.SIGN, subOperationType, documentId, hashIdentifier, orderResource, stream, States.NOT_OBSERVED, null, willBeClosedStamping);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resource save(int personId,
                         int operationType,
                         int subOperationType,
                         int documentId,
                         String hashIdentifier,
                         int orderResource,
                         InputStream stream,
                         byte observed,
                         String comment,
                         boolean willBeClosedStamping) {
        Assigner actualAssigner = assignerService.getBy(personId, documentId);
        if (actualAssigner.getCompleted() == States.COMPLETED) {
            throw new AssignerNotFoundException(String.format("Assignment was completed previously by personId: %d, documentId: %d", personId, documentId));
        }
        Optional<Document> optionalDocument = documentService.findBy(documentId);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            if (hashIdentifier.equals(document.getHashIdentifier())) {
                Optional<Resource> optionalOriginalResource = this.getOriginalResourceBy(documentId, orderResource);
                if (optionalOriginalResource.isPresent()) {
                    Resource originalResource = optionalOriginalResource.get();
                    String fileName = StringUtils.cleanPath(originalResource.getOriginalName());
                    String directoryTarget = buildDirectoryTargetPath(document, actualAssigner.getOrderOperation(), orderResource);
                    Operator actualOperator = actualAssigner.getOperatorByOperatorId();
                    final boolean allowedAddTsa = isAllowedAddTsa(actualOperator);
                    if (allowedAddTsa) {
                        stream = addTimeStamping(document, stream);
                    }
                    String path = createFile(directoryTarget, stream, fileName);
                    Resource newResource = ResourceAllocator.build(originalResource, path.replaceFirst(Pattern.quote(File.separator), ""), orderResource);
                    repository.save(newResource);
                    // Reasign a new operator and mark assigner entity as signed
                    boolean isLastResource = isLastResourceOf(document, orderResource);
                    if (isLastResource) {
                        actualAssigner.setCompleted(States.COMPLETED);
                        assignerService.save(actualAssigner);
                        Enterprise enterprise = actualOperator.getEnterpriseByEnterpriseId();
                        Optional<Operator> nextOptionalOperator = operatorService.findNextBy(enterprise, document);
                        if (OperationType.SIGN == operationType) {
                            if (ElectronicSignature.ANYTHING == subOperationType) {
                                traceability.save(personId, documentId, DocumentState.SIGNED, TraceabilityType.MANDATORY);
                            } else if (ElectronicSignature.GRAPHIC_SIGNATURE == subOperationType) {
                                traceability.save(personId, documentId, DocumentState.ES_GRAPHIC_SIGNATURE, TraceabilityType.MANDATORY);
                            }
                        } else if (OperationType.REVIEW == operationType) {
                            traceability.save(personId, documentId, DocumentState.REVIEWED, TraceabilityType.MANDATORY);
                            if (States.OBSERVED == observed) {
                                traceability.save(personId, documentId, DocumentState.OBSERVED, TraceabilityType.MANDATORY);
                            }
                        }
                        if (nextOptionalOperator.isPresent()) {
                            final Operator nextOperator = nextOptionalOperator.get();
                            Assigner newAssigner = AssignerAllocator.build(document, nextOperator, actualAssigner);
                            assignerService.save(newAssigner);
                            // Send alert notification (electronic mail) to next operator
                            Notification notification;
                            if (States.SEND_ALERT == nextOperator.getSendAlert()) {
                                notification = NotificationAllocator.build4MarkAsNotSent(nextOperator);
                            } else {
                                notification = NotificationAllocator.build4MarkAsSent(nextOperator);
                            }
                            notificationService.save(notification);
                        } else {
                            documentService.endProcess(document);
                            this.prepare2Delivery(document);
                            traceability.save(personId, documentId, DocumentState.FINISHED, TraceabilityType.MANDATORY);
                        }
                        String newHashIdentifier = documentService.buildHashIdentifier(document.getSubject());
                        DocumentAllocator.forUpdate(document, newHashIdentifier, willBeClosedStamping);
                        documentService.save(document);
                        Historicalhash historicalhash = HistoricalhashAllocator.build(document);
                        historicalHashService.save(historicalhash);
                        notificationService.invalidateBy(actualOperator);
                        // TODO Registramos la programación de la notificación al SIE
                        if (States.SEND_SIE_NOTIFICATION == actualOperator.getSendNotification()) {
                            prepareAndScheduleSienotification(actualOperator);
                        }
                    }
                    // TODO Registramos que la firma del documento se completó satisfactoriamente
                    Done done = DoneAllocator.build(actualAssigner, newResource, observed, comment, allowedAddTsa);
                    doneService.save(done);
                    Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseId();
                    if (OperationType.SIGN == operationType) {
                        if (ElectronicSignature.ANYTHING == subOperationType) {
                            headbalanceallocationService.consumeBalance(enterprise, document, new Person(personId), com.apsout.electronictestimony.api.util.enums.Service.DIGITAL_SIGNATURE);
                        } else if (ElectronicSignature.GRAPHIC_SIGNATURE == subOperationType) {
                            headbalanceallocationService.consumeBalance(enterprise, document, new Person(personId), com.apsout.electronictestimony.api.util.enums.Service.ELECTRONIC_SIGNATURE);
                        }
                        if (allowedAddTsa) {
                            headbalanceallocationService.consumeBalance(enterprise, document, new Person(personId), com.apsout.electronictestimony.api.util.enums.Service.TIMESTAMPING);
                        }
                    } else if (OperationType.REVIEW == operationType) {
                        headbalanceallocationService.consumeBalance(enterprise, document, new Person(personId), com.apsout.electronictestimony.api.util.enums.Service.REVIEW);
                    }
                    return newResource;
                }
            }
        }
        throw new DocumentNotFoundException(String.format("Document not found by personId: %d, documentId: %d", personId, documentId));
    }

    private void prepare2Delivery(Document document) {
        if (States.NOT_DELIVER == document.getWorkflowByWorkflowId().getDeliver()) {
            return;
        }
        Delivery delivery = DeliveryAllocator.build(document);
        deliveryService.save(delivery);
    }

    private void prepareAndScheduleSienotification(Operator operator) {
        Sienotification sienotification = SienotificationAllocator.build4MarkAsNotSent(operator);
        sienotificationService.save(sienotification);
    }

    private boolean isLastResourceOf(Document document, int orderResource) {
        final Resource resourceOfMaxOrder = getMaximumOrderBy(document);
        return orderResource == resourceOfMaxOrder.getOrderResource();
    }

    private boolean isAllowedAddTsa(Operator operator) {
        return OperationType.SIGN == operator.getOperationId() && States.ADD_TSA == operator.getAddTsa();
    }

    private InputStream addTimeStamping(Document document, InputStream stream) {
        try {
            logger.info(String.format("Starting to add TSA for documentId: %d  and subject: %s", document.getId(), document.getSubject()));
            Path pathWithoutTsa = Files.createTempFile("withouttsa-", ".pdf");
            File fileWithoutTsa = pathWithoutTsa.toFile();
            Path pathWithTsa = Files.createTempFile("withtsa-", ".pdf");
            File fileWithTsa = pathWithTsa.toFile();
            createFile(pathWithoutTsa.getParent().toString(), stream, fileWithoutTsa.getName());
            PdfSigner.timestampLastSignature(fileWithoutTsa, fileWithTsa, Global.TSA_URL, Global.TSA_USERNAME, Global.TSA_PASSWORD);
            logger.info(String.format("TSA added for documentId: %d", document.getId()));
            return new FileInputStream(fileWithTsa);
        } catch (IOException e) {
            logger.error(String.format("Addign TSA to documentId: %d", document.getId()), e);
            throw new RuntimeException(e);
        }
    }

    public org.springframework.core.io.Resource getStreamBy(String hashIdentifier, ScopeResource scopeResource) {
        Historicalhash historicalhash = historicalHashService.getBy(hashIdentifier);
        Document document = historicalhash.getDocumentByDocumentId();
        return this.getStreamBy(document.getId(), hashIdentifier, scopeResource);
    }

    public org.springframework.core.io.Resource getStreamBy2(int personId, String hashIdentifier, String hashResource, ScopeResource scopeResource) {
        Historicalhash historicalhash = historicalHashService.getBy(hashIdentifier);
        Document document = historicalhash.getDocumentByDocumentId();
        Resource resourceMaxOrder = getMaximumOrderBy(document, hashResource);
        return this.getStreamBy2(personId, document.getId(), hashIdentifier, resourceMaxOrder.getOrderResource(), scopeResource);
    }

    private String buildDirectoryTargetPath(Document document, Integer orderSign, int orderResource) {
        return new StringBuilder("e-")
                .append(document.getId())
                .append(File.separator)
                .append(orderSign)
                .append(File.separator)
                .append(orderResource).toString();
    }

    public org.springframework.core.io.Resource getCsvStreamBy(String uuid) {
        List<Integer> identifiers = applicationService.getIdentifiers(uuid);
        if (identifiers.isEmpty()) {
            return null;
        }
        int personId = applicationService.getPersonIdBy(uuid);
        List<Document> documents = documentService.getBy(identifiers, personId);
        File file = generateCsvFile(documents, personId);
        try {
            org.springframework.core.io.Resource resourceStream = new UrlResource(file.toURI());
            if (resourceStream.exists()) {
                return resourceStream;
            } else {
                throw new ResourceNotFoundException(String.format("File temp not found with uuid: %s", uuid));
            }
        } catch (MalformedURLException e) {
            logger.error(String.format("Malformed URL for temp file with uuid: %s", uuid), e);
            throw new ResourceNotFoundException(String.format("Malformed URL for temp file with uuid: %s", uuid));
        }
    }

    private File generateCsvFile(List<Document> documents, int personId) {
        Path csvPath;
        try {
            csvPath = Files.createTempFile("csvFile", ".csv");
        } catch (IOException e) {
            logger.error("Creating csv file", e);
            throw new RuntimeException(e);
        }
        File csvFile = csvPath.toFile();
        try (FileWriter csvFileWriter = new FileWriter(csvFile)) {
            documents.stream().forEach(document -> {
                List<Resource> resources = this.findAllBy(document);
                resources.stream().forEach(resource -> {
                    try {
                        csvFileWriter.append(getURLRowCsv(personId, document, resource));
                    } catch (IOException e) {
                        logger.error(String.format("Writing temp file with document identifier: %d", document.getId()), e);
                    }
                });
            });
            csvFileWriter.flush();
            logger.error(String.format("Csv file filled for personId: %d", personId));
            return csvFile;
        } catch (IOException e) {
            logger.error("Filling csv file", e);
            throw new RuntimeException(e);
        }
    }

    private String getURLRowCsv(int personId, Document document, Resource resource) {
        Workflow workflow = document.getWorkflowByWorkflowId();
        Optional<Stampdatetime> optional = stampdatetimeService.findFirstBy(workflow);
        String from = getURLDownload(personId, document, resource);
        String to = getURLUpload(personId, document, resource);
        boolean haveFirstSignature = documentService.haveFirstSignature(document);
        if (optional.isPresent() && !haveFirstSignature) {
            Stampdatetime stampdatetime = optional.get();
            int stampOn = documentService.getStampOnBy(document, resource, stampdatetime);
            return new StringBuilder(from)
                    .append(",")
                    .append(to)
                    .append(",")
                    .append(resource.getOriginalName())
                    .append(",")
                    .append(stampdatetime.getPositionX().intValue())
                    .append(",")
                    .append(stampdatetime.getPositionY().intValue())
                    .append(",")
                    .append(stampdatetime.getWidthContainer().intValue())
                    .append(",")
                    .append(stampdatetime.getHeightContainer().intValue())
                    .append(",")
                    .append(stampdatetime.getDescription())
                    .append(",")
                    .append(stampOn)
                    .append("\n").toString();
        }
        return new StringBuilder(from)
                .append(",")
                .append(to)
                .append(",")
                .append(resource.getOriginalName())
                .append("\n").toString();
    }

    private String getURLDownload(int personId, Document document, Resource resource) {
        return new StringBuilder(Global.ROOT_API_V1)
                .append("/public/people/")
                .append(personId)
                .append("/documents/")
                .append(document.getId())
                .append("/")
                .append(document.getHashIdentifier())
                .append("/resources/")
                .append(resource.getOrderResource())
                .append("/stream").toString();
    }

    private String getURLUpload(int personId, Document document, Resource resource) {
        boolean willBeClosedStamping = documentService.willBeClosedStamping(personId, document);
        return new StringBuilder(Global.ROOT_API_V1)
                .append("/public/people/")
                .append(personId)
                .append("/documents/")
                .append(document.getId())
                .append("/")
                .append(document.getHashIdentifier())
                .append("/resources/")
                .append(resource.getOrderResource())
                .append("/stream/")
                .append(willBeClosedStamping)
                .append("/closedStamping").toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(int personId, int documentId, String hashIdentifier, byte observed, byte cancelled, String comment, boolean willBeClosedStamping, HttpServletRequest request) {
        if (States.CANCELLED == cancelled) {
            Person person = personService.getBy(request);
            Document document = documentService.getBy(documentId);
            traceability.save(person, document, DocumentState.REVIEWED, TraceabilityType.MANDATORY);
            documentService.cancel(person, document, comment);
        } else {
            List<Resource> resources = this.findAllBy(documentId);
            resources.stream().map(resource -> {
                //TODO Agregado personId
                org.springframework.core.io.Resource streamResource = this.getStreamBy2(personId, documentId, hashIdentifier, resource.getOrderResource(), ScopeResource.PRIVATE);
                try {
                    InputStream inputStream = streamResource.getInputStream();
                    Resource resource2 = this.save(personId, OperationType.REVIEW, ElectronicSignature.ANYTHING, documentId, hashIdentifier, resource.getOrderResource(), inputStream, observed, comment == null ? "" : comment, willBeClosedStamping);
                    logger.info(String.format("Received stream file with documentId: %d, hashIdentifier: %s, personId: %d, orderResource: %d ", documentId, hashIdentifier, personId, resource.getOrderResource()));
                    return resource2;
                } catch (IOException e) {
                    logger.error("Saving revision of file", e);
                }
                throw new ResourceNotFoundException(String.format("Stream resource not found by personId: %d, documentId: %d"));
            }).collect(Collectors.toList());
        }
    }

    public List<Resource> findAllBy(Document document) {
        return repository.findAllByDocumentId(document.getId());
    }

    public List<Resource> findAllBy(int documentId) {
        Document document = documentService.getByNoDeleted(documentId);
        return this.findAllBy(document);
    }

    public Resource getMaximumOrderBy(Document document) {
        return repository.getFirstByDocumentIdOrderDesc(document.getId());
    }

    public Resource getMaximumOrderBy(Document document, String hashResource) {
        return repository.getFirstByDocumentIdAndHashResourceOrderDesc(document.getId(), hashResource);
    }

    public Resource getBy(int resourceId) {
        Optional<Resource> optional = this.finBy(resourceId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ResourceNotFoundException(String.format("Resource not found by resourceId: %d", resourceId));
    }

    public org.springframework.core.io.Resource getStreamBy(String docIdentifiers) {
        List<Integer> identifiers = StringUtil.split2Integers(docIdentifiers, ",");
        List<Document> documents = documentService.getBy(identifiers);
        List<File> paths = documents.stream().map(document -> {
            if (States.FINISHED == document.getFinished()) {
                org.springframework.core.io.Resource resource = this.getStreamBy(document.getId(), document.getHashIdentifier(), ScopeResource.PRIVATE);
                try {
                    final File file = resource.getFile();
                    String fileExtension = FilenameUtils.getExtension(file.getName());
                    if (ZIP.equals(fileExtension)) {
                        return file;
                    }
                    // Change pdf name with subject-sign.pdf
                    Path path = FileUtil.createPathOnTemp(document.getSubject() + "-signed.pdf");
                    Files.copy(file.toPath(), path);
                    return path.toFile();
                } catch (IOException e) {
                    logger.error("Building multiple file downloads", e);
                    throw new RuntimeException("Problemas al construir el grupo de archivos firmados.");
                }
            }
            return null;
        }).filter(file -> file != null).collect(Collectors.toList());
        Path zipPath = Compressor.zip(paths);
        try {
            return new UrlResource(zipPath.toUri());
        } catch (MalformedURLException e) {
            logger.error(String.format("Returnning zip file for document identifiers: %s", zipPath.getFileName()));
            throw new ResourceNotFoundException(String.format("Returnning zip file for document identifiers: %s, zipname: %s", docIdentifiers, zipPath.getFileName()));
        }
    }

    @Override
    public org.springframework.core.io.Resource getStreamTemplateBy(int workflowId) {
        Workflow workflow = workflowService.getBy(workflowId);
        Enterprise enterprise = workflow.getEnterpriseByEnterpriseId();
        Optional<Stamptestfile> optionalStamptestfile = stamptestfileService.findBy(workflow);
        Optional<Stamplayoutfile> optionalStamplayoutfile = stamplayoutfileService.findBy(workflow);
        Path template;
        if (optionalStamplayoutfile.isPresent()) {
            Stamplayoutfile stamplayoutfile = optionalStamplayoutfile.get();
            byte[] data = stamplayoutfile.getData();
            byte[] excelData = stamplayoutfile.getExcelData();
            if (data == null) {
                throw new RuntimeException("Es necesario el archivo plantilla .jasper");
            }
            if (excelData == null) {
                throw new RuntimeException("Es necesario el archivo fuente de datos");
            }
            template = stamplayoutfileService.buildUniqueFile(stamplayoutfile).toPath();
        } else if (optionalStamptestfile.isPresent()) {
            Stamptestfile stamptestfile = optionalStamptestfile.get();
            File testFile = FileUtil.write2NewFile(stamptestfile.getData(), "template-", ".pdf");
            template = testFile.toPath();
        } else {
            template = FileUtil.copyInputStreamOfResources("document-test-4-sign.pdf");
        }
        Path filePath = stamperService.stampAllElements(enterprise, workflow, null, template, true);
        return extractStream(filePath);
    }

    @Override
    public Optional<Resource> findBy(String resumeHash) {
        return repository.findBy(resumeHash);
    }

    public Resource getBy(String resumeHash) {
        Optional<Resource> optional = this.findBy(resumeHash);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ResourceNotFoundException(String.format("Resource not found by resumeHash: %s", resumeHash));
    }

    public org.springframework.core.io.Resource getStreamByResumeHash(String resumeHashResource) {
        Optional<Resource> optional = this.findBy(resumeHashResource);
        if (optional.isPresent()) {
            Resource resource = optional.get();
            Documentresource documentresource = documentresourceService.getBy(resource);
            Document document = documentresource.getDocumentByDocumentId();
            Resource resourceMaxOrder = getMaximumOrderBy(document, resource.getHash());
            //TODO Revisar personID = 0
            return this.getStreamBy2(0, document.getId(), document.getHashIdentifier(), resourceMaxOrder.getOrderResource(), ScopeResource.PUBLIC);
        } else {
            throw new ResourceNotFoundException(String.format("Recurso no encontrado con el código de verificación: %s", resumeHashResource));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(int personId, _Revision revision, boolean willBeClosedStamping, HttpServletRequest request) {
        List<_ReviewInfo> reviewInfos = revision.getReviewInfos();
        reviewInfos.stream().forEach(reviewInfo -> {
            int documentId = reviewInfo.getDocumentId();
            String hashIdentifier = reviewInfo.getHashIdentifier();
            Document document = documentService.getBy(documentId, hashIdentifier);
            byte observed = reviewInfo.getObserved();
            byte cancelled = reviewInfo.getCancelled();
            String comment = reviewInfo.getComment();
            review(personId, documentId, hashIdentifier, observed, cancelled, comment, willBeClosedStamping, request);
        });
    }

    public Optional<Resource> findFirstBy(Document document) {
        return this.findAllBy(document).stream().findFirst();
    }

    public String buildURLVerifier(Document document, Resource resource) {
        return new StringBuilder(Global.ROOT_FRONT)
                .append("/document/")
                .append(document.getHashIdentifier())
                .append("/resources/")
                .append(resource.getHash())
                .append("/verifier").toString();
    }

    public String buildURLVerifier4FirstResource(Document document) {
        List<Resource> resources = this.findAllBy(document);
        if (resources.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Resource not found by documentId: %d", document.getId()));
        }
        return buildURLVerifier(document, resources.get(0));
    }
}
