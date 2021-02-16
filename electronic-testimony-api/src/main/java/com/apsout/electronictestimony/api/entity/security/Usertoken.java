package com.apsout.electronictestimony.api.entity.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "usertoken")
public class Usertoken {
    private Integer id;
    private Integer userId;
    private Integer tokenId;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private User userByUserId;
    private Token tokenByTokenId;

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
    @Column(name = "tokenId", nullable = false)
    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usertoken usertoken = (Usertoken) o;
        return Objects.equals(id, usertoken.id) &&
                Objects.equals(userId, usertoken.userId) &&
                Objects.equals(tokenId, usertoken.tokenId) &&
                Objects.equals(createAt, usertoken.createAt) &&
                Objects.equals(active, usertoken.active) &&
                Objects.equals(deleted, usertoken.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, tokenId, createAt, active, deleted);
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
    @JoinColumn(name = "tokenId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Token getTokenByTokenId() {
        return tokenByTokenId;
    }

    public void setTokenByTokenId(Token tokenByTokenId) {
        this.tokenByTokenId = tokenByTokenId;
    }
}
