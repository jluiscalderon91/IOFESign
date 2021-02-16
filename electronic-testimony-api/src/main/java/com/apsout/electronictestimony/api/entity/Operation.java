package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "operation")
public class Operation {
    private Integer id;
    private String description;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Operator> operatorsById;
    @JsonIgnore
    private Collection<Participant> participantsById;

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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) &&
                Objects.equals(description, operation.description) &&
                Objects.equals(createAt, operation.createAt) &&
                Objects.equals(active, operation.active) &&
                Objects.equals(deleted, operation.deleted) &&
                Objects.equals(observation, operation.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "operationByOperationId")
    public Collection<Operator> getOperatorsById() {
        return operatorsById;
    }

    public void setOperatorsById(Collection<Operator> operatorsById) {
        this.operatorsById = operatorsById;
    }

    @OneToMany(mappedBy = "operationByOperationId")
    public Collection<Participant> getParticipantsById() {
        return participantsById;
    }

    public void setParticipantsById(Collection<Participant> participantsById) {
        this.participantsById = participantsById;
    }
}
