package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stampqrcode")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stampqrcode {
    private Integer id;
    private Double positionX;
    private Double positionY;
    private Integer sideSize;
    private Integer pageStamp;
    private Integer stampOn;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Workflowstampqrcode> workflowstampqrcodesById;
    private String pageStampValue;

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
    @Column(name = "sideSize", nullable = true)
    public Integer getSideSize() {
        return sideSize;
    }

    public void setSideSize(Integer sideSize) {
        this.sideSize = sideSize;
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
        Stampqrcode that = (Stampqrcode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(positionX, that.positionX) &&
                Objects.equals(positionY, that.positionY) &&
                Objects.equals(sideSize, that.sideSize) &&
                Objects.equals(pageStamp, that.pageStamp) &&
                Objects.equals(stampOn, that.stampOn) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionX, positionY, sideSize, pageStamp, stampOn, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "stampqrcodeByStampqrcodeId")
    public Collection<Workflowstampqrcode> getWorkflowstampqrcodesById() {
        return workflowstampqrcodesById;
    }

    public void setWorkflowstampqrcodesById(Collection<Workflowstampqrcode> workflowstampqrcodesById) {
        this.workflowstampqrcodesById = workflowstampqrcodesById;
    }

//    @Transient
//    public String getPageStampValue() {
//        return pageStampValue;
//    }
//
//    public void setPageStampValue(String pageStampValue) {
//        this.pageStampValue = pageStampValue;
//    }
}
