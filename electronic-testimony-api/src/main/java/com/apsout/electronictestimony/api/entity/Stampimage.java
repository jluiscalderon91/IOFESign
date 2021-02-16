package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stampimage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stampimage {
    private Integer id;
    private String name;
    private String basename;
    private String extension;
    @JsonIgnore
    private byte[] data;
    private Integer length;
    private Integer positionX;
    private Integer positionY;
    private Double percentSize;
    private Double rotation;
    private Integer contentPosition;
    private Integer pageStamp;
    private Integer stampOn;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Workflowstampimage> workflowstampimagesById;
    private String base64Data;

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
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "basename", nullable = true, length = 64)
    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    @Basic
    @Column(name = "extension", nullable = true, length = 8)
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Basic
    @Column(name = "data", nullable = true)
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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
    @Column(name = "contentPosition", nullable = true, precision = 0)
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
        Stampimage that = (Stampimage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(basename, that.basename) &&
                Objects.equals(extension, that.extension) &&
                Arrays.equals(data, that.data) &&
                Objects.equals(length, that.length) &&
                Objects.equals(positionX, that.positionX) &&
                Objects.equals(positionY, that.positionY) &&
                Objects.equals(percentSize, that.percentSize) &&
                Objects.equals(rotation, that.rotation) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, basename, extension, length, positionX, positionY, rotation, percentSize, createAt, active, deleted, observation);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @OneToMany(mappedBy = "stampimageByStampimageId")
    public Collection<Workflowstampimage> getWorkflowstampimagesById() {
        return workflowstampimagesById;
    }

    public void setWorkflowstampimagesById(Collection<Workflowstampimage> workflowstampimagesById) {
        this.workflowstampimagesById = workflowstampimagesById;
    }

    @Transient
    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
