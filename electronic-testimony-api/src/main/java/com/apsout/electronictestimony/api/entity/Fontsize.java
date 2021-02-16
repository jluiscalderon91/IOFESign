package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "fontsize")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fontsize {
    private Integer id;
    private Double size;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "size", nullable = true)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
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
        Fontsize fontsize = (Fontsize) o;
        return Objects.equals(id, fontsize.id) &&
                Objects.equals(size, fontsize.size) &&
                Objects.equals(createAt, fontsize.createAt) &&
                Objects.equals(active, fontsize.active) &&
                Objects.equals(deleted, fontsize.deleted) &&
                Objects.equals(observation, fontsize.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, createAt, active, deleted, observation);
    }
}
