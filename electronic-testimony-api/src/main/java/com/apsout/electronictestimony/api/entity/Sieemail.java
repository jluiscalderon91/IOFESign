package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "sieemail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sieemail {
    private Integer id;
    private Integer participantId;
    private String subject;
    private String body;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Participant participantByParticipantId;
    private Collection<Sieemailoperator> sieemailoperatorsById;
    private Collection<Sierecipient> sierecipientsById;

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
    @Column(name = "participantId", nullable = false)
    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    @Basic
    @Column(name = "subject", nullable = true, length = 128)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "body", nullable = true, length = 1024)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
        Sieemail sieemail = (Sieemail) o;
        return Objects.equals(id, sieemail.id) &&
                Objects.equals(participantId, sieemail.participantId) &&
                Objects.equals(subject, sieemail.subject) &&
                Objects.equals(body, sieemail.body) &&
                Objects.equals(createAt, sieemail.createAt) &&
                Objects.equals(active, sieemail.active) &&
                Objects.equals(deleted, sieemail.deleted) &&
                Objects.equals(observation, sieemail.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, participantId, subject, body, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "participantId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Participant getParticipantByParticipantId() {
        return participantByParticipantId;
    }

    public void setParticipantByParticipantId(Participant participantByParticipantId) {
        this.participantByParticipantId = participantByParticipantId;
    }

    @OneToMany(mappedBy = "sieemailBySieemailId")
    public Collection<Sieemailoperator> getSieemailoperatorsById() {
        return sieemailoperatorsById;
    }

    public void setSieemailoperatorsById(Collection<Sieemailoperator> sieemailoperatorsById) {
        this.sieemailoperatorsById = sieemailoperatorsById;
    }

    @OneToMany(mappedBy = "sieemailBySieemailId")
    public Collection<Sierecipient> getSierecipientsById() {
        return sierecipientsById;
    }

    public void setSierecipientsById(Collection<Sierecipient> sierecipientsById) {
        this.sierecipientsById = sierecipientsById;
    }
}
