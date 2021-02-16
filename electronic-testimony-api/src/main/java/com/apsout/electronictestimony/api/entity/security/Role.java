package com.apsout.electronictestimony.api.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private Integer id;
    private String name;
    private String abbreviation;
    private String description;
    private String nameView;
    private Byte editable;
    private Integer orderRole;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Roleauthority> roleauthoritiesById;
    @JsonIgnore
    private Collection<Userrole> userrolesById;

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
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "abbreviation", nullable = true, length = 32)
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 128)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "nameView", nullable = true, length = 32)
    public String getNameView() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView = nameView;
    }

    @Basic
    @Column(name = "editable")
    public Byte getEditable() {
        return editable;
    }

    public void setEditable(Byte editable) {
        this.editable = editable;
    }

    @Basic
    @Column(name = "orderRole")
    public Integer getOrderRole() {
        return orderRole;
    }

    public void setOrderRole(Integer orderRole) {
        this.orderRole = orderRole;
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
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(abbreviation, role.abbreviation) &&
                Objects.equals(description, role.description) &&
                Objects.equals(editable, role.editable) &&
                Objects.equals(createAt, role.createAt) &&
                Objects.equals(active, role.active) &&
                Objects.equals(deleted, role.deleted) &&
                Objects.equals(observation, role.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, abbreviation, description, editable, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<Roleauthority> getRoleauthoritiesById() {
        return roleauthoritiesById;
    }

    public void setRoleauthoritiesById(Collection<Roleauthority> roleauthoritiesById) {
        this.roleauthoritiesById = roleauthoritiesById;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<Userrole> getUserrolesById() {
        return userrolesById;
    }

    public void setUserrolesById(Collection<Userrole> userrolesById) {
        this.userrolesById = userrolesById;
    }
}
