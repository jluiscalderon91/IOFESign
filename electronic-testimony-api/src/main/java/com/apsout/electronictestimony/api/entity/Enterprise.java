package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutEnterprise;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "enterprise")
public class Enterprise {
    private Integer id;
    private Integer partnerId;
    private String documentType;
    private String documentNumber;
    private String name;
    private String tradeName;
    private Byte excluded;
    private Byte isPartner;
    private Boolean isCustomer;
    private Integer createdByPersonId;
    private Timestamp updatedAt;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    private MoreAboutEnterprise moreAboutEnterprise;
    @JsonIgnore
    private Collection<Balancepurchase> balancepurchasesById;
    @JsonIgnore
    private Collection<Deliverysetting> deliverysettingsById;
    private Partner partnerByPartnerId;
    @JsonIgnore
    private Collection<Headbalanceallocation> headbalanceallocationsById;
    @JsonIgnore
    private Collection<Job> jobsById;
    @JsonIgnore
    private Collection<Mailtemplate> mailtemplatesById;
    @JsonIgnore
    private Collection<Operator> operatorsById;
    @JsonIgnore
    private Collection<Person> peopleById;
    @JsonIgnore
    private Collection<Person> peopleById_0;
    @JsonIgnore
    private Collection<Siecredential> siecredentialsById;
    @JsonIgnore
    private Collection<Workflow> workflowsById;

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
    @Column(name = "documentType", nullable = true, length = 2)
    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Basic
    @Column(name = "documentNumber", nullable = true, length = 24)
    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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
    @Column(name = "tradeName", nullable = true, length = 128)
    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    @Basic
    @Column(name = "excluded", nullable = true)
    public Byte getExcluded() {
        return excluded;
    }

    public void setExcluded(Byte excluded) {
        this.excluded = excluded;
    }

    @Basic
    @Column(name = "isPartner", nullable = true)
    public Byte getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Byte isPartner) {
        this.isPartner = isPartner;
    }

    @Basic
    @Column(name = "isCustomer", nullable = true)
    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    @Basic
    @Column(name = "createdByPersonId", nullable = true)
    public Integer getCreatedByPersonId() {
        return createdByPersonId;
    }

    public void setCreatedByPersonId(Integer createdByPersonId) {
        this.createdByPersonId = createdByPersonId;
    }

    @Basic
    @Column(name = "updatedAt", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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
        Enterprise that = (Enterprise) o;
        return Objects.equals(id, that.id) && Objects.equals(partnerId, that.partnerId) && Objects.equals(documentType, that.documentType) && Objects.equals(documentNumber, that.documentNumber) && Objects.equals(name, that.name) && Objects.equals(tradeName, that.tradeName) && Objects.equals(excluded, that.excluded) && Objects.equals(isPartner, that.isPartner) && Objects.equals(isCustomer, that.isCustomer) && Objects.equals(createdByPersonId, that.createdByPersonId) && Objects.equals(createAt, that.createAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(active, that.active) && Objects.equals(deleted, that.deleted) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partnerId, documentType, documentNumber, name, tradeName, excluded, isPartner, isCustomer, createdByPersonId, updatedAt, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Balancepurchase> getBalancepurchasesById() {
        return balancepurchasesById;
    }

    public void setBalancepurchasesById(Collection<Balancepurchase> balancepurchasesById) {
        this.balancepurchasesById = balancepurchasesById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Deliverysetting> getDeliverysettingsById() {
        return deliverysettingsById;
    }

    public void setDeliverysettingsById(Collection<Deliverysetting> deliverysettingsById) {
        this.deliverysettingsById = deliverysettingsById;
    }

    @ManyToOne
    @JoinColumn(name = "partnerId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Partner getPartnerByPartnerId() {
        return partnerByPartnerId;
    }

    public void setPartnerByPartnerId(Partner partnerByPartnerId) {
        this.partnerByPartnerId = partnerByPartnerId;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Headbalanceallocation> getHeadbalanceallocationsById() {
        return headbalanceallocationsById;
    }

    public void setHeadbalanceallocationsById(Collection<Headbalanceallocation> headbalanceallocationsById) {
        this.headbalanceallocationsById = headbalanceallocationsById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Job> getJobsById() {
        return jobsById;
    }

    public void setJobsById(Collection<Job> jobsById) {
        this.jobsById = jobsById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Mailtemplate> getMailtemplatesById() {
        return mailtemplatesById;
    }

    public void setMailtemplatesById(Collection<Mailtemplate> mailtemplatesById) {
        this.mailtemplatesById = mailtemplatesById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Operator> getOperatorsById() {
        return operatorsById;
    }

    public void setOperatorsById(Collection<Operator> operatorsById) {
        this.operatorsById = operatorsById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Person> getPeopleById() {
        return peopleById;
    }

    public void setPeopleById(Collection<Person> peopleById) {
        this.peopleById = peopleById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseIdView")
    public Collection<Person> getPeopleById_0() {
        return peopleById_0;
    }

    public void setPeopleById_0(Collection<Person> peopleById_0) {
        this.peopleById_0 = peopleById_0;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Siecredential> getSiecredentialsById() {
        return siecredentialsById;
    }

    public void setSiecredentialsById(Collection<Siecredential> siecredentialsById) {
        this.siecredentialsById = siecredentialsById;
    }

    @OneToMany(mappedBy = "enterpriseByEnterpriseId")
    public Collection<Workflow> getWorkflowsById() {
        return workflowsById;
    }

    public void setWorkflowsById(Collection<Workflow> workflowsById) {
        this.workflowsById = workflowsById;
    }

    @Transient
    public MoreAboutEnterprise getMoreAboutEnterprise() {
        return moreAboutEnterprise;
    }

    public void setMoreAboutEnterprise(MoreAboutEnterprise moreAboutEnterprise) {
        this.moreAboutEnterprise = moreAboutEnterprise;
    }
}
