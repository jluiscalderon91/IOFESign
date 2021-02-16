package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stamprubric")
public class Stamprubric {
    private Integer id;
    private Integer participantId;
    private Integer positionX;
    private Integer positionY;
    private Integer positionXf;
    private Integer positionYf;
    private Double percentSize;
    private Double rotation;
    private Integer contentPosition;
    private Integer pageStamp;
    private Integer stampOn;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Participant participantByParticipantId;
    @JsonIgnore
    private Collection<Workflowstamprubric> workflowstamprubricsById;

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
    @Column(name = "participantId", nullable = false)
    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    @Basic
    @Column(name = "positionX", nullable = true)
    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    @Basic
    @Column(name = "positionY", nullable = true)
    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    @Basic
    @Column(name = "positionXf", nullable = true)
    public Integer getPositionXf() {
        return positionXf;
    }

    public void setPositionXf(Integer positionXf) {
        this.positionXf = positionXf;
    }

    @Basic
    @Column(name = "positionYf", nullable = true)
    public Integer getPositionYf() {
        return positionYf;
    }

    public void setPositionYf(Integer positionYf) {
        this.positionYf = positionYf;
    }

    @Basic
    @Column(name = "percentSize", nullable = true, precision = 0)
    public Double getPercentSize() {
        return percentSize;
    }

    public void setPercentSize(Double percentSize) {
        this.percentSize = percentSize;
    }

    @Basic
    @Column(name = "rotation", nullable = true, precision = 0)
    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    @Basic
    @Column(name = "contentPosition", nullable = true)
    public Integer getContentPosition() {
        return contentPosition;
    }

    public void setContentPosition(Integer contentPosition) {
        this.contentPosition = contentPosition;
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
        Stamprubric that = (Stamprubric) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(participantId, that.participantId) &&
                Objects.equals(positionX, that.positionX) &&
                Objects.equals(positionY, that.positionY) &&
                Objects.equals(positionXf, that.positionXf) &&
                Objects.equals(positionYf, that.positionYf) &&
                Objects.equals(percentSize, that.percentSize) &&
                Objects.equals(rotation, that.rotation) &&
                Objects.equals(contentPosition, that.contentPosition) &&
                Objects.equals(pageStamp, that.pageStamp) &&
                Objects.equals(stampOn, that.stampOn) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, participantId, positionX, positionY, positionXf, positionYf, percentSize, rotation, contentPosition, pageStamp, stampOn, createAt, active, deleted, observation);
    }

    @ManyToOne
    @JoinColumn(name = "participantId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Participant getParticipantByParticipantId() {
        return participantByParticipantId;
    }

    public void setParticipantByParticipantId(Participant participantByParticipantId) {
        this.participantByParticipantId = participantByParticipantId;
    }

    @OneToMany(mappedBy = "stamprubricByStamprubricId")
    public Collection<Workflowstamprubric> getWorkflowstamprubricsById() {
        return workflowstamprubricsById;
    }

    public void setWorkflowstamprubricsById(Collection<Workflowstamprubric> workflowstamprubricsById) {
        this.workflowstamprubricsById = workflowstamprubricsById;
    }
}
