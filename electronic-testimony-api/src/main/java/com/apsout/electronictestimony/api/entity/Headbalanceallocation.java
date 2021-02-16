package com.apsout.electronictestimony.api.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "headbalanceallocation")
public class Headbalanceallocation {
    private Integer id;
    private Integer enterpriseId;
    private BigDecimal quantity;
    private BigDecimal balance;
    private Timestamp lastUpdateAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Collection<Detailbalanceallocation> detailbalanceallocationsById;
    private Enterprise enterpriseByEnterpriseId;
    private Collection<Historicalbalanceallocation> historicalbalanceallocationsById;

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
    @Column(name = "enterpriseId", nullable = false)
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
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
    @Column(name = "lastUpdateAt", nullable = true)
    public Timestamp getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Timestamp lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
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
        Headbalanceallocation that = (Headbalanceallocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(enterpriseId, that.enterpriseId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(lastUpdateAt, that.lastUpdateAt) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enterpriseId, quantity, balance, lastUpdateAt, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "headbalanceallocationByHeadbalanceallocationId")
    public Collection<Detailbalanceallocation> getDetailbalanceallocationsById() {
        return detailbalanceallocationsById;
    }

    public void setDetailbalanceallocationsById(Collection<Detailbalanceallocation> detailbalanceallocationsById) {
        this.detailbalanceallocationsById = detailbalanceallocationsById;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @OneToMany(mappedBy = "headbalanceallocationByHeadbalanceallocationId")
    public Collection<Historicalbalanceallocation> getHistoricalbalanceallocationsById() {
        return historicalbalanceallocationsById;
    }

    public void setHistoricalbalanceallocationsById(Collection<Historicalbalanceallocation> historicalbalanceallocationsById) {
        this.historicalbalanceallocationsById = historicalbalanceallocationsById;
    }
}
