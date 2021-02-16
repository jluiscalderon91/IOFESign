package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "serviceweight")
public class Serviceweight {
    private Integer id;
    private Integer serviceId;
    private BigDecimal weight;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Service serviceByServiceId;

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
    @Column(name = "serviceId", nullable = false)
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "weight", nullable = true, precision = 2)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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
        Serviceweight that = (Serviceweight) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceId, weight, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "serviceId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Service getServiceByServiceId() {
        return serviceByServiceId;
    }

    public void setServiceByServiceId(Service serviceByServiceId) {
        this.serviceByServiceId = serviceByServiceId;
    }
}
