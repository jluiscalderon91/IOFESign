package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sierecipient")
public class Sierecipient {
    private Integer id;
    private Integer sieemailId;
    private String address;
    @JsonIgnore
    private String documentNumber;
    @JsonIgnore
    private Timestamp createAt;
    @JsonIgnore
    private Byte active;
    @JsonIgnore
    private Byte deleted;
    @JsonIgnore
    private String observation;
    @JsonIgnore
    private Sieemail sieemailBySieemailId;

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
    @Column(name = "address", nullable = true, length = 64)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "documentNumber", nullable = true, length = 16)
    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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
        Sierecipient that = (Sierecipient) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sieemailId, that.sieemailId) &&
                Objects.equals(address, that.address) &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sieemailId, address, documentNumber, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "sieemailId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Sieemail getSieemailBySieemailId() {
        return sieemailBySieemailId;
    }

    public void setSieemailBySieemailId(Sieemail sieemailBySieemailId) {
        this.sieemailBySieemailId = sieemailBySieemailId;
    }
}
