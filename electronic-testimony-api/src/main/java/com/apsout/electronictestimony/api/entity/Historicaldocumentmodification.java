package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "historicaldocumentmodification")
public class Historicaldocumentmodification {
    private Integer id;
    private Integer documentmodificationId;
    private Integer documentId;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Documentmodification documentmodificationByDocumentmodificationId;
    private Document documentByDocumentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "documentmodificationId", nullable = false)
    public Integer getDocumentmodificationId() {
        return documentmodificationId;
    }

    public void setDocumentmodificationId(Integer documentmodificationId) {
        this.documentmodificationId = documentmodificationId;
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
        Historicaldocumentmodification that = (Historicaldocumentmodification) o;
        return Objects.equals(id, that.id) && Objects.equals(documentmodificationId, that.documentmodificationId) && Objects.equals(documentId, that.documentId) && Objects.equals(createAt, that.createAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentmodificationId, documentId, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "documentmodificationId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Documentmodification getDocumentmodificationByDocumentmodificationId() {
        return documentmodificationByDocumentmodificationId;
    }

    public void setDocumentmodificationByDocumentmodificationId(Documentmodification documentmodificationByDocumentmodificationId) {
        this.documentmodificationByDocumentmodificationId = documentmodificationByDocumentmodificationId;
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
