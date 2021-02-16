package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "stationcounter")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stationcounter {
    private Integer id;
    private Integer workflowId;
    private Integer personId;
    private Integer type;
    private Integer initialAmount;
    private Integer finalAmount;
    private Byte completed;
    private Timestamp updateAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Workflow workflowByWorkflowId;
    private Person personByPersonId;

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
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "initialAmount", nullable = true)
    public Integer getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Integer initialAmount) {
        this.initialAmount = initialAmount;
    }

    @Basic
    @Column(name = "finalAmount", nullable = true)
    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Basic
    @Column(name = "completed", nullable = true)
    public Byte getCompleted() {
        return completed;
    }

    public void setCompleted(Byte completed) {
        this.completed = completed;
    }

    @Basic
    @Column(name = "updateAt", nullable = true)
    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stationcounter that = (Stationcounter) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(initialAmount, that.initialAmount) &&
                Objects.equals(finalAmount, that.finalAmount) &&
                Objects.equals(completed, that.completed) &&
                Objects.equals(updateAt, that.updateAt) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(workflowId, that.workflowId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, initialAmount, finalAmount, completed, updateAt, createAt, active, deleted, observation, workflowId, personId);
    }

    @ManyToOne
    @JoinColumn(name = "workflowId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Workflow getWorkflowByWorkflowId() {
        return workflowByWorkflowId;
    }

    public void setWorkflowByWorkflowId(Workflow workflowByWorkflowId) {
        this.workflowByWorkflowId = workflowByWorkflowId;
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @Override
    public String toString() {
        return new StringBuilder("workflowId: ")
                .append(workflowId)
                .append(", type:")
                .append(type).toString();
    }
}
