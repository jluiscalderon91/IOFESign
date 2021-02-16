package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "siecertificate")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Siecertificate {
    private Integer id;
    private Integer siecredentialId;
    private String password;
    private String name;
    private String basename;
    private String extension;
    private byte[] data;
    private Timestamp notBefore;
    private Timestamp notAfter;
    private String alias;
    private Long length;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Siecredential siecredentialBySiecredentialId;
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
    @Column(name = "siecredentialId", nullable = false)
    public Integer getSiecredentialId() {
        return siecredentialId;
    }

    public void setSiecredentialId(Integer siecredentialId) {
        this.siecredentialId = siecredentialId;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "notBefore", nullable = true)
    public Timestamp getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Timestamp notBefore) {
        this.notBefore = notBefore;
    }

    @Basic
    @Column(name = "notAfter", nullable = true)
    public Timestamp getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Timestamp notAfter) {
        this.notAfter = notAfter;
    }

    @Basic
    @Column(name = "alias", nullable = true, length = 64)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
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
        Siecertificate that = (Siecertificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siecredentialId, that.siecredentialId) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Arrays.equals(data, that.data) &&
                Objects.equals(length, that.length) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, siecredentialId, password, name, length, createAt, active, deleted, observation);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "siecredentialId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Siecredential getSiecredentialBySiecredentialId() {
        return siecredentialBySiecredentialId;
    }

    public void setSiecredentialBySiecredentialId(Siecredential siecredentialBySiecredentialId) {
        this.siecredentialBySiecredentialId = siecredentialBySiecredentialId;
    }

    @Transient
    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
