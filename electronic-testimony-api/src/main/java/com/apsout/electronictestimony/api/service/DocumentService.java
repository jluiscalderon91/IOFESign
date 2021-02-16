package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.pojo._Body;
import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;
import com.inversionesrc.dsignature.SignatureResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Page<Document> getAll(Pageable pageable);

    Optional<Document> findBy(int documentId);

    Document getBy(int documentId);

    Document save(Document document);

    Document save(Person person, int flowId, int type, String subject, String participants, MultipartFile[] multipartFiles);

    Document save(int workflowId, int type, String subject, String participants, MultipartFile[] multipartFiles, HttpServletRequest request);

    List<Document> getBy(List<Integer> identifiers, int personId);

    Document endProcess(Document document);

    Page<Document> getAllBy4User(int enterpriseId, int workflowId, int personId, String term2Search, String states, Pageable pageable);

    Document getDocumentInfoBy(int personId, String hashIdentifier);

    Document getBy(String hashIdentifier);

    _Embedded getVerificationInfoBy(String hashIdentifier);

    _Embedded getVerificationInfoBy(String hashIdentifier, byte isPrivate);

    _Embedded getVerificationInfoBy(String hashIdentifier, String hashResource);

    _Embedded getVerificationInfoBy(String hashIdentifier, String hashResource, byte isPrivate);

    Document findByOperatorId(int operatorId);

    Document findByWithoutConditions(int operatorId);

    Document getBy(int documentId, String hashIdentifier);

    Document cancellation(Observationcancel observationcancel, HttpServletRequest request);

    Document cancel(Person person, Document document, String comment);

    Page<Document> getAllBy4Admins(int enterpriseId, int workflowId, String term2Search, String states, Pageable pageable, HttpServletRequest request);

    List<Document> getBy(List<Integer> identifiers);

    List<Document> update(int personId, int stateId, String docIdentifiers);

    List<Document> save(List<Document> documents);

    String buildUrl(Person person, Document document);

    String buildHashIdentifier(String subject);

    Document getBy(Resource resource);

    void checkPermissionsOnFlow(int workflowId, HttpServletRequest request);

    Document save(Person person, _Body body);

    Page<Document> getAll4Assistant(int enterpriseId, int workflowId, int personId, String term2Search, String states, Pageable pageable);

    Document getBy4Outside(int documentId);

    _Embedded getVerificationInfoByResumeHashOfResource(String resumeHashResource);

    Document delete(int documentId);

    Optional<Document> findByNoDeleted(int documentId);

    Document getByNoDeleted(int documentId);

    boolean haveFirstSignature(Document document);

    int getStampOnBy(Document document, Resource resource, Stampdatetime stampdatetime);

    boolean isAllowedDeleted(HttpServletRequest request, int documentId);

    boolean isAllowedCancelled(HttpServletRequest request, Observationcancel observationcancel);

    List<SignatureResult> verifySignatures(File file);

    boolean isFinished(Document document);

    boolean isCanceled(Document document);

    Document getDocumentInfoBy(String resumeHashResource);

    boolean willBeClosedStamping(int personId, Document document);

    boolean willBeClosedStamping(int personId, Operator operator, Document document);

    void signElectronically(int personId, int subOperationType, String docIdentifiers);

    void signElectronically(int personId, int subOperationType, String docIdentifiers, String uuid);

    String getURLStreamBy(Document document);

    Document getByNoDeleted(int documentId, String hashIdentifier);

    Document modify(int workflowId, int type, String subject, String replacements, MultipartFile[] multipartFiles, int documentIdModified, String reasonModification, HttpServletRequest request);
}
