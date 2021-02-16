package com.apsout.electronictestimony.api.entity.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "roleauthority")
public class Roleauthority {
    private Integer id;
    private Integer roleId;
    private Integer authorityId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Role roleByRoleId;
    private Authority authorityByAuthorityId;

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
    @Column(name = "roleId", nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "authorityId", nullable = false)
    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
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
        Roleauthority that = (Roleauthority) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(roleId, that.roleId) &&
                Objects.equals(authorityId, that.authorityId) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, authorityId, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "authorityId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Authority getAuthorityByAuthorityId() {
        return authorityByAuthorityId;
    }

    public void setAuthorityByAuthorityId(Authority authorityByAuthorityId) {
        this.authorityByAuthorityId = authorityByAuthorityId;
    }
}
