package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDetailbalance;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "detailbalanceallocation")
public class Detailbalanceallocation {
    private Integer id;
    private Integer headbalanceallocationId;
    private Integer documentId;
    private Integer personId;
    private BigDecimal oldBalance;
    private Integer serviceweightId;
    private BigDecimal weight;
    private BigDecimal actualBalance;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Headbalanceallocation headbalanceallocationByHeadbalanceallocationId;
    private Document documentByDocumentId;
    private Person personByPersonId;
    private MoreAboutDetailbalance moreAboutDetailbalance;

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
    @Column(name = "headbalanceallocationId", nullable = false)
    public Integer getHeadbalanceallocationId() {
        return headbalanceallocationId;
    }

    public void setHeadbalanceallocationId(Integer headbalanceallocationId) {
        this.headbalanceallocationId = headbalanceallocationId;
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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "oldBalance", nullable = true)
    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    @Basic
    @Column(name = "serviceweightId", nullable = true)
    public Integer getServiceweightId() {
        return serviceweightId;
    }

    public void setServiceweightId(Integer serviceweightId) {
        this.serviceweightId = serviceweightId;
    }

    @Basic
    @Column(name = "weight", nullable = true)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "actualBalance", nullable = true)
    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
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
        Detailbalanceallocation that = (Detailbalanceallocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(headbalanceallocationId, that.headbalanceallocationId) &&
                Objects.equals(documentId, that.documentId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(oldBalance, that.oldBalance) &&
                Objects.equals(serviceweightId, that.serviceweightId) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(actualBalance, that.actualBalance) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, headbalanceallocationId, documentId, personId, oldBalance, serviceweightId, weight, actualBalance, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "headbalanceallocationId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Headbalanceallocation getHeadbalanceallocationByHeadbalanceallocationId() {
        return headbalanceallocationByHeadbalanceallocationId;
    }

    public void setHeadbalanceallocationByHeadbalanceallocationId(Headbalanceallocation headbalanceallocationByHeadbalanceallocationId) {
        this.headbalanceallocationByHeadbalanceallocationId = headbalanceallocationByHeadbalanceallocationId;
    }

    @ManyToOne
    @JoinColumn(name = "documentId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Document getDocumentByDocumentId() {
        return documentByDocumentId;
    }

    public void setDocumentByDocumentId(Document documentByDocumentId) {
        this.documentByDocumentId = documentByDocumentId;
    }

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @Transient
    public MoreAboutDetailbalance getMoreAboutDetailbalance() {
        return moreAboutDetailbalance;
    }

    public void setMoreAboutDetailbalance(MoreAboutDetailbalance moreAboutDetailbalance) {
        this.moreAboutDetailbalance = moreAboutDetailbalance;
    }
}
