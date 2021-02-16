package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "fontcolor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fontcolor {
    private Integer id;
    private String description;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;

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
    @Column(name = "description", nullable = true, length = 32)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Fontcolor fontcolor = (Fontcolor) o;
        return Objects.equals(id, fontcolor.id) &&
                Objects.equals(description, fontcolor.description) &&
                Objects.equals(createAt, fontcolor.createAt) &&
                Objects.equals(active, fontcolor.active) &&
                Objects.equals(deleted, fontcolor.deleted) &&
                Objects.equals(observation, fontcolor.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createAt, active, deleted, observation);
    }
}
