package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumenttraceability;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "documenttraceability")
public class Documenttraceability {
    private Integer id;
    private Integer documentId;
    private Integer stateId;
    private Integer personId;
    private Byte visible;
    private Integer type;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private MoreAboutDocumenttraceability moreAboutDocumenttraceability;

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
    @Column(name = "documentId", nullable = true)
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "stateId", nullable = true)
    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "personId", nullable = true)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "visible", nullable = true)
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        Documenttraceability that = (Documenttraceability) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(documentId, that.documentId) &&
                Objects.equals(stateId, that.stateId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, stateId, personId, visible, type, createAt, active, deleted, observation);
    }

    @Transient
    public MoreAboutDocumenttraceability getMoreAboutDocumenttraceability() {
        return moreAboutDocumenttraceability;
    }

    public void setMoreAboutDocumenttraceability(MoreAboutDocumenttraceability moreAboutDocumenttraceability) {
        this.moreAboutDocumenttraceability = moreAboutDocumenttraceability;
    }
}
