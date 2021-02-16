package com.apsout.electronictestimony.api.entity.security;

import com.apsout.electronictestimony.api.entity.Authenticationattempt;
import com.apsout.electronictestimony.api.entity.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    private Integer id;
    private Integer personId;
    private String username;
    private Integer nextUserNumber;
    private String password;
    private Timestamp createAt;
    private Byte sentCreds;
    private Timestamp sentCredsAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Authenticationattempt> authenticationattemptsById;
    private Person personByPersonId;
    private Collection<Userauthority> userauthoritiesById;
    private Collection<Userrole> userrolesById;
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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "nextUserNumber", nullable = true)
    public Integer getNextUserNumber() {
        return nextUserNumber;
    }

    public void setNextUserNumber(Integer nextUserNumber) {
        this.nextUserNumber = nextUserNumber;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 124)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "sentCreds", nullable = true)
    public Byte getSentCreds() {
        return sentCreds;
    }

    public void setSentCreds(Byte sentCreds) {
        this.sentCreds = sentCreds;
    }

    @Basic
    @Column(name = "sentCredsAt", nullable = true)
    public Timestamp getSentCredsAt() {
        return sentCredsAt;
    }

    public void setSentCredsAt(Timestamp sentCredsAt) {
        this.sentCredsAt = sentCredsAt;
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
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(personId, user.personId) &&
                Objects.equals(username, user.username) &&
                Objects.equals(nextUserNumber, user.nextUserNumber) &&
                Objects.equals(password, user.password) &&
                Objects.equals(createAt, user.createAt) &&
                Objects.equals(sentCreds, user.sentCreds) &&
                Objects.equals(sentCredsAt, user.sentCredsAt) &&
                Objects.equals(active, user.active) &&
                Objects.equals(deleted, user.deleted) &&
                Objects.equals(observation, user.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, username, nextUserNumber, password, createAt, sentCreds, sentCredsAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Authenticationattempt> getAuthenticationattemptsById() {
        return authenticationattemptsById;
    }

    public void setAuthenticationattemptsById(Collection<Authenticationattempt> authenticationattemptsById) {
        this.authenticationattemptsById = authenticationattemptsById;
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Userauthority> getUserauthoritiesById() {
        return userauthoritiesById;
    }

    public void setUserauthoritiesById(Collection<Userauthority> userauthoritiesById) {
        this.userauthoritiesById = userauthoritiesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Userrole> getUserrolesById() {
        return userrolesById;
    }

    public void setUserrolesById(Collection<Userrole> userrolesById) {
        this.userrolesById = userrolesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Usertoken> getUsertokensById() {
        return usertokensById;
    }

    public void setUsertokensById(Collection<Usertoken> usertokensById) {
        this.usertokensById = usertokensById;
    }
}
