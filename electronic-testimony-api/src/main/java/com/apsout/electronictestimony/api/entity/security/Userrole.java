package com.apsout.electronictestimony.api.entity.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "userrole")
public class Userrole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Byte onlyApi;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private User userByUserId;
    private Role roleByRoleId;

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
    @Column(name = "userId", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "onlyApi", nullable = true)
    public Byte getOnlyApi() {
        return onlyApi;
    }

    public void setOnlyApi(Byte onlyApi) {
        this.onlyApi = onlyApi;
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
        Userrole userrole = (Userrole) o;
        return Objects.equals(id, userrole.id) &&
                Objects.equals(userId, userrole.userId) &&
                Objects.equals(roleId, userrole.roleId) &&
                Objects.equals(onlyApi, userrole.onlyApi) &&
                Objects.equals(createAt, userrole.createAt) &&
                Objects.equals(active, userrole.active) &&
                Objects.equals(deleted, userrole.deleted) &&
                Objects.equals(observation, userrole.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleId, onlyApi, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}
