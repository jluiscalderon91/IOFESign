package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumentmodification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "documentmodification")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Documentmodification {
    private Integer id;
    private Integer personId;
    private Integer documentIdNew;
    private Integer documentIdOld;
    private String description;
    private Timestamp createAt;
    private Boolean active;
    private Boolean deleted;
    private String observation;
    private Person personByPersonId;
    private Document documentByDocumentIdNew;
    private Document documentByDocumentIdOld;
    @JsonIgnore
    private Collection<Historicaldocumentmodification> historicaldocumentmodificationsById;
    private MoreAboutDocumentmodification moreAboutDocumentmodification;

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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "documentIdNew", nullable = false)
    public Integer getDocumentIdNew() {
        return documentIdNew;
    }

    public void setDocumentIdNew(Integer documentIdNew) {
        this.documentIdNew = documentIdNew;
    }

    @Basic
    @Column(name = "documentIdOld", nullable = false)
    public Integer getDocumentIdOld() {
        return documentIdOld;
    }

    public void setDocumentIdOld(Integer documentIdOld) {
        this.documentIdOld = documentIdOld;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Documentmodification that = (Documentmodification) o;
        return Objects.equals(id, that.id) && Objects.equals(personId, that.personId) && Objects.equals(documentIdNew, that.documentIdNew) && Objects.equals(documentIdOld, that.documentIdOld) && Objects.equals(description, that.description) && Objects.equals(createAt, that.createAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, documentIdNew, documentIdOld, description, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "documentIdNew", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentIdNew() {
        return documentByDocumentIdNew;
    }

    public void setDocumentByDocumentIdNew(Document documentByDocumentIdNew) {
        this.documentByDocumentIdNew = documentByDocumentIdNew;
    }

    @ManyToOne
    @JoinColumn(name = "documentIdOld", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentIdOld() {
        return documentByDocumentIdOld;
    }

    public void setDocumentByDocumentIdOld(Document documentByDocumentIdOld) {
        this.documentByDocumentIdOld = documentByDocumentIdOld;
    }

    @OneToMany(mappedBy = "documentmodificationByDocumentmodificationId")
    public Collection<Historicaldocumentmodification> getHistoricaldocumentmodificationsById() {
        return historicaldocumentmodificationsById;
    }

    public void setHistoricaldocumentmodificationsById(Collection<Historicaldocumentmodification> historicaldocumentmodificationsById) {
        this.historicaldocumentmodificationsById = historicaldocumentmodificationsById;
    }

    @Transient
    public MoreAboutDocumentmodification getMoreAboutDocumentmodification() {
        return moreAboutDocumentmodification;
    }

    public void setMoreAboutDocumentmodification(MoreAboutDocumentmodification moreAboutDocumentmodification) {
        this.moreAboutDocumentmodification = moreAboutDocumentmodification;
    }
}
