package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "done")
public class Done {
    private Integer id;
    private Integer assignerId;
    private Integer resourceId;
    private Byte observed;
    private String comment;
    private Timestamp signedAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private Byte hasTsa;
    private String observation;
    private Assigner assignerByAssignerId;
    private Resource resourceByResourceId;

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
    @Column(name = "assignerId")
    public Integer getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(Integer assignerId) {
        this.assignerId = assignerId;
    }

    @Basic
    @Column(name = "resourceId")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "observed")
    public Byte getObserved() {
        return observed;
    }

    public void setObserved(Byte observed) {
        this.observed = observed;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String signatureNumber) {
        this.comment = signatureNumber;
    }

    @Basic
    @Column(name = "hasTsa")
    public Byte getHasTsa() {
        return hasTsa;
    }

    public void setHasTsa(Byte hasTsa) {
        this.hasTsa = hasTsa;
    }

    @Basic
    @Column(name = "signedAt")
    public Timestamp getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(Timestamp signedAt) {
        this.signedAt = signedAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Done done = (Done) o;
        return Objects.equals(id, done.id) &&
                Objects.equals(assignerId, done.assignerId) &&
                Objects.equals(resourceId, done.resourceId) &&
                Objects.equals(comment, done.comment) &&
                Objects.equals(hasTsa, done.hasTsa) &&
                Objects.equals(signedAt, done.signedAt) &&
                Objects.equals(createAt, done.createAt) &&
                Objects.equals(active, done.active) &&
                Objects.equals(deleted, done.deleted) &&
                Objects.equals(observation, done.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignerId, resourceId, comment, hasTsa, signedAt, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "assignerId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Assigner getAssignerByAssignerId() {
        return assignerByAssignerId;
    }

    public void setAssignerByAssignerId(Assigner assignerByAssignerId) {
        this.assignerByAssignerId = assignerByAssignerId;
    }

    @ManyToOne
    @JoinColumn(name = "resourceId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Resource getResourceByResourceId() {
        return resourceByResourceId;
    }

    public void setResourceByResourceId(Resource resourceByResourceId) {
        this.resourceByResourceId = resourceByResourceId;
    }
}
