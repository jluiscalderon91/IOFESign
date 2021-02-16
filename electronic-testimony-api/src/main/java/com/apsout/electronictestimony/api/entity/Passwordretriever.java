package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "passwordretriever")
public class Passwordretriever {
    private Integer id;
    private String uuid;
    private String username;
    private String email;
    private String hashFirstStep;
    private String verificationCode;
    private Byte sent;
    private Timestamp sentAt;
    private Integer priority;
    private Byte matched;
    private Timestamp matchedAt;
    private String hashSecondStep;
    private Byte finished;
    private Timestamp finishedAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;

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
    @Column(name = "uuid", nullable = true, length = 64)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    @Column(name = "email", nullable = true, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "hashFirstStep", nullable = true, length = 64)
    public String getHashFirstStep() {
        return hashFirstStep;
    }

    public void setHashFirstStep(String hashFirstStep) {
        this.hashFirstStep = hashFirstStep;
    }

    @Basic
    @Column(name = "verificationCode", nullable = true, length = 6)
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Basic
    @Column(name = "sent", nullable = true)
    public Byte getSent() {
        return sent;
    }

    public void setSent(Byte sent) {
        this.sent = sent;
    }

    @Basic
    @Column(name = "sentAt", nullable = true)
    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    @Basic
    @Column(name = "priority", nullable = true)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "matched", nullable = true)
    public Byte getMatched() {
        return matched;
    }

    public void setMatched(Byte matched) {
        this.matched = matched;
    }

    @Basic
    @Column(name = "matchedAt", nullable = true)
    public Timestamp getMatchedAt() {
        return matchedAt;
    }

    public void setMatchedAt(Timestamp matchedAt) {
        this.matchedAt = matchedAt;
    }

    @Basic
    @Column(name = "hashSecondStep", nullable = true, length = 64)
    public String getHashSecondStep() {
        return hashSecondStep;
    }

    public void setHashSecondStep(String hashSecondStep) {
        this.hashSecondStep = hashSecondStep;
    }

    @Basic
    @Column(name = "finished", nullable = true)
    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "finishedAt", nullable = true)
    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
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
        Passwordretriever that = (Passwordretriever) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(hashFirstStep, that.hashFirstStep) &&
                Objects.equals(verificationCode, that.verificationCode) &&
                Objects.equals(sent, that.sent) &&
                Objects.equals(sentAt, that.sentAt) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(matched, that.matched) &&
                Objects.equals(matchedAt, that.matchedAt) &&
                Objects.equals(hashSecondStep, that.hashSecondStep) &&
                Objects.equals(finished, that.finished) &&
                Objects.equals(finishedAt, that.finishedAt) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, username, email, hashFirstStep, verificationCode, sent, sentAt, priority, matched, matchedAt, hashSecondStep, finished, finishedAt, createAt, active, deleted, observation);
    }
}
