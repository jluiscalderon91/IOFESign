package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.DocumentModel;
import com.apsout.electronictestimony.api.entity.model.pojo._Body;
import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;
import com.apsout.electronictestimony.api.entity.model.pojo._Revision;
import com.apsout.electronictestimony.api.modelassembler.DocumentModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.OutsideDocumentModelAssembler;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.statics.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class DocumentController {

    @Autowired
    public DocumentService documentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PersonService personService;
    @Autowired
    private AccessResourceService accessResourceService;
    @Autowired
    private DocumenttraceabilityService traceability;
    @Autowired
    private DeliverymailService deliverymailService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:document:add:one2one') or hasAuthority('own:document:add:batch') or hasAnyAuthority('own:document:add:one2one:dynamic')")
    @PostMapping("/documents")
    public ResponseEntity<DocumentModel> save(@RequestParam("workflowId") int workflowId,
                                              @RequestParam("type") int type,
                                              @RequestParam("subject") String subject,
                                              @RequestParam("replacements") String replacements,
                                              @RequestParam("files") MultipartFile[] files,
                                              HttpServletRequest request) {
        documentService.checkPermissionsOnFlow(workflowId, request);
        Document document = documentService.save(workflowId, type, subject, replacements, files, request);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }

    @PreAuthorize("hasAuthority('own:document:get:supervisor') or hasAuthority('own:document:get:admin') or hasAuthority('own:document:get:superadmin')")
    @GetMapping(value = "/enterprises/{enterpriseId}/workflows/{workflowId}/documents")
    public ResponseEntity<CollectionModel<DocumentModel>> getAllByAdmin(@PathVariable int enterpriseId,
                                                                        @PathVariable int workflowId,
                                                                        @RequestParam String findby,
                                                                        @RequestParam String states,
                                                                        Pageable pageable,
                                                                        HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        documentService.checkPermissionsOnFlow(workflowId, request);
        Page<Document> documents = documentService.getAllBy4Admins(enterpriseId, workflowId, findby, states, pageable, request);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        PagedModel<DocumentModel> pagedModel = resourcesAssembler.toModel(documents, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:document:get:assistant')")
    @GetMapping(value = "/enterprises/{enterpriseId}/workflows/{workflowId}/people/{personId}/documents")
    public ResponseEntity<CollectionModel<DocumentModel>> getAllBy4Assistant(@PathVariable int enterpriseId,
                                                                             @PathVariable int workflowId,
                                                                             @PathVariable int personId,
                                                                             @RequestParam String findby,
                                                                             @RequestParam String states,
                                                                             Pageable pageable,
                                                                             HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        documentService.checkPermissionsOnFlow(workflowId, request);
        Page<Document> documents = documentService.getAll4Assistant(enterpriseId, workflowId, personId, findby, states, pageable);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        PagedModel<DocumentModel> pagedModel = resourcesAssembler.toModel(documents, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:document:get:user')")
    @GetMapping(value = "/enterprises/{enterpriseId}/workflows/{workflowId}/people/{personId}/user/documents")
    public ResponseEntity<CollectionModel<DocumentModel>> getAllBy4User(@PathVariable int enterpriseId,
                                                                        @PathVariable int workflowId,
                                                                        @PathVariable int personId,
                                                                        @RequestParam String findby,
                                                                        @RequestParam String states,
                                                                        Pageable pageable,
                                                                        HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Page<Document> documents = documentService.getAllBy4User(enterpriseId, workflowId, personId, findby, states, pageable);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        PagedModel<DocumentModel> pagedModel = resourcesAssembler.toModel(documents, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:document:review:one2one')")
    @PostMapping("/documents/{documentId}/{hashIdentifier}/people/{personId}/review")
    public ResponseEntity reviewOne2OneBy(@PathVariable int documentId,
                                          @PathVariable String hashIdentifier,
                                          @PathVariable int personId,
                                          @RequestParam("observed") byte observed,
                                          @RequestParam("cancelled") byte cancelled,
                                          @RequestParam("comment") String comment,
                                          HttpServletRequest request) {
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        //TODO recibir parámetro de cierre de estampado
        resourceService.review(personId, documentId, hashIdentifier, observed, cancelled, comment, false, request);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('own:document:cancel:one2one') or this.documentService.isAllowedCancelled(#request, #observationcancel)")
    @PutMapping(value = "/documents")
    public ResponseEntity<DocumentModel> cancel(@RequestBody Observationcancel observationcancel,
                                                HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentIdOperators(request, observationcancel.getDocumentId());
        Document documentCancelled = documentService.cancellation(observationcancel, request);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(documentCancelled);
        return ResponseEntity.ok().body(documentModel);
    }

    @PreAuthorize("hasAuthority('own:document:attend:one2one') or hasAuthority('own:document:attend:batch') or hasAuthority('own:document:unattend:one2one') or hasAuthority('own:document:unattend:batch')")
    @PostMapping("/people/{personId}/states/{stateId}/documents/update")
    public ResponseEntity<CollectionModel<DocumentModel>> update(@PathVariable String personId,
                                                                 @PathVariable String stateId,
                                                                 @RequestParam("docIdentifiers") String docIdentifiers) {
        List<Document> documentsUpdated = documentService.update(Integer.parseInt(personId), Integer.parseInt(stateId), docIdentifiers);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        CollectionModel<DocumentModel> collectionModel = assembler.toCollectionModel(documentsUpdated);
        return ResponseEntity.ok().body(collectionModel);
    }

    @PreAuthorize("hasAuthority('outside:document:add:one2one') or hasAuthority('outside:document:add:batch')")
    @PostMapping(value = "/outside/documents")
    public ResponseEntity<DocumentModel> save(@RequestBody _Body body, HttpServletRequest request) {
        accessResourceService.validate2participants(request, body.getWorkflowId());
        Person person = personService.getBy(request);
        Document document = documentService.save(person, body);
        OutsideDocumentModelAssembler assembler = new OutsideDocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }

    @PreAuthorize("hasAuthority('outside:document:status:one2one')")
    @GetMapping(value = "/outside/documents/{documentId}")
    public ResponseEntity<DocumentModel> getBy2(@PathVariable int documentId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        Document document = documentService.getBy4Outside(documentId);
        OutsideDocumentModelAssembler assembler = new OutsideDocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }

    @PreAuthorize("hasAuthority('own:document:delete:one2one') or this.documentService.isAllowedDeleted(#request, #documentId)")
    @DeleteMapping(value = "/documents/{documentId}")
    public ResponseEntity<DocumentModel> delete(@PathVariable int documentId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        Document documentDeleted = documentService.delete(documentId);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(documentDeleted);
        return ResponseEntity.ok().body(documentModel);
    }

    @PreAuthorize("hasAuthority('own:document:verification:resource')")
    @GetMapping("/documents/{hashIdentifier}/resources/{hashResource}/private/verifier")
    public ResponseEntity<HashMap<String, Object>> getSigners2By(@PathVariable String hashIdentifier,
                                                                 @PathVariable String hashResource) {
        _Embedded _embedded = documentService.getVerificationInfoBy(hashIdentifier, hashResource, States.PRIVATE);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @PreAuthorize("hasAuthority('own:document:verification:oldresource')")
    @GetMapping("/documents/{hashIdentifier}/private/verifier")
    public ResponseEntity<HashMap<String, Object>> getSignersBy(@PathVariable String hashIdentifier) {
        _Embedded _embedded = documentService.getVerificationInfoBy(hashIdentifier, States.PRIVATE);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @PreAuthorize("hasAuthority('own:traceability:add:document')")
    @PostMapping("/documents/{documentId}/traceability/{type}/states/{stateId}")
    public ResponseEntity registerTraceabilityBy(@PathVariable int documentId,
                                                 @PathVariable int type,
                                                 @PathVariable int stateId,
                                                 HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentIdOperators(request, documentId);
        traceability.save(request, documentId, stateId, type);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('own:document:review:batch')")
    @PostMapping("/people/{personId}/documents/review")
    public ResponseEntity reviewBacthBy(@PathVariable int personId,
                                        @RequestBody _Revision revision,
                                        HttpServletRequest request) {
        // TODO recibir parámetro de estampado final
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        resourceService.review(personId, revision, false, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('own:document:send:mail:attachment')")
    @PostMapping("/documents/{documentId}/contentdeliverymail")
    public ResponseEntity sendAttachments(@PathVariable int documentId,
                                          @RequestBody Contentdeliverymail contentdeliverymail,
                                          HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        deliverymailService.register(documentId, contentdeliverymail, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("true")
    @PostMapping("/people/{personId}/suboperationtype/{subOperationType}/documents/sign")
    public ResponseEntity signElectronically(@PathVariable int personId,
                                             @PathVariable int subOperationType,
                                             @RequestParam("docIdentifiers") String docIdentifiers,
                                             HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfPersonId(request, personId);
        documentService.signElectronically(personId, subOperationType, docIdentifiers);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("true")
    @PostMapping("/documents/{documentId}/modify")
    public ResponseEntity<DocumentModel> modify(@PathVariable int documentId,
                                                @RequestParam("workflowId") int workflowId,
                                                @RequestParam("type") int type,
                                                @RequestParam("subject") String subject,
                                                @RequestParam("replacements") String replacements,
                                                @RequestParam("files") MultipartFile[] files,
                                                @RequestParam("reasonModification") String reasonModification,
                                                HttpServletRequest request) {
//        documentService.checkPermissionsOnFlow(workflowId, request);
        Document document = documentService.modify(workflowId, type, subject, replacements, files, documentId, reasonModification, request);
        DocumentModelAssembler assembler = new DocumentModelAssembler();
        DocumentModel documentModel = assembler.toModel(document);
        return ResponseEntity.ok().body(documentModel);
    }
}
