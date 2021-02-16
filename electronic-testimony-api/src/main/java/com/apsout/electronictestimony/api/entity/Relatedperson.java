package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "relatedperson")
public class Relatedperson {
    private Integer id;
    private Integer personId;
    private Integer personIdRelated;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Person personByPersonId;
    private Person personByPersonIdRelated;

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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "personIdRelated", nullable = false)
    public Integer getPersonIdRelated() {
        return personIdRelated;
    }

    public void setPersonIdRelated(Integer personIdRelated) {
        this.personIdRelated = personIdRelated;
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
        Relatedperson that = (Relatedperson) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(personIdRelated, that.personIdRelated) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, personIdRelated, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "personIdRelated", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonIdRelated() {
        return personByPersonIdRelated;
    }

    public void setPersonByPersonIdRelated(Person personByPersonIdRelated) {
        this.personByPersonIdRelated = personByPersonIdRelated;
    }
}
