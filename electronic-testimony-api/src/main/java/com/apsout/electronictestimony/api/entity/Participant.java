package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutParticipant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "participant")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Participant {
    private Integer id;
    private Integer workflowId;
    private Integer operationId;
    private Integer personId;
    private Integer participantType;
    private Integer orderParticipant;
    private Byte addTsa;
    private Byte sendAlert;
    private Byte sendNotification;
    private Byte sieConfigured;
    private Timestamp createAt;
    private Boolean uploadRubric;
    private Boolean digitalSignature;
    private Integer typeElectronicSignature;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Workflow workflowByWorkflowId;
    private Operation operationByOperationId;
    private Person personByPersonId;
    @JsonIgnore
    private Collection<Sieemail> sieemailsById;
    @JsonIgnore
    private Collection<Stamprubric> stamprubricsById;
    private MoreAboutParticipant moreAboutParticipant;

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
    @Column(name = "operationId", nullable = false)
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
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
    @Column(name = "participantType", nullable = true)
    public Integer getParticipantType() {
        return participantType;
    }

    public void setParticipantType(Integer participantType) {
        this.participantType = participantType;
    }

    @Basic
    @Column(name = "orderParticipant", nullable = true)
    public Integer getOrderParticipant() {
        return orderParticipant;
    }

    public void setOrderParticipant(Integer orderParticipant) {
        this.orderParticipant = orderParticipant;
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
    @Column(name = "sieConfigured", nullable = true)
    public Byte getSieConfigured() {
        return sieConfigured;
    }

    public void setSieConfigured(Byte sieConfigured) {
        this.sieConfigured = sieConfigured;
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
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(workflowId, that.workflowId) &&
                Objects.equals(operationId, that.operationId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(participantType, that.participantType) &&
                Objects.equals(orderParticipant, that.orderParticipant) &&
                Objects.equals(addTsa, that.addTsa) &&
                Objects.equals(sendAlert, that.sendAlert) &&
                Objects.equals(sendNotification, that.sendNotification) &&
                Objects.equals(sieConfigured, that.sieConfigured) &&
                Objects.equals(uploadRubric, that.uploadRubric) &&
                Objects.equals(digitalSignature, that.digitalSignature) &&
                Objects.equals(typeElectronicSignature, that.typeElectronicSignature) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workflowId, operationId, personId, participantType, orderParticipant, addTsa, sendAlert, sendNotification, sieConfigured, uploadRubric, digitalSignature, typeElectronicSignature, createAt, active, deleted, observation);
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
    @JoinColumn(name = "operationId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Operation getOperationByOperationId() {
        return operationByOperationId;
    }

    public void setOperationByOperationId(Operation operationByOperationId) {
        this.operationByOperationId = operationByOperationId;
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @OneToMany(mappedBy = "participantByParticipantId")
    public Collection<Sieemail> getSieemailsById() {
        return sieemailsById;
    }

    public void setSieemailsById(Collection<Sieemail> sieemailsById) {
        this.sieemailsById = sieemailsById;
    }

    @OneToMany(mappedBy = "participantByParticipantId")
    public Collection<Stamprubric> getStamprubricsById() {
        return stamprubricsById;
    }

    public void setStamprubricsById(Collection<Stamprubric> stamprubricsById) {
        this.stamprubricsById = stamprubricsById;
    }

    @Transient
    public MoreAboutParticipant getMoreAboutParticipant() {
        return moreAboutParticipant;
    }

    public void setMoreAboutParticipant(MoreAboutParticipant moreAboutParticipant) {
        this.moreAboutParticipant = moreAboutParticipant;
    }
}
