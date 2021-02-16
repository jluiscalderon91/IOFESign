package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "document")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {
    private Integer id;
    private Integer workflowId;
    private Integer personId;
    private Integer stateId;
    private Integer type;
    private String enterpriseDocumentNumber;
    private String subject;
    private Byte finished;
    private String description;
    private String hashIdentifier;
    private String resumeHashIdentifier;
    private Integer numberOperators;
    private Byte hasMultipleAttachments;
    private Boolean closedStamping;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Assigner> assignersById;
    @JsonIgnore
    private Collection<Cancelnotification> cancelnotificationsById;
    @JsonIgnore
    private Collection<Delivery> deliveriesById;
    private Collection<Deliverymail> deliverymailsById;
    @JsonIgnore
    private Collection<Detailbalanceallocation> detailbalanceallocationsById;
    private Workflow workflowByWorkflowId;
    private Person personByPersonId;
    private State stateByStateId;
    @JsonIgnore
    private Collection<Documentmodification> documentmodificationsById;
    @JsonIgnore
    private Collection<Documentmodification> documentmodificationsById_0;
    @JsonIgnore
    private Collection<Documentresource> documentresourcesById;
    @JsonIgnore
    private Collection<Historicaldocumentmodification> historicaldocumentmodificationsById;
    @JsonIgnore
    private Collection<Historicalhash> historicalhashesById;
    @JsonIgnore
    private Collection<Numbersignature> numbersignaturesById;
    @JsonIgnore
    private Collection<Observationcancel> observationcancelsById;
    @JsonIgnore
    private Collection<Operator> operatorsById;
    private MoreAboutDocument moreAboutDocument;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "workflowId", nullable = false)
    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    @Basic
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "stateId", nullable = false)
    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "enterpriseDocumentNumber", nullable = true, length = 24)
    public String getEnterpriseDocumentNumber() {
        return enterpriseDocumentNumber;
    }

    public void setEnterpriseDocumentNumber(String enterpriseDocumentNumber) {
        this.enterpriseDocumentNumber = enterpriseDocumentNumber;
    }

    @Basic
    @Column(name = "subject", nullable = true, length = 128)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "finished", nullable = true)
    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "hashIdentifier", nullable = true, length = 128)
    public String getHashIdentifier() {
        return hashIdentifier;
    }

    public void setHashIdentifier(String hashIdentifier) {
        this.hashIdentifier = hashIdentifier;
    }

    @Basic
    @Column(name = "resumeHashIdentifier", nullable = true, length = 32)
    public String getResumeHashIdentifier() {
        return resumeHashIdentifier;
    }

    public void setResumeHashIdentifier(String resumeHashIdentifier) {
        this.resumeHashIdentifier = resumeHashIdentifier;
    }

    @Basic
    @Column(name = "numberOperators", nullable = true)
    public Integer getNumberOperators() {
        return numberOperators;
    }

    public void setNumberOperators(Integer numberOperators) {
        this.numberOperators = numberOperators;
    }

    @Basic
    @Column(name = "hasMultipleAttachments", nullable = true)
    public Byte getHasMultipleAttachments() {
        return hasMultipleAttachments;
    }

    public void setHasMultipleAttachments(Byte hasMultipleAttachments) {
        this.hasMultipleAttachments = hasMultipleAttachments;
    }

    @Basic
    @Column(name = "closedStamping", nullable = true)
    public Boolean getClosedStamping() {
        return closedStamping;
    }

    public void setClosedStamping(Boolean closedStamping) {
        this.closedStamping = closedStamping;
    }

    @Basic
    @Column(name = "createAt", nullable = true)
    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted", nullable = true)
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "observation", nullable = true, length = 64)
    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(workflowId, document.workflowId) &&
                Objects.equals(personId, document.personId) &&
                Objects.equals(stateId, document.stateId) &&
                Objects.equals(type, document.type) &&
                Objects.equals(enterpriseDocumentNumber, document.enterpriseDocumentNumber) &&
                Objects.equals(subject, document.subject) &&
                Objects.equals(finished, document.finished) &&
                Objects.equals(description, document.description) &&
                Objects.equals(hashIdentifier, document.hashIdentifier) &&
                Objects.equals(resumeHashIdentifier, document.resumeHashIdentifier) &&
                Objects.equals(numberOperators, document.numberOperators) &&
                Objects.equals(hasMultipleAttachments, document.hasMultipleAttachments) &&
                Objects.equals(createAt, document.createAt) &&
                Objects.equals(active, document.active) &&
                Objects.equals(deleted, document.deleted) &&
                Objects.equals(observation, document.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workflowId, personId, stateId, type, enterpriseDocumentNumber, subject, finished, description, hashIdentifier, resumeHashIdentifier, numberOperators, hasMultipleAttachments, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Assigner> getAssignersById() {
        return assignersById;
    }

    public void setAssignersById(Collection<Assigner> assignersById) {
        this.assignersById = assignersById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Cancelnotification> getCancelnotificationsById() {
        return cancelnotificationsById;
    }

    public void setCancelnotificationsById(Collection<Cancelnotification> cancelnotificationsById) {
        this.cancelnotificationsById = cancelnotificationsById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Delivery> getDeliveriesById() {
        return deliveriesById;
    }

    public void setDeliveriesById(Collection<Delivery> deliveriesById) {
        this.deliveriesById = deliveriesById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Detailbalanceallocation> getDetailbalanceallocationsById() {
        return detailbalanceallocationsById;
    }

    public void setDetailbalanceallocationsById(Collection<Detailbalanceallocation> detailbalanceallocationsById) {
        this.detailbalanceallocationsById = detailbalanceallocationsById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Deliverymail> getDeliverymailsById() {
        return deliverymailsById;
    }

    public void setDeliverymailsById(Collection<Deliverymail> deliverymailsById) {
        this.deliverymailsById = deliverymailsById;
    }

    @ManyToOne
    @JoinColumn(name = "workflowId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Workflow getWorkflowByWorkflowId() {
        return workflowByWorkflowId;
    }

    public void setWorkflowByWorkflowId(Workflow workflowByWorkflowId) {
        this.workflowByWorkflowId = workflowByWorkflowId;
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "stateId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public State getStateByStateId() {
        return stateByStateId;
    }

    public void setStateByStateId(State stateByStateId) {
        this.stateByStateId = stateByStateId;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Documentresource> getDocumentresourcesById() {
        return documentresourcesById;
    }

    public void setDocumentresourcesById(Collection<Documentresource> documentresourcesById) {
        this.documentresourcesById = documentresourcesById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Historicalhash> getHistoricalhashesById() {
        return historicalhashesById;
    }

    public void setHistoricalhashesById(Collection<Historicalhash> historicalhashesById) {
        this.historicalhashesById = historicalhashesById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Numbersignature> getNumbersignaturesById() {
        return numbersignaturesById;
    }

    public void setNumbersignaturesById(Collection<Numbersignature> numbersignaturesById) {
        this.numbersignaturesById = numbersignaturesById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Observationcancel> getObservationcancelsById() {
        return observationcancelsById;
    }

    public void setObservationcancelsById(Collection<Observationcancel> observationcancelsById) {
        this.observationcancelsById = observationcancelsById;
    }

    @OneToMany(mappedBy = "documentByDocumentId")
    public Collection<Operator> getOperatorsById() {
        return operatorsById;
    }

    public void setOperatorsById(Collection<Operator> operatorsById) {
        this.operatorsById = operatorsById;
    }

    @Transient
    public MoreAboutDocument getMoreAboutDocument() {
        return moreAboutDocument;
    }

    public void setMoreAboutDocument(MoreAboutDocument moreAboutDocument) {
        this.moreAboutDocument = moreAboutDocument;
    }
}
