package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutOperator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "operator")
public class Operator {
    private Integer id;
    private Integer personId;
    private Integer operationId;
    private Integer enterpriseId;
    private Integer documentId;
    private Byte mandatory;
    private Byte addTsa;
    private Byte sendAlert;
    private Byte sendNotification;
    private Integer orderOperation;
    private Boolean uploadRubric;
    private Boolean digitalSignature;
    private Integer typeElectronicSignature;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Collection<Assigner> assignersById;
    private Collection<Notification> notificationsById;
    private Person personByPersonId;
    private Operation operationByOperationId;
    @JsonIgnore
    private Enterprise enterpriseByEnterpriseId;
    private Document documentByDocumentId;
    private Collection<Sieemailoperator> sieemailoperatorsById;
    private Collection<Sienotification> sienotificationsById;
    private MoreAboutOperator moreAboutOperator;

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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "operationId", nullable = false)
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "enterpriseId", nullable = false)
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "documentId", nullable = false)
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "mandatory", nullable = true)
    public Byte getMandatory() {
        return mandatory;
    }

    public void setMandatory(Byte mandatory) {
        this.mandatory = mandatory;
    }

    @Basic
    @Column(name = "addTsa", nullable = true)
    public Byte getAddTsa() {
        return addTsa;
    }

    public void setAddTsa(Byte addTsa) {
        this.addTsa = addTsa;
    }

    @Basic
    @Column(name = "sendAlert", nullable = true)
    public Byte getSendAlert() {
        return sendAlert;
    }

    public void setSendAlert(Byte sendAlert) {
        this.sendAlert = sendAlert;
    }

    @Basic
    @Column(name = "sendNotification", nullable = true)
    public Byte getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(Byte sendNotification) {
        this.sendNotification = sendNotification;
    }

    @Basic
    @Column(name = "orderOperation", nullable = true)
    public Integer getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(Integer orderOperation) {
        this.orderOperation = orderOperation;
    }

    @Basic
    @Column(name = "uploadRubric", nullable = true)
    public Boolean getUploadRubric() {
        return uploadRubric;
    }

    public void setUploadRubric(Boolean uploadRubric) {
        this.uploadRubric = uploadRubric;
    }

    @Basic
    @Column(name = "digitalSignature", nullable = true)
    public Boolean getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(Boolean digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @Basic
    @Column(name = "typeElectronicSignature", nullable = true)
    public Integer getTypeElectronicSignature() {
        return typeElectronicSignature;
    }

    public void setTypeElectronicSignature(Integer typeElectronicSignature) {
        this.typeElectronicSignature = typeElectronicSignature;
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
        Operator operator = (Operator) o;
        return Objects.equals(id, operator.id) &&
                Objects.equals(personId, operator.personId) &&
                Objects.equals(operationId, operator.operationId) &&
                Objects.equals(enterpriseId, operator.enterpriseId) &&
                Objects.equals(documentId, operator.documentId) &&
                Objects.equals(mandatory, operator.mandatory) &&
                Objects.equals(addTsa, operator.addTsa) &&
                Objects.equals(sendAlert, operator.sendAlert) &&
                Objects.equals(sendNotification, operator.sendNotification) &&
                Objects.equals(orderOperation, operator.orderOperation) &&
                Objects.equals(uploadRubric, operator.uploadRubric) &&
                Objects.equals(digitalSignature, operator.digitalSignature) &&
                Objects.equals(typeElectronicSignature, operator.typeElectronicSignature) &&
                Objects.equals(createAt, operator.createAt) &&
                Objects.equals(active, operator.active) &&
                Objects.equals(deleted, operator.deleted) &&
                Objects.equals(observation, operator.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, operationId, enterpriseId, documentId, mandatory, addTsa, sendAlert, sendNotification, orderOperation, uploadRubric, digitalSignature, typeElectronicSignature, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "operatorByOperatorId")
    public Collection<Assigner> getAssignersById() {
        return assignersById;
    }

    public void setAssignersById(Collection<Assigner> assignersById) {
        this.assignersById = assignersById;
    }

    @OneToMany(mappedBy = "operatorByOperatorId")
    public Collection<Notification> getNotificationsById() {
        return notificationsById;
    }

    public void setNotificationsById(Collection<Notification> notificationsById) {
        this.notificationsById = notificationsById;
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
    @JoinColumn(name = "operationId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Operation getOperationByOperationId() {
        return operationByOperationId;
    }

    public void setOperationByOperationId(Operation operationByOperationId) {
        this.operationByOperationId = operationByOperationId;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentId() {
        return documentByDocumentId;
    }

    public void setDocumentByDocumentId(Document documentByDocumentId) {
        this.documentByDocumentId = documentByDocumentId;
    }

    @OneToMany(mappedBy = "operatorByOperatorId")
    public Collection<Sieemailoperator> getSieemailoperatorsById() {
        return sieemailoperatorsById;
    }

    public void setSieemailoperatorsById(Collection<Sieemailoperator> sieemailoperatorsById) {
        this.sieemailoperatorsById = sieemailoperatorsById;
    }

    @OneToMany(mappedBy = "operatorByOperatorId")
    public Collection<Sienotification> getSienotificationsById() {
        return sienotificationsById;
    }

    public void setSienotificationsById(Collection<Sienotification> sienotificationsById) {
        this.sienotificationsById = sienotificationsById;
    }

    @Transient
    public MoreAboutOperator getMoreAboutOperator() {
        return moreAboutOperator;
    }

    public void setMoreAboutOperator(MoreAboutOperator moreAboutOperator) {
        this.moreAboutOperator = moreAboutOperator;
    }
}
