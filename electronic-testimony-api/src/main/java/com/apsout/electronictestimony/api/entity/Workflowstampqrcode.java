package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "workflowstampqrcode")
public class Workflowstampqrcode {
    private Integer id;
    private Integer workflowId;
    private Integer stampqrcodeId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Workflow workflowByWorkflowId;
    private Stampqrcode stampqrcodeByStampqrcodeId;

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
    @Column(name = "stampqrcodeId", nullable = false)
    public Integer getStampqrcodeId() {
        return stampqrcodeId;
    }

    public void setStampqrcodeId(Integer stampqrcodeId) {
        this.stampqrcodeId = stampqrcodeId;
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
        Workflowstampqrcode that = (Workflowstampqrcode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(workflowId, that.workflowId) &&
                Objects.equals(stampqrcodeId, that.stampqrcodeId) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workflowId, stampqrcodeId, createAt, active, deleted, observation);
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
    @JoinColumn(name = "stampqrcodeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Stampqrcode getStampqrcodeByStampqrcodeId() {
        return stampqrcodeByStampqrcodeId;
    }

    public void setStampqrcodeByStampqrcodeId(Stampqrcode stampqrcodeByStampqrcodeId) {
        this.stampqrcodeByStampqrcodeId = stampqrcodeByStampqrcodeId;
    }
}
