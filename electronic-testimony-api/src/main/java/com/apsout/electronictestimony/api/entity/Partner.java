package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "partner")
public class Partner {
    private Integer id;
    private String name;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Enterprise> enterprisesById;
    @JsonIgnore
    private Collection<Person> peopleById;
    @JsonIgnore
    private Collection<Shoppingcard> shoppingcardsById;

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
    @Column(name = "name", nullable = true, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Partner partner = (Partner) o;
        return Objects.equals(id, partner.id) && Objects.equals(name, partner.name) && Objects.equals(createAt, partner.createAt) && Objects.equals(active, partner.active) && Objects.equals(deleted, partner.deleted) && Objects.equals(observation, partner.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "partnerByPartnerId")
    public Collection<Enterprise> getEnterprisesById() {
        return enterprisesById;
    }

    public void setEnterprisesById(Collection<Enterprise> enterprisesById) {
        this.enterprisesById = enterprisesById;
    }

    @OneToMany(mappedBy = "partnerByPartnerId")
    public Collection<Person> getPeopleById() {
        return peopleById;
    }

    public void setPeopleById(Collection<Person> peopleById) {
        this.peopleById = peopleById;
    }

    @OneToMany(mappedBy = "partnerByPartnerId")
    public Collection<Shoppingcard> getShoppingcardsById() {
        return shoppingcardsById;
    }

    public void setShoppingcardsById(Collection<Shoppingcard> shoppingcardsById) {
        this.shoppingcardsById = shoppingcardsById;
    }
}
