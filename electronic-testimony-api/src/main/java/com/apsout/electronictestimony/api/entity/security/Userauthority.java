package com.apsout.electronictestimony.api.entity.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "userauthority")
public class Userauthority {
    private Integer id;
    private Integer userId;
    private Integer authorityId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private User userByUserId;
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
    @Column(name = "userId", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        Userauthority that = (Userauthority) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(authorityId, that.authorityId) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, authorityId, createAt, active, deleted, observation);
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
    @JoinColumn(name = "authorityId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Authority getAuthorityByAuthorityId() {
        return authorityByAuthorityId;
    }

    public void setAuthorityByAuthorityId(Authority authorityByAuthorityId) {
        this.authorityByAuthorityId = authorityByAuthorityId;
    }
}
