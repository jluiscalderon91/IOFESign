package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.AssignerModel;
import com.apsout.electronictestimony.api.entity.model.DocumentModel;
import com.apsout.electronictestimony.api.entity.model.PasswordretrieverModel;
import com.apsout.electronictestimony.api.entity.model.PersonModel;
import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;
import com.apsout.electronictestimony.api.modelassembler.AssignerModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.DocumentModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.PasswordretrieverModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.OutsideDocumentModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.PersonModelAssembler;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.ElectronicSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private PersonService personService;
    @Autowired
    private AssignerService assignerService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PasswordretrieverService passwordretrieverService;
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/public/documents/{documentId}/{hashIdentifier}/resources/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamByPublic(@PathVariable int documentId,
                                                                                  @PathVariable String hashIdentifier,
                                                                                  HttpServletRequest request) {
        return getStreamBy(documentId, hashIdentifier, ScopeResource.PUBLIC, request);
    }


    @GetMapping("/public/documents/{documentId}/{hashIdentifier}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamByPrivate(@PathVariable int documentId,
                                                                                   @PathVariable String hashIdentifier,
                                                                                   HttpServletRequest request) {
        org.springframework.core.io.Resource resource = resourceService.getStreamBy(documentId, hashIdentifier, ScopeResource.PRIVATE, request);
        String nameFileDownload = StringUtil.decode(resource.getFilename(), "ISO8859_1");
        return extractOctecStream(request, resource, nameFileDownload);
    }

    @GetMapping("/public/outside/documents/{documentId}/{hashIdentifier}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamByOutside(@PathVariable int documentId,
                                                                                   @PathVariable String hashIdentifier,
                                                                                   HttpServletRequest request) {
        org.springframework.core.io.Resource resource = resourceService.getStreamOutsideBy(documentId, hashIdentifier, ScopeResource.PRIVATE);
        String nameFileDownload = StringUtil.decode(resource.getFilename(), "ISO8859_1");
        return extractOctecStream(request, resource, nameFileDownload);
    }

    private ResponseEntity<org.springframework.core.io.Resource> getStreamBy(int documentId,
                                                                             String hashIdentifier,
                                                                             ScopeResource scopeResource,
                                                                             HttpServletRequest request) {
        org.springframework.core.io.Resource resource = resourceService.getStreamBy(documentId, hashIdentifier, scopeResource);
        String nameFileDownload = StringUtil.decode(resource.getFilename(), "ISO8859_1");
        return extractOctecStream(request, resource, nameFileDownload);
    }

