package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "workflowstampdatetime")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workflowstampdatetime {
    private Integer id;
    private Integer workflowId;
    private Integer stampdatetimeId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Workflow workflowByWorkflowId;
    private Stampdatetime stampdatetimeByStampdatetimeId;

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
    @Column(name = "stampdatetimeId", nullable = false)
    public Integer getStampdatetimeId() {
        return stampdatetimeId;
    }

    public void setStampdatetimeId(Integer stampdatetimeId) {
        this.stampdatetimeId = stampdatetimeId;
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
        Workflowstampdatetime that = (Workflowstampdatetime) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(workflowId, that.workflowId) &&
                Objects.equals(stampdatetimeId, that.stampdatetimeId) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workflowId, stampdatetimeId, createAt, active, deleted, observation);
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
    @JoinColumn(name = "stampdatetimeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Stampdatetime getStampdatetimeByStampdatetimeId() {
        return stampdatetimeByStampdatetimeId;
    }

    public void setStampdatetimeByStampdatetimeId(Stampdatetime stampdatetimeByStampdatetimeId) {
        this.stampdatetimeByStampdatetimeId = stampdatetimeByStampdatetimeId;
    }
}
