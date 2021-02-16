package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "delivery")
public class Delivery {
    private Integer id;
    private Integer documentId;
    private Integer priority;
    private Byte sent;
    private Timestamp sentAt;
    private String requestBody;
    private String response;
    private String responseCode;
    private String responseDescription;
    private Timestamp lastAttemptAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Document documentByDocumentId;

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
    @Column(name = "responseCode", nullable = true)
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Basic
    @Column(name = "requestBody", nullable = true)
    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Basic
    @Column(name = "response", nullable = true)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "responseDescription", nullable = true)
    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
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
    @Column(name = "observation", nullable = true)
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
        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id) &&
                Objects.equals(documentId, delivery.documentId) &&
                Objects.equals(priority, delivery.priority) &&
                Objects.equals(sent, delivery.sent) &&
                Objects.equals(sentAt, delivery.sentAt) &&
                Objects.equals(responseCode, delivery.responseCode) &&
                Objects.equals(responseDescription, delivery.responseDescription) &&
                Objects.equals(createAt, delivery.createAt) &&
                Objects.equals(active, delivery.active) &&
                Objects.equals(deleted, delivery.deleted) &&
                Objects.equals(observation, delivery.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, priority, sent, sentAt, responseCode, responseDescription, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentId() {
        return documentByDocumentId;
    }

    public void setDocumentByDocumentId(Document documentByDocumentId) {
        this.documentByDocumentId = documentByDocumentId;
    }
}
