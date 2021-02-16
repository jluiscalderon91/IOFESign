package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutSiecredential;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "siecredential")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Siecredential {
    private Integer id;
    private Integer enterpriseId;
    private String username;
    private String password;
    private String localpart;
    private String domain;
    private Double version;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Siecertificate> siecertificatesById;
    private Enterprise enterpriseByEnterpriseId;
    private MoreAboutSiecredential moreAboutSiecredential;

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
    @Column(name = "username", nullable = true, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "localpart", nullable = true, length = 32)
    public String getLocalpart() {
        return localpart;
    }

    public void setLocalpart(String localpart) {
        this.localpart = localpart;
    }

    @Basic
    @Column(name = "domain", nullable = true, length = 32)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "version", nullable = true, precision = 0)
    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
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
        Siecredential that = (Siecredential) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(enterpriseId, that.enterpriseId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(localpart, that.localpart) &&
                Objects.equals(domain, that.domain) &&
                Objects.equals(version, that.version) &&
                Objects.equals(createAt, that.createAt) &&
                Objects.equals(active, that.active) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enterpriseId, username, password, localpart, domain, version, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "siecredentialBySiecredentialId")
    public Collection<Siecertificate> getSiecertificatesById() {
        return siecertificatesById;
    }

    public void setSiecertificatesById(Collection<Siecertificate> siecertificatesById) {
        this.siecertificatesById = siecertificatesById;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @Transient
    public MoreAboutSiecredential getMoreAboutSiecredential() {
        return moreAboutSiecredential;
    }

    public void setMoreAboutSiecredential(MoreAboutSiecredential moreAboutSiecredential) {
        this.moreAboutSiecredential = moreAboutSiecredential;
    }
}
