package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "stamplayoutfile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stamplayoutfile {
    private Integer id;
    private Integer workflowId;
    private String name;
    private String excelName;
    private String basename;
    private String excelBasename;
    private String extension;
    @JsonIgnore
    private byte[] data;
    @JsonIgnore
    private byte[] excelData;
    private String excelExtension;
    private Integer length;
    private Integer excelLength;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Workflow workflowByWorkflowId;
    private String base64Data;
    private String excelBase64Data;

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
    @Column(name = "workflowId", nullable = false)
    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "excelName", nullable = true, length = 128)
    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    @Basic
    @Column(name = "basename", nullable = true, length = 128)
    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    @Basic
    @Column(name = "excelBasename", nullable = true, length = 128)
    public String getExcelBasename() {
        return excelBasename;
    }

    public void setExcelBasename(String excelBasename) {
        this.excelBasename = excelBasename;
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
    @Column(name = "excelExtension", nullable = true, length = 8)
    public String getExcelExtension() {
        return excelExtension;
    }

    public void setExcelExtension(String excelExtension) {
        this.excelExtension = excelExtension;
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
    @Column(name = "excelData", nullable = true)
    public byte[] getExcelData() {
        return excelData;
    }

    public void setExcelData(byte[] excelData) {
        this.excelData = excelData;
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
    @Column(name = "excelLength", nullable = true)
    public Integer getExcelLength() {
        return excelLength;
    }

    public void setExcelLength(Integer excelLength) {
        this.excelLength = excelLength;
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
        Stamplayoutfile that = (Stamplayoutfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(workflowId, that.workflowId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(basename, that.basename) &&
                Objects.equals(extension, that.extension) &&
                Objects.equals(excelExtension, that.excelExtension) &&
                Objects.equals(data, that.data) &&
                Objects.equals(excelData, that.excelData) &&
                Objects.equals(length, that.length) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, workflowId, name, basename, extension, excelExtension, length, createAt, active, deleted, observation);
        result = 31 * result + Arrays.hashCode(data);
        result = 31 * result + Arrays.hashCode(excelData);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "workflowId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Workflow getWorkflowByWorkflowId() {
        return workflowByWorkflowId;
    }

    public void setWorkflowByWorkflowId(Workflow workflowByWorkflowId) {
        this.workflowByWorkflowId = workflowByWorkflowId;
    }

    @Transient
    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    @Transient
    public String getExcelBase64Data() {
        return excelBase64Data;
    }

    public void setExcelBase64Data(String excelBase64Data) {
        this.excelBase64Data = excelBase64Data;
    }
}
