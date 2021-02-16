package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "contentdeliverymail")
public class Contentdeliverymail {
    private Integer id;
    private Integer deliverymailId;
    private String recipient;
    private String cc;
    private Boolean attachFiles;
    private String subject;
    private String body;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Deliverymail deliverymailByDeliverymailId;

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
    @Column(name = "deliverymailId", nullable = false)
    public Integer getDeliverymailId() {
        return deliverymailId;
    }

    public void setDeliverymailId(Integer deliverymailId) {
        this.deliverymailId = deliverymailId;
    }

    @Basic
    @Column(name = "recipient", nullable = true, length = 256)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Basic
    @Column(name = "cc", nullable = true, length = 256)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
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
    @Column(name = "attachFiles", nullable = true)
    public Boolean getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(Boolean attachFiles) {
        this.attachFiles = attachFiles;
    }

    @Basic
    @Column(name = "body", nullable = true, length = -1)
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
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted", nullable = true)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
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
        Contentdeliverymail that = (Contentdeliverymail) o;
        return Objects.equals(id, that.id) && Objects.equals(deliverymailId, that.deliverymailId) && Objects.equals(recipient, that.recipient) && Objects.equals(cc, that.cc) && Objects.equals(subject, that.subject) && Objects.equals(body, that.body) && Objects.equals(createAt, that.createAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliverymailId, recipient, cc, subject, body, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "deliverymailId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Deliverymail getDeliverymailByDeliverymailId() {
        return deliverymailByDeliverymailId;
    }

    public void setDeliverymailByDeliverymailId(Deliverymail deliverymailByDeliverymailId) {
        this.deliverymailByDeliverymailId = deliverymailByDeliverymailId;
    }
}
