package com.apsout.electronictestimony.api.entity.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "token")
public class Token {
    private Integer id;
    private String value;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Collection<Usertoken> usertokensById;

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
    @Column(name = "value", nullable = true, length = 256)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        Token token = (Token) o;
        return Objects.equals(id, token.id) &&
                Objects.equals(value, token.value) &&
                Objects.equals(createAt, token.createAt) &&
                Objects.equals(active, token.active) &&
                Objects.equals(deleted, token.deleted) &&
                Objects.equals(observation, token.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "tokenByTokenId")
    public Collection<Usertoken> getUsertokensById() {
        return usertokensById;
    }

    public void setUsertokensById(Collection<Usertoken> usertokensById) {
        this.usertokensById = usertokensById;
    }
}
