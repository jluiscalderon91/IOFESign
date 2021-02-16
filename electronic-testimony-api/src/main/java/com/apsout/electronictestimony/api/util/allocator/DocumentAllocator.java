package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;
import com.apsout.electronictestimony.api.entity.model.pojo._Comment;
import com.apsout.electronictestimony.api.entity.model.pojo._Operator;
import com.apsout.electronictestimony.api.entity.model.pojo._Resource;
import com.apsout.electronictestimony.api.util.crypto.Hash;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.DocumentState;
import com.apsout.electronictestimony.api.util.statics.ElectronicSignature;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;

import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DocumentAllocator {
    public static Document build(Person person, Workflow workflow, State state, int type, String subject, String hashIdentifier, String description, int numberOperators) {
        Document document = new Document();
        document.setPersonId(person.getId());
        document.setWorkflowId(workflow.getId());
        document.setWorkflowByWorkflowId(workflow);
        document.setPersonByPersonId(person);
        document.setStateId(state.getId());
        document.setStateByStateId(state);
        document.setType(type);
        document.setSubject(subject.trim());
        document.setFinished(States.NOT_FINISHED);
        document.setDescription(description);
        document.setNumberOperators(numberOperators);
        document.setEnterpriseDocumentNumber(person.getEnterpriseByEnterpriseIdView().getDocumentNumber());
        document.setHashIdentifier(hashIdentifier);
        document.setResumeHashIdentifier(Hash.Crc(hashIdentifier).toLowerCase());
        document.setClosedStamping(false);
        ofPostMethod(document);
        return document;
    }

    //Add info before save on db, for a Post request
    public static void ofPostMethod(Document document) {
        document.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        document.setActive(States.ACTIVE);
        document.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Document document, byte active) {
        forUpdateWithState(document, DocumentState.CANCELLED);
        document.setActive(active);
    }

    public static void forUpdate(Document document, String hashIdentifier, boolean willBeClosedStamping) {
        document.setHashIdentifier(hashIdentifier);
        document.setClosedStamping(willBeClosedStamping);
    }

    public static void forUpdate2(Document document, int sizeListFile) {
        if (sizeListFile > 1) {
            document.setHasMultipleAttachments(States.HAS_MULTIPLE_ATTACH);
        } else {
            document.setHasMultipleAttachments(States.HAS_NOT_MULTIPLE_ATTACH);
        }
    }

    public static List<Document> getOnlyFinished(List<Document> documents) {
        return documents.stream().filter(document -> States.FINISHED == document.getFinished()).collect(Collectors.toList());
    }

    public static void forUpdateWithState(Document document, int documentState) {
        document.setStateId(documentState);
    }

    public static void loadSignInfo(X509Certificate x509Certificate, TimeStampToken timeStampToken, _Operator operator, Operation operation, byte correctSignature) {
        String certName = x509Certificate.getSubjectX500Principal().getName();
        String tsaName = "-";
        String trustedSigningTime = "-";
        if (timeStampToken != null) {
            Store certStore = timeStampToken.getCertificates();
            X509CertificateHolder x509CertificateHolder = (X509CertificateHolder) certStore.getMatches(timeStampToken.getSID()).iterator().next();
            String subjectTSA = x509CertificateHolder.getSubject().toString();
            tsaName = subjectTSA.substring(subjectTSA.indexOf("CN="), subjectTSA.indexOf(",C=PE")).substring(3);
            trustedSigningTime = DateUtil.build(timeStampToken.getTimeStampInfo().getGenTime(), "dd/MM/yyyy HH:mm:ss");
        }
        final String signerName = getCN(certName);
        operator.setOperatorName(signerName);
        operator.setOperationId(operation.getId());
        operator.setTsa(tsaName);
        operator.setCertainDateTime(trustedSigningTime);
        operator.setOperationDescription("Firma digital");
        operator.setCorrectOperation(correctSignature);
        String issuerDNName = x509Certificate.getIssuerDN().getName();
        final String issuerCertName = getCN(issuerDNName);
        operator.setIssuerCertName(issuerCertName);
        operator.setTypeElectronicSignature(ElectronicSignature.ANYTHING);
        operator.setDigitalSignature(true);
    }

    private static String getCN(String name) {
        String[] strings = name.split(Pattern.quote(","));
        Optional<String> optional = Arrays.stream(strings).map(String::trim).filter(s -> s.startsWith("CN=")).findFirst();
        if (optional.isPresent()) {
            final String commonName = optional.get().replaceFirst("CN=", "");
            // Validation for eDNI
            if (commonName.contains(" FIR ")) {
                int indexOf = commonName.indexOf(" FIR ");
                return commonName.substring(0, indexOf);
            }
            return commonName;
        } else {
            return "Search again for the CN data";
        }
    }

    public static void loadMoreInfo01(MoreAboutDocument moreAboutDocument,
                                      Document document,
                                      List<Resource> resources,
                                      String assignmentProgress,
                                      byte hasComment,
                                      List<_Comment> comments,
                                      byte observed) {
        moreAboutDocument.setFullnameNextSigner("-");
        final int FINISHED = 0;
        moreAboutDocument.setPersonId(FINISHED);
        moreAboutDocument.setAssignmentProgress(assignmentProgress);
        moreAboutDocument.setOperationId(FINISHED);
        moreAboutDocument.setCommentsInfo(comments);
        moreAboutDocument.setHasComment(hasComment);
        moreAboutDocument.setObserved(observed);
        moreAboutDocument.setWorkflowDescription(document.getWorkflowByWorkflowId().getDescription());
        moreAboutDocument.setParticipantType(FINISHED);
        moreAboutDocument.setMandatoryOperator((byte) FINISHED);
        moreAboutDocument.setUploaderName(document.getPersonByPersonId().getFullname());
        moreAboutDocument.setEnterpriseName(document.getPersonByPersonId().getEnterpriseByEnterpriseIdView().getTradeName());
        if (Default.UNIQUE_ELEMENT == resources.size()) {
            Resource resource = resources.stream().findFirst().get();
            moreAboutDocument.setBasenameFile(FilenameUtils.getBaseName(resource.getOriginalName()));
            moreAboutDocument.setExtension(resource.getExtension());
        } else {
            moreAboutDocument.setBasenameFile(document.getSubject());
            moreAboutDocument.setExtension("zip");
        }
        moreAboutDocument.setSuffix("-signed");
    }

    public static void loadMoreInfo02(MoreAboutDocument moreAboutDocument,
                                      Document document,
                                      Operator operator,
                                      List<Resource> resources,
                                      Integer orderNextSigner,
                                      Person person,
                                      String urlSignOperator,
                                      Scope scope,
                                      String assignmentProgress,
                                      byte hasComment,
                                      List<_Comment> comments,
                                      byte observed,
                                      byte dynamicWorkflow,
                                      boolean willBeClosedStamping) {
        moreAboutDocument.setOrderNextSigner(orderNextSigner);
        moreAboutDocument.setFullnameNextSigner(person.getFullname());
        moreAboutDocument.setPersonId(person.getId());
        moreAboutDocument.setAssignmentProgress(assignmentProgress);
        moreAboutDocument.setOperationId(operator.getOperationByOperationId().getId());
        moreAboutDocument.setCommentsInfo(comments);
        moreAboutDocument.setHasComment(hasComment);
        moreAboutDocument.setObserved(observed);
        moreAboutDocument.setWorkflowDescription(document.getWorkflowByWorkflowId().getDescription());
        moreAboutDocument.setParticipantType(scope.getParticipantType());
        moreAboutDocument.setUploaderName(document.getPersonByPersonId().getFullname());
        moreAboutDocument.setEnterpriseName(document.getPersonByPersonId().getEnterpriseByEnterpriseIdView().getTradeName());
        final Byte mandatoryOperator = operator.getMandatory();
        if (States.NOT_MANDATORY == mandatoryOperator) {
            moreAboutDocument.setMandatoryOperator(mandatoryOperator);
            moreAboutDocument.setUrlSignOperator(urlSignOperator);
        }
        if (Default.UNIQUE_ELEMENT == resources.size()) {
            Resource resource = resources.stream().findFirst().get();
            moreAboutDocument.setBasenameFile(FilenameUtils.getBaseName(resource.getOriginalName()));
            moreAboutDocument.setExtension(resource.getExtension());
        } else {
            moreAboutDocument.setBasenameFile(document.getSubject());
            moreAboutDocument.setExtension("zip");
        }
        moreAboutDocument.setDynamicWorkflow(dynamicWorkflow);
        moreAboutDocument.setDigitalSignature(operator.getDigitalSignature());
        moreAboutDocument.setWillBeClosedStamping(willBeClosedStamping);
    }

    public static void loadMoreInfo02(MoreAboutDocument moreAboutDocument,
                                      Document document,
                                      Operator operator,
                                      List<Resource> resources,
                                      Integer orderNextSigner,
                                      Person person,
                                      String urlSignOperator,
                                      Scope scope,
                                      String assignmentProgress,
                                      byte hasComment,
                                      List<_Comment> comments,
                                      byte observed,
                                      Stampdatetime stampdatetime,
                                      byte dynamicWorkflow,
                                      boolean willBeClosedStamping) {
        loadMoreInfo02(moreAboutDocument, document, operator, resources, orderNextSigner, person, urlSignOperator, scope, assignmentProgress, hasComment, comments, observed, dynamicWorkflow, willBeClosedStamping);
        moreAboutDocument.setStampdatetime(stampdatetime);
    }

    public static void loadMoreInfoResources(MoreAboutDocument moreAboutDocument, int personId, Document document, List<Resource> resources) {
        List<_Resource> resourceList = resources.stream().map(resource -> {
            _Resource resource1 = new _Resource();
            resource1.setFileName(resource.getOriginalName());
            final String url = new StringBuilder(Global.ROOT_API_V1)
                    .append("/public/people/")
                    .append(personId)
                    .append("/documents/")
                    .append(document.getId())
                    .append("/")
                    .append(document.getHashIdentifier())
                    .append("/resources/")
                    .append(resource.getOrderResource())
                    .append("/private/stream").toString();
            resource1.setUrl(url);
            resource1.setHashResource(resource.getHash());
            resource1.setOrderResource(resource.getOrderResource());
            resource1.setLength(resource.getLength());
            final String url2 = new StringBuilder(Global.ROOT_API_V1)
                    .append("/public/people/")
                    .append(personId)
                    .append("/documents/")
                    .append(document.getId())
                    .append("/")
                    .append(document.getHashIdentifier())
                    .append("/resources/")
                    .append(resource.getOrderResource())
                    .append("/stream").toString();
            resource1.setUrl2(url2);
            return resource1;
        }).collect(Collectors.toList());
        moreAboutDocument.setResources(resourceList);
        moreAboutDocument.setWorkflowDescription(document.getWorkflowByWorkflowId().getDescription());
    }

    public static void loadMoreInfo01Outside(MoreAboutDocument moreAboutDocument,
                                             String assignmentProgress, List<_Operator> operators) {
        moreAboutDocument.setAssignmentProgress(assignmentProgress);
        moreAboutDocument.setOperators(operators);
    }

    public static void loadMoreInfo02Outside(MoreAboutDocument moreAboutDocument,
                                             Person person,
                                             String assignmentProgress,
                                             int currentSignerOrder,
                                             int totalSigners,
                                             List<_Operator> operators) {
        moreAboutDocument.setNextSignerFullname(person.getFullname());
        moreAboutDocument.setAssignmentProgress(assignmentProgress);
        moreAboutDocument.setCurrentSignerOrder(currentSignerOrder);
        moreAboutDocument.setTotalSigners(totalSigners);
        moreAboutDocument.setOperators(operators);
    }

    public static void forDelete(Document document, byte deleted) {
        document.setDeleted(deleted);
    }
}
