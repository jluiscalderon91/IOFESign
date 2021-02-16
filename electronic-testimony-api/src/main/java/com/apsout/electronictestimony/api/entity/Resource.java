package com.apsout.electronictestimony.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "resource")
public class Resource {
    private Integer id;
    private String type;
    private String path;
    private String originalName;
    private String newName;
    private String extension;
    private Integer orderResource;
    private Long length;
    private String hash;
    private String resumeHash;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private Collection<Documentresource> documentresourcesById;
    @JsonIgnore
    private Collection<Done> donesById;
    @JsonIgnore
    private Collection<Numbersignature> numbersignaturesById;

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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "originalName")
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Basic
    @Column(name = "newName")
    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Basic
    @Column(name = "extension")
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Basic
    @Column(name = "orderResource", nullable = true)
    public Integer getOrderResource() {
        return orderResource;
    }

    public void setOrderResource(Integer orderResource) {
        this.orderResource = orderResource;
    }

    @Basic
    @Column(name = "length")
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Basic
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "resumeHash")
    public String getResumeHash() {
        return resumeHash;
    }

    public void setResumeHash(String resumeHash) {
        this.resumeHash = resumeHash;
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
    public String toString() {
        return new StringBuilder("resourceId: ")
                .append(this.id)
                .append(", documentName: '")
                .append(originalName)
                .append("'").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) &&
                Objects.equals(type, resource.type) &&
                Objects.equals(path, resource.path) &&
                Objects.equals(originalName, resource.originalName) &&
                Objects.equals(newName, resource.newName) &&
                Objects.equals(extension, resource.extension) &&
                Objects.equals(orderResource, resource.orderResource) &&
                Objects.equals(length, resource.length) &&
                Objects.equals(hash, resource.hash) &&
                Objects.equals(resumeHash, resource.resumeHash) &&
                Objects.equals(createAt, resource.createAt) &&
                Objects.equals(active, resource.active) &&
                Objects.equals(deleted, resource.deleted) &&
                Objects.equals(observation, resource.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, path, originalName, newName, extension, orderResource, length, hash, resumeHash, createAt, active, deleted, observation);
    }


    @OneToMany(mappedBy = "resourceByResourceId")
    public Collection<Documentresource> getDocumentresourcesById() {
        return documentresourcesById;
    }

    public void setDocumentresourcesById(Collection<Documentresource> documentresourcesById) {
        this.documentresourcesById = documentresourcesById;
    }

    @OneToMany(mappedBy = "resourceByResourceId")
    public Collection<Done> getDonesById() {
        return donesById;
    }

    public void setDonesById(Collection<Done> donesById) {
        this.donesById = donesById;
    }

    @OneToMany(mappedBy = "resourceByResourceId")
    public Collection<Numbersignature> getNumbersignaturesById() {
        return numbersignaturesById;
    }

    public void setNumbersignaturesById(Collection<Numbersignature> numbersignaturesById) {
        this.numbersignaturesById = numbersignaturesById;
    }
}
