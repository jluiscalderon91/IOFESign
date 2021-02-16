package com.apsout.electronictestimony.api.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "authority")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authority {
    private Integer id;
    private String code;
    private String description;
    private Integer module;
    private Byte onlySuperadmin;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Roleauthority> roleauthoritiesById;
    @JsonIgnore
    private Collection<Userauthority> userauthoritiesById;

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
    @Column(name = "code", nullable = true, length = 64)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 192)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "module", nullable = true)
    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    @Basic
    @Column(name = "onlySuperadmin", nullable = true)
    public Byte getOnlySuperadmin() {
        return onlySuperadmin;
    }

    public void setOnlySuperadmin(Byte onlySuperadmin) {
        this.onlySuperadmin = onlySuperadmin;
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
        Authority authority = (Authority) o;
        return Objects.equals(id, authority.id) &&
                Objects.equals(code, authority.code) &&
                Objects.equals(description, authority.description) &&
                Objects.equals(module, authority.module) &&
                Objects.equals(onlySuperadmin, authority.onlySuperadmin) &&
                Objects.equals(createAt, authority.createAt) &&
                Objects.equals(active, authority.active) &&
                Objects.equals(deleted, authority.deleted) &&
                Objects.equals(observation, authority.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, module, onlySuperadmin, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "authorityByAuthorityId")
    public Collection<Roleauthority> getRoleauthoritiesById() {
        return roleauthoritiesById;
    }

    public void setRoleauthoritiesById(Collection<Roleauthority> roleauthoritiesById) {
        this.roleauthoritiesById = roleauthoritiesById;
    }

    @OneToMany(mappedBy = "authorityByAuthorityId")
    public Collection<Userauthority> getUserauthoritiesById() {
        return userauthoritiesById;
    }

    public void setUserauthoritiesById(Collection<Userauthority> userauthoritiesById) {
        this.userauthoritiesById = userauthoritiesById;
    }
}
