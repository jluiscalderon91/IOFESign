package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutDocument;
import com.apsout.electronictestimony.api.entity.common.MoreAboutHistoricalbalanceallocation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "historicalbalanceallocation")
public class Historicalbalanceallocation {
    private Integer id;
    private Integer headbalanceallocationId;
    private Integer personId;
    private Integer enterpriseIdAction;
    private BigDecimal quantity;
    private BigDecimal balance;
    private Byte isReturn;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Headbalanceallocation headbalanceallocationByHeadbalanceallocationId;
    private Person personByPersonId;
    private MoreAboutHistoricalbalanceallocation moreAboutHistoricalbalanceallocation;

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
    @Column(name = "personId", nullable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "enterpriseIdAction", nullable = true)
    public Integer getEnterpriseIdAction() {
        return enterpriseIdAction;
    }

    public void setEnterpriseIdAction(Integer enterpriseIdAction) {
        this.enterpriseIdAction = enterpriseIdAction;
    }

    @Basic
    @Column(name = "quantity", nullable = true, precision = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }


    @Basic
    @Column(name = "balance", nullable = true, precision = 2)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "isReturn", nullable = true)
    public Byte getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Byte isReturn) {
        this.isReturn = isReturn;
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
        Historicalbalanceallocation that = (Historicalbalanceallocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(headbalanceallocationId, that.headbalanceallocationId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(enterpriseIdAction, that.enterpriseIdAction) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(isReturn, that.isReturn) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, headbalanceallocationId, personId, enterpriseIdAction, quantity, balance, isReturn, createAt, active, deleted, observation);
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
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @Transient
    public MoreAboutHistoricalbalanceallocation getMoreAboutHistoricalbalanceallocation() {
        return moreAboutHistoricalbalanceallocation;
    }

    public void setMoreAboutHistoricalbalanceallocation(MoreAboutHistoricalbalanceallocation moreAboutHistoricalbalanceallocation) {
        this.moreAboutHistoricalbalanceallocation = moreAboutHistoricalbalanceallocation;
    }
}
