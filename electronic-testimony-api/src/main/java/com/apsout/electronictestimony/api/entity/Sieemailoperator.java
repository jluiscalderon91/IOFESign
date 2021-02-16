package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sieemailoperator")
public class Sieemailoperator {
    private Integer id;
    private Integer sieemailId;
    private Integer operatorId;
    private Byte active;
    private Byte deleted;
    private Timestamp createAt;
    private String observation;
    private Sieemail sieemailBySieemailId;
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
    @Column(name = "sieemailId", nullable = false)
    public Integer getSieemailId() {
        return sieemailId;
    }

    public void setSieemailId(Integer sieemailId) {
        this.sieemailId = sieemailId;
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
    @Column(name = "createAt", nullable = true)
    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
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
        Sieemailoperator that = (Sieemailoperator) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sieemailId, that.sieemailId) &&
                Objects.equals(operatorId, that.operatorId) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sieemailId, operatorId, active, deleted, createAt, observation);
    }

    @ManyToOne
    @JoinColumn(name = "sieemailId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Sieemail getSieemailBySieemailId() {
        return sieemailBySieemailId;
    }

    public void setSieemailBySieemailId(Sieemail sieemailBySieemailId) {
        this.sieemailBySieemailId = sieemailBySieemailId;
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
