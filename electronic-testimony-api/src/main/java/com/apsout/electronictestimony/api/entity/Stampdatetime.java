package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stampdatetime")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stampdatetime {
    private Integer id;
    private String description;
    private Double positionX;
    private Double positionY;
    private Double widthContainer;
    private Double heightContainer;
    private Integer pageStamp;
    private Integer stampOn;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Workflowstampdatetime> workflowstampdatetimesById;

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
    @Column(name = "description", nullable = true, length = 32)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "positionX", nullable = true, precision = 0)
    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    @Basic
    @Column(name = "positionY", nullable = true, precision = 0)
    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    @Basic
    @Column(name = "widthContainer", nullable = true, precision = 0)
    public Double getWidthContainer() {
        return widthContainer;
    }

    public void setWidthContainer(Double widthContainer) {
        this.widthContainer = widthContainer;
    }

    @Basic
    @Column(name = "heightContainer", nullable = true, precision = 0)
    public Double getHeightContainer() {
        return heightContainer;
    }

    public void setHeightContainer(Double heightContainer) {
        this.heightContainer = heightContainer;
    }

    @Basic
    @Column(name = "pageStamp", nullable = true)
    public Integer getPageStamp() {
        return pageStamp;
    }

    public void setPageStamp(Integer pageStamp) {
        this.pageStamp = pageStamp;
    }

    @Basic
    @Column(name = "stampOn", nullable = true)
    public Integer getStampOn() {
        return stampOn;
    }

    public void setStampOn(Integer stampOn) {
        this.stampOn = stampOn;
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
        Stampdatetime that = (Stampdatetime) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(positionX, that.positionX) &&
                Objects.equals(positionY, that.positionY) &&
                Objects.equals(widthContainer, that.widthContainer) &&
                Objects.equals(heightContainer, that.heightContainer) &&
                Objects.equals(pageStamp, that.pageStamp) &&
                Objects.equals(stampOn, that.stampOn) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, positionX, positionY, widthContainer, heightContainer, pageStamp, stampOn, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "stampdatetimeByStampdatetimeId")
    public Collection<Workflowstampdatetime> getWorkflowstampdatetimesById() {
        return workflowstampdatetimesById;
    }

    public void setWorkflowstampdatetimesById(Collection<Workflowstampdatetime> workflowstampdatetimesById) {
        this.workflowstampdatetimesById = workflowstampdatetimesById;
    }
}
