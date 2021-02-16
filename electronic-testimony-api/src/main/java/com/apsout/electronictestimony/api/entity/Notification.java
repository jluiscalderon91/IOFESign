package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification {
    private Integer id;
    private Integer operatorId;
    private Integer type;
    private Integer priority;
    private Byte enabled;
    private Byte sent;
    private Timestamp sentAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Operator operatorByOperatorId;

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
    @Column(name = "operatorId", nullable = false)
    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "priority", nullable = true)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "enabled", nullable = true)
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "sent", nullable = true)
    public Byte getSent() {
        return sent;
    }

    public void setSent(Byte sent) {
        this.sent = sent;
    }

    @Basic
    @Column(name = "sentAt", nullable = true)
    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sendedAt) {
        this.sentAt = sendedAt;
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
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operatorId, that.operatorId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(sent, that.sent) &&
                Objects.equals(sentAt, that.sentAt) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operatorId, type, priority, enabled, sent, sentAt, createAt, active, deleted, observation);
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
