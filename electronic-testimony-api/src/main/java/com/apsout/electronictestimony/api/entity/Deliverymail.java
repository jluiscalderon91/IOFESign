package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "deliverymail")
public class Deliverymail {
    private Integer id;
    private Integer documentId;
    private Integer priority;
    private Boolean sent;
    private Timestamp sentAt;
    private Timestamp lastAttemptAt;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Collection<Contentdeliverymail> contentdeliverymailsById;
    private Document documentByDocumentId;
    private Contentdeliverymail contentdeliverymail;

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
    @Column(name = "documentId", nullable = false)
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
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
    @Column(name = "sent", nullable = true)
    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
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
    @Column(name = "lastAttemptAt", nullable = true)
    public Timestamp getLastAttemptAt() {
        return lastAttemptAt;
    }

    public void setLastAttemptAt(Timestamp lastAttemptAt) {
        this.lastAttemptAt = lastAttemptAt;
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
    @Column(name = "observation", nullable = true, length = 45)
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
        Deliverymail that = (Deliverymail) o;
        return Objects.equals(id, that.id) && Objects.equals(documentId, that.documentId) && Objects.equals(priority, that.priority) && Objects.equals(sent, that.sent) && Objects.equals(sentAt, that.sentAt) && Objects.equals(lastAttemptAt, that.lastAttemptAt) && Objects.equals(createAt, that.createAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, priority, sent, sentAt, lastAttemptAt, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "deliverymailByDeliverymailId")
    public Collection<Contentdeliverymail> getContentdeliverymailsById() {
        return contentdeliverymailsById;
    }

    public void setContentdeliverymailsById(Collection<Contentdeliverymail> contentdeliverymailsById) {
        this.contentdeliverymailsById = contentdeliverymailsById;
    }

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentId() {
        return documentByDocumentId;
    }

    public void setDocumentByDocumentId(Document documentByDocumentId) {
        this.documentByDocumentId = documentByDocumentId;
    }

    @Transient
    public Contentdeliverymail getContentdeliverymail() {
        return contentdeliverymail;
    }

    public void setContentdeliverymail(Contentdeliverymail contentdeliverymail) {
        this.contentdeliverymail = contentdeliverymail;
    }
}