//    @GetMapping("/public/documents/{documentId}/{hashIdentifier}/resources/{orderResource}/stream")
//    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy2(@PathVariable int documentId,
//                                                                             @PathVariable String hashIdentifier,
//                                                                             @PathVariable int orderResource,
//                                                                             HttpServletRequest request) {
//        // TODO Solicitud hecha por las URL's del componente de firma
//        //TODO REVIEW PERSON ID
//        org.springframework.core.io.Resource resource = resourceService.getStreamBy2(0, documentId, hashIdentifier, orderResource, ScopeResource.PUBLIC);
//        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf('-') + 1);
//        return extractOctecStream(request, resource, nameFileDownload);
//    }

    //TODO  Como en anterior pero, agregado el campo persona
    @GetMapping("/public/people/{personId}/documents/{documentId}/{hashIdentifier}/resources/{orderResource}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy5(@PathVariable int documentId,
                                                                             @PathVariable String hashIdentifier,
                                                                             @PathVariable int personId,
                                                                             @PathVariable int orderResource,
                                                                             HttpServletRequest request) {
        // TODO Solicitud hecha por las URL's del componente de firma
        org.springframework.core.io.Resource resource = resourceService.getStreamBy2(personId, documentId, hashIdentifier, orderResource, ScopeResource.PUBLIC);
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf('-') + 1);
        return extractOctecStream(request, resource, nameFileDownload);
    }

    @GetMapping("/public/people/{personId}/documents/{documentId}/{hashIdentifier}/resources/{orderResource}/private/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy3(@PathVariable int documentId,
                                                                             @PathVariable int personId,
                                                                             @PathVariable String hashIdentifier,
                                                                             @PathVariable int orderResource,
                                                                             HttpServletRequest request) {
        // TODO Solicitud hecha por un usario INVITADO
        //TODO REVIEW PERSONID
        org.springframework.core.io.Resource resource = resourceService.getStreamBy2(personId, documentId, hashIdentifier, orderResource, ScopeResource.PRIVATE);
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf('-') + 1);
        return extractOctecStream(request, resource, nameFileDownload);
    }

    /**
     * Endpoint usado para obtener el Stream del documento a ser visualizado cuando no hay firmas
     *
     * @param documentId
     * @param hashIdentifier
     * @param orderResource
     * @param request
     * @return
     */
    @GetMapping("/public/documents/{documentId}/{hashIdentifier}/resources/{orderResource}/private/stream/traceability")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy4(@PathVariable int documentId,
                                                                             @PathVariable String hashIdentifier,
                                                                             @PathVariable int orderResource,
                                                                             HttpServletRequest request) {
        org.springframework.core.io.Resource resource = resourceService.getStreamByWithStamps(0, documentId, hashIdentifier, orderResource, ScopeResource.PRIVATE, false);
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf('-') + 1);
        return extractOctecStream(request, resource, nameFileDownload);
    }

    @PostMapping(value = "/public/people/{personId}/documents/{documentId}/{hashIdentifier}/resources/{orderResource}/stream/{closedStamping}/closedStamping")
    public ResponseEntity<Resource> uploadStreamBy(@PathVariable int personId,
                                                   @PathVariable int documentId,
                                                   @PathVariable String hashIdentifier,
                                                   @PathVariable int orderResource,
                                                   @PathVariable boolean closedStamping,
                                                   HttpServletRequest request) {
        Resource resource = null;
        try {
            resource = resourceService.save(personId, ElectronicSignature.ANYTHING, documentId, hashIdentifier, orderResource, request.getInputStream(), closedStamping);
            logger.info(String.format("Received stream file with documentId: %d, hashIdentifier: %s, personId: %d, orderResource: %d", documentId, hashIdentifier, personId, orderResource));
        } catch (IOException e) {
            logger.error("Receiving file stream ", e);
        }
        return ResponseEntity.ok().body(resource);
    }

    @GetMapping("/public/signs/batch/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy(@RequestParam(value = "uuid") String uuid) {
        org.springframework.core.io.Resource resource = resourceService.getCsvStreamBy(uuid);
        String contentType = "application/octet-stream";
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }

    @GetMapping("/public/documents/{hashIdentifier}/verifier")
    public ResponseEntity<HashMap<String, Object>> getSignersBy(@PathVariable String hashIdentifier) {
        _Embedded _embedded = documentService.getVerificationInfoBy(hashIdentifier);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/public/documents/{hashIdentifier}/resources/{hashResource}/verifier")
    public ResponseEntity<HashMap<String, Object>> getSigners2By(@PathVariable String hashIdentifier,
                                                                 @PathVariable String hashResource) {
        _Embedded _embedded = documentService.getVerificationInfoBy(hashIdentifier, hashResource);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/public/documents/{hashIdentifier}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy(@PathVariable String hashIdentifier,
                                                                            HttpServletRequest request) throws IOException {

        org.springframework.core.io.Resource resource = resourceService.getStreamBy(hashIdentifier, ScopeResource.PUBLIC);
        return extractPdfStream(request, resource);
    }

    @GetMapping("/public/documents/{hashIdentifier}/resources/{hashResource}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy2(@PathVariable String hashIdentifier,
                                                                             @PathVariable String hashResource,
                                                                             HttpServletRequest request) throws IOException {
        //TODO personId = 0
        org.springframework.core.io.Resource resource = resourceService.getStreamBy2(0, hashIdentifier, hashResource, ScopeResource.PUBLIC);
        return extractPdfStream(request, resource);
    }

    @GetMapping("/public/documents/{hashIdentifier}/resources/{hashResource}/private/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBy3(@PathVariable String hashIdentifier,
                                                                             @PathVariable String hashResource,
                                                                             HttpServletRequest request) throws IOException {
        //TODO personId = 0
        org.springframework.core.io.Resource resource = resourceService.getStreamBy2(0, hashIdentifier, hashResource, ScopeResource.PRIVATE);
        return extractPdfStream(request, resource);
    }

    @GetMapping(value = "/public/people/{personId}/hashIdentifier/{hashIdentifier}/documents")
    public ResponseEntity<DocumentModel> getBy(@PathVariable int personId,
                                               @PathVariable String hashIdentifier) {
        Document document = documentService.getDocumentInfoBy(personId, hashIdentifier);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }

    @GetMapping(value = "/public/documents/{hashIdentifier}/people/{personId}")
    public ResponseEntity<PersonModel> getBy(@PathVariable String hashIdentifier, @PathVariable int personId) {
        notificationService.verifyBy(personId, hashIdentifier);
        Person person = personService.findBy(personId, hashIdentifier);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @GetMapping("/public/signs/one2one/status")
    public ResponseEntity<AssignerModel> checkStatusBy(@RequestParam("personId") String personId,
                                                       @RequestParam("documentId") String documentId) {
        Assigner assigner = assignerService.getBy(Integer.parseInt(personId), Integer.parseInt(documentId));
        AssignerModelAssembler assembler = new AssignerModelAssembler();
        AssignerModel assignerModel = assembler.toModel(assigner);
        return ResponseEntity.ok().body(assignerModel);
    }


    @PostMapping(value = "/public/signs/batch/uuid")
    public ResponseEntity<HashMap<String, String>> save(@RequestParam("identifiers") String identifiers,
                                                        @RequestParam("personId") String personId) {
        String uuid = applicationService.add(Integer.parseInt(personId), identifiers);
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/public/signs/batch/status")
    public ResponseEntity<HashMap<String, Byte>> checkStatusBatchBy(@RequestParam(value = "uuid") String uuid,
                                                                    @RequestParam(value = "personId") String personId) {
        byte completed = assignerService.checkStatusBatchBy(Integer.parseInt(personId), uuid);
        HashMap<String, Byte> map = new HashMap<>();
        map.put("completed", completed);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/public/workflows/{workflowId}/templatedesign/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamResourceBy(@PathVariable int workflowId,
                                                                                    HttpServletRequest request) {
        org.springframework.core.io.Resource resource = resourceService.getStreamTemplateBy(workflowId);
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return extractOctecStream(request, resource, nameFileDownload);
    }

    @GetMapping("/public/documents/resources/{resumeHashResource}/verifier")
    public ResponseEntity<HashMap<String, Object>> getSigners2BySearchCode(@PathVariable String resumeHashResource) {
        _Embedded _embedded = documentService.getVerificationInfoByResumeHashOfResource(resumeHashResource);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/public/documents/resources/{resumeHashResource}/stream")
    public ResponseEntity<org.springframework.core.io.Resource> getStreamBySearchCode(@PathVariable String resumeHashResource,
                                                                                      HttpServletRequest request) throws IOException {
        org.springframework.core.io.Resource resource = resourceService.getStreamByResumeHash(resumeHashResource);
        return extractPdfStream(request, resource);
    }

    private ResponseEntity<org.springframework.core.io.Resource> extractOctecStream(HttpServletRequest request, org.springframework.core.io.Resource resource, String nameFileDownload) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }

    private ResponseEntity<org.springframework.core.io.Resource> extractPdfStream(HttpServletRequest request, org.springframework.core.io.Resource resource) throws IOException {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/pdf";
        }
        String nameFileDownload = resource.getFilename().substring(resource.getFilename().indexOf("-") + 1);
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFileDownload + "\"")
                .body(resource);
    }

    @GetMapping(value = "/public/passwordrecovery/uuid")
    public ResponseEntity<PasswordretrieverModel> get(HttpServletRequest request) {
        Passwordretriever passwordretriever = passwordretrieverService.getUUID();
        PasswordretrieverModelAssembler assembler = new PasswordretrieverModelAssembler();
        PasswordretrieverModel passwordretrieverModel = assembler.toModel(passwordretriever);
        return ResponseEntity.ok().body(passwordretrieverModel);
    }

    @PostMapping(value = "/public/passwordrecovery")
    public ResponseEntity<PasswordretrieverModel> send(@RequestParam("uuid") String uuid,
                                                       @RequestParam("username") String username,
                                                       HttpServletRequest request) {
        Passwordretriever passwordretriever = passwordretrieverService.sent(uuid, username);
        PasswordretrieverModelAssembler assembler = new PasswordretrieverModelAssembler();
        PasswordretrieverModel passwordretrieverModel = assembler.toModel(passwordretriever);
        return ResponseEntity.ok().body(passwordretrieverModel);
    }

    @PostMapping(value = "/public/passwordrecovery/verification")
    public ResponseEntity<PasswordretrieverModel> send(@RequestParam("uuid") String uuid,
                                                       @RequestParam("hash") String hash,
                                                       @RequestParam("verificationCode") String verificationCode,
                                                       HttpServletRequest request) {
        Passwordretriever passwordretriever = passwordretrieverService.verifyCode(uuid, hash, verificationCode);
        PasswordretrieverModelAssembler assembler = new PasswordretrieverModelAssembler();
        PasswordretrieverModel passwordretrieverModel = assembler.toModel(passwordretriever);
        return ResponseEntity.ok().body(passwordretrieverModel);
    }

    @PostMapping(value = "/public/passwordrecovery/validate1")
    public ResponseEntity validate(@RequestParam("hash1") String hash1,
                                   HttpServletRequest request) {
        passwordretrieverService.validate(hash1);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping(value = "/public/passwordrecovery/validate2")
    public ResponseEntity validate(@RequestParam("hash1") String hash1,
                                   @RequestParam("hash2") String hash2,
                                   HttpServletRequest request) {
        passwordretrieverService.validate(hash1, hash2);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping(value = "/public/passwordrecovery/setup")
    public ResponseEntity validate(@RequestParam("hash1") String hash1,
                                   @RequestParam("hash2") String hash2,
                                   @RequestParam("newPassword") String newPassword,
                                   HttpServletRequest request) {
        passwordretrieverService.update(hash1, hash2, newPassword);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/public/documents/search")
    public ResponseEntity<DocumentModel> getStateDocumentBy(@RequestParam("verificationcode") String resumeHashResource,
                                                            HttpServletRequest request) {
        Document document = documentService.getDocumentInfoBy(resumeHashResource);
        OutsideDocumentModelAssembler assembler = new OutsideDocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }


    @PostMapping(value = "/public/people/{personId}/rubric")
    public ResponseEntity<PersonModel> updateUserRubric(@PathVariable int personId,
                                                        @RequestParam("uuid") String uuid,
                                                        @RequestParam("rubricFilename") String rubricFilename,
                                                        @RequestParam("rubricFileBase64") String rubricFileBase64,
                                                        HttpServletRequest request) {
        Person person = personService.updateRubric(personId, uuid, rubricFilename, rubricFileBase64);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @PostMapping("/public/people/{personId}/suboperationtype/{subOperationType}/documents/sign")
    public ResponseEntity<CollectionModel<DocumentModel>> signElectronically(@PathVariable int personId,
                                                                             @PathVariable int subOperationType,
                                                                             @RequestParam("uuid") String uuid,
                                                                             @RequestParam("docIdentifiers") String docIdentifiers) {
        documentService.signElectronically(personId, subOperationType, docIdentifiers, uuid);
        return ResponseEntity.ok().body(null);
    }
}
