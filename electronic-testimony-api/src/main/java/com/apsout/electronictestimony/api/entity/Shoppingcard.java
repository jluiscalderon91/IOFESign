package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "shoppingcard")
public class Shoppingcard {
    private Integer id;
    @JsonIgnore
    private Integer partnerId;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private Integer orderCard;
    private Timestamp createAt;
    private Byte active;
    @JsonIgnore
    private Byte deleted;
    @JsonIgnore
    private String observation;
    @JsonIgnore
    private Partner partnerByPartnerId;

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
    @Column(name = "partnerId", nullable = false)
    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 128)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "orderCard", nullable = true)
    public Integer getOrderCard() {
        return orderCard;
    }

    public void setOrderCard(Integer orderCard) {
        this.orderCard = orderCard;
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
        Shoppingcard that = (Shoppingcard) o;
        return Objects.equals(id, that.id) && Objects.equals(partnerId, that.partnerId) && Objects.equals(description, that.description) && Objects.equals(quantity, that.quantity) && Objects.equals(price, that.price) && Objects.equals(orderCard, that.orderCard) && Objects.equals(createAt, that.createAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partnerId, description, quantity, price, orderCard, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "partnerId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Partner getPartnerByPartnerId() {
        return partnerByPartnerId;
    }

    public void setPartnerByPartnerId(Partner partnerByPartnerId) {
        this.partnerByPartnerId = partnerByPartnerId;
    }
}
