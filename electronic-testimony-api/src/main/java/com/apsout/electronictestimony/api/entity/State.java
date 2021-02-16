package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "state")
public class State {
    private Integer id;
    private String longDescription;
    private String shortDescription;
    private Integer orderState;
    private Byte visible;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Document> documentsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "longDescription")
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Basic
    @Column(name = "shortDescription")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Basic
    @Column(name = "orderState")
    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    @Basic
    @Column(name = "visible")
    public Byte getVisible() {
        return visible;
    }

    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "createAt")
    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Basic
    @Column(name = "active")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "observation")
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
        State state = (State) o;
        return Objects.equals(id, state.id) &&
                Objects.equals(longDescription, state.longDescription) &&
                Objects.equals(shortDescription, state.shortDescription) &&
                Objects.equals(visible, state.visible) &&
                Objects.equals(createAt, state.createAt) &&
                Objects.equals(active, state.active) &&
                Objects.equals(deleted, state.deleted) &&
                Objects.equals(observation, state.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longDescription, shortDescription, visible, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "stateByStateId")
    public Collection<Document> getDocumentsById() {
        return documentsById;
    }

    public void setDocumentsById(Collection<Document> documentsById) {
        this.documentsById = documentsById;
    }
}
