package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "assigner")
public class Assigner {
    private Integer id;
    private Integer documentId;
    private Integer operatorId;
    private Byte completed;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Document documentByDocumentId;
    private Integer orderOperation;
    private Operator operatorByOperatorId;
    @JsonIgnore
    private Collection<Done> donesById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "documentId")
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "operatorId", nullable = false)
    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "completed")
    public Byte getCompleted() {
        return completed;
    }

    public void setCompleted(Byte signed) {
        this.completed = signed;
    }

    @Basic
    @Column(name = "createAt")
    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }


    @Basic
    @Column(name = "active")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "observation")
    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Basic
    @Column(name = "orderOperation")
    public Integer getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(Integer order) {
        this.orderOperation = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assigner assigner = (Assigner) o;
        return Objects.equals(id, assigner.id) &&
                Objects.equals(documentId, assigner.documentId) &&
                Objects.equals(operatorId, assigner.operatorId) &&
                Objects.equals(completed, assigner.completed) &&
                Objects.equals(createAt, assigner.createAt) &&
                Objects.equals(active, assigner.active) &&
                Objects.equals(orderOperation, assigner.orderOperation) &&
                Objects.equals(deleted, assigner.deleted) &&
                Objects.equals(observation, assigner.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, operatorId, completed, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentId() {
        return documentByDocumentId;
    }

    public void setDocumentByDocumentId(Document documentByDocumentId) {
        this.documentByDocumentId = documentByDocumentId;
    }

    @OneToMany(mappedBy = "assignerByAssignerId")
    public Collection<Done> getDonesById() {
        return donesById;
    }

    public void setDonesById(Collection<Done> signsById) {
        this.donesById = signsById;
    }

    @ManyToOne
    @JoinColumn(name = "operatorId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Operator getOperatorByOperatorId() {
        return operatorByOperatorId;
    }

    public void setOperatorByOperatorId(Operator operatorByOperatorId) {
        this.operatorByOperatorId = operatorByOperatorId;
    }
}
