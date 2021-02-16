package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutPerson;
import com.apsout.electronictestimony.api.entity.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    private Integer id;
    private Integer partnerId;
    private Integer enterpriseId;
    private Integer enterpriseIdView;
    private String enterpriseName;
    private Short type;
    private String documentType;
    private String documentNumber;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String cellphone;
    private Byte replaceable;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Balancepurchase> balancepurchasesById;
    @JsonIgnore
    private Collection<Cancelnotification> cancelnotificationsById;
    @JsonIgnore
    private Collection<Detailbalanceallocation> detailbalanceallocationsById;
    @JsonIgnore
    private Collection<Document> documentsById;
    @JsonIgnore
    private Collection<Documentmodification> documentmodificationsById;
    private Collection<Employee> employeesById;
    @JsonIgnore
    private Collection<Historicalbalanceallocation> historicalbalanceallocationsById;
    @JsonIgnore
    private Collection<Observationcancel> observationcancelsById;
    @JsonIgnore
    private Collection<Operator> operatorsById;
    @JsonIgnore
    private Collection<Participant> participantsById;
    private Partner partnerByPartnerId;
    private Enterprise enterpriseByEnterpriseId;
    private MoreAboutPerson moreAboutPerson;
    private Enterprise enterpriseByEnterpriseIdView;
    @JsonIgnore
    private Collection<Personrubric> personrubricsById;
    @JsonIgnore
    private Collection<Personworkflow> personworkflowsById;
    @JsonIgnore
    private Collection<Profileupdateattempt> profileupdateattemptsById;
    private Collection<Relatedperson> relatedpeopleById;
    private Collection<Relatedperson> relatedpeopleById_0;
    private Collection<Scope> scopesById;
    @JsonIgnore
    private Collection<Stationcounter> stationcountersById;
    @JsonIgnore
    private Collection<Temporarysession> temporarysessionsById;
    @JsonIgnore
    private Collection<User> usersById;
    private String workflows;
    private String enterpriseDocumentNumber;
    private String roles;
    private Integer scope;
    private Integer jobId;
    private String jobDescription;
    private String oldPassword;
    private String newPassword;
    private String rubricBase64Data;
    private String rubricFilename;

    public Person() {
    }

    public Person(Integer id){
        this.id = id;
    }

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
    @Column(name = "enterpriseId", nullable = false)
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "enterpriseIdView", nullable = false)
    public Integer getEnterpriseIdView() {
        return enterpriseIdView;
    }

    public void setEnterpriseIdView(Integer enterpriseIdView) {
        this.enterpriseIdView = enterpriseIdView;
    }

    @Basic
    @Column(name = "enterpriseName", nullable = true, length = 128)
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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
    @Column(name = "firstname", nullable = true, length = 64)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = true, length = 64)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "fullname", nullable = true, length = 128)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "cellphone", nullable = true, length = 32)
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Basic
    @Column(name = "replaceable", nullable = true)
    public Byte getReplaceable() {
        return replaceable;
    }

    public void setReplaceable(Byte replaceable) {
        this.replaceable = replaceable;
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
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(partnerId, person.partnerId) && Objects.equals(enterpriseId, person.enterpriseId) && Objects.equals(enterpriseIdView, person.enterpriseIdView) && Objects.equals(enterpriseName, person.enterpriseName) && Objects.equals(type, person.type) && Objects.equals(documentType, person.documentType) && Objects.equals(documentNumber, person.documentNumber) && Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname) && Objects.equals(fullname, person.fullname) && Objects.equals(email, person.email) && Objects.equals(cellphone, person.cellphone) && Objects.equals(replaceable, person.replaceable) && Objects.equals(createAt, person.createAt) && Objects.equals(active, person.active) && Objects.equals(deleted, person.deleted) && Objects.equals(observation, person.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partnerId, enterpriseId, enterpriseIdView, enterpriseName, type, documentType, documentNumber, firstname, lastname, fullname, email, cellphone, replaceable, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Balancepurchase> getBalancepurchasesById() {
        return balancepurchasesById;
    }

    public void setBalancepurchasesById(Collection<Balancepurchase> balancepurchasesById) {
        this.balancepurchasesById = balancepurchasesById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Cancelnotification> getCancelnotificationsById() {
        return cancelnotificationsById;
    }

    public void setCancelnotificationsById(Collection<Cancelnotification> cancelnotificationsById) {
        this.cancelnotificationsById = cancelnotificationsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Detailbalanceallocation> getDetailbalanceallocationsById() {
        return detailbalanceallocationsById;
    }

    public void setDetailbalanceallocationsById(Collection<Detailbalanceallocation> detailbalanceallocationsById) {
        this.detailbalanceallocationsById = detailbalanceallocationsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Document> getDocumentsById() {
        return documentsById;
    }

    public void setDocumentsById(Collection<Document> documentsById) {
        this.documentsById = documentsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Documentmodification> getDocumentmodificationsById() {
        return documentmodificationsById;
    }

    public void setDocumentmodificationsById(Collection<Documentmodification> documentmodificationsById) {
        this.documentmodificationsById = documentmodificationsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Employee> getEmployeesById() {
        return employeesById;
    }

    public void setEmployeesById(Collection<Employee> employeesById) {
        this.employeesById = employeesById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Historicalbalanceallocation> getHistoricalbalanceallocationsById() {
        return historicalbalanceallocationsById;
    }

    public void setHistoricalbalanceallocationsById(Collection<Historicalbalanceallocation> historicalbalanceallocationsById) {
        this.historicalbalanceallocationsById = historicalbalanceallocationsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Observationcancel> getObservationcancelsById() {
        return observationcancelsById;
    }

    public void setObservationcancelsById(Collection<Observationcancel> observationcancelsById) {
        this.observationcancelsById = observationcancelsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Operator> getOperatorsById() {
        return operatorsById;
    }

    public void setOperatorsById(Collection<Operator> operatorsById) {
        this.operatorsById = operatorsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Participant> getParticipantsById() {
        return participantsById;
    }

    public void setParticipantsById(Collection<Participant> participantsById) {
        this.participantsById = participantsById;
    }

    @ManyToOne
    @JoinColumn(name = "partnerId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Partner getPartnerByPartnerId() {
        return partnerByPartnerId;
    }

    public void setPartnerByPartnerId(Partner partnerByPartnerId) {
        this.partnerByPartnerId = partnerByPartnerId;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseIdView", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseIdView() {
        return enterpriseByEnterpriseIdView;
    }

    public void setEnterpriseByEnterpriseIdView(Enterprise enterpriseByEnterpriseIdView) {
        this.enterpriseByEnterpriseIdView = enterpriseByEnterpriseIdView;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Personrubric> getPersonrubricsById() {
        return personrubricsById;
    }

    public void setPersonrubricsById(Collection<Personrubric> personrubricsById) {
        this.personrubricsById = personrubricsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Personworkflow> getPersonworkflowsById() {
        return personworkflowsById;
    }

    public void setPersonworkflowsById(Collection<Personworkflow> personworkflowsById) {
        this.personworkflowsById = personworkflowsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Profileupdateattempt> getProfileupdateattemptsById() {
        return profileupdateattemptsById;
    }

    public void setProfileupdateattemptsById(Collection<Profileupdateattempt> profileupdateattemptsById) {
        this.profileupdateattemptsById = profileupdateattemptsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Relatedperson> getRelatedpeopleById() {
        return relatedpeopleById;
    }

    public void setRelatedpeopleById(Collection<Relatedperson> relatedpeopleById) {
        this.relatedpeopleById = relatedpeopleById;
    }

    @OneToMany(mappedBy = "personByPersonIdRelated")
    public Collection<Relatedperson> getRelatedpeopleById_0() {
        return relatedpeopleById_0;
    }

    public void setRelatedpeopleById_0(Collection<Relatedperson> relatedpeopleById_0) {
        this.relatedpeopleById_0 = relatedpeopleById_0;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Scope> getScopesById() {
        return scopesById;
    }

    public void setScopesById(Collection<Scope> scopesById) {
        this.scopesById = scopesById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Stationcounter> getStationcountersById() {
        return stationcountersById;
    }

    public void setStationcountersById(Collection<Stationcounter> stationcountersById) {
        this.stationcountersById = stationcountersById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Temporarysession> getTemporarysessionsById() {
        return temporarysessionsById;
    }

    public void setTemporarysessionsById(Collection<Temporarysession> temporarysessionsById) {
        this.temporarysessionsById = temporarysessionsById;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }

    @Transient
    public MoreAboutPerson getMoreAboutPerson() {
        return moreAboutPerson;
    }

    public void setMoreAboutPerson(MoreAboutPerson moreAboutPerson) {
        this.moreAboutPerson = moreAboutPerson;
    }

    @Transient
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Transient
    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    @Transient
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Transient
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Transient
    public String getWorkflows() {
        return workflows;
    }

    public void setWorkflows(String workflows) {
        this.workflows = workflows;
    }

    @Transient
    public String getEnterpriseDocumentNumber() {
        return enterpriseDocumentNumber;
    }

    public void setEnterpriseDocumentNumber(String enterpriseDocumentNumber) {
        this.enterpriseDocumentNumber = enterpriseDocumentNumber;
    }

    @Transient
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Transient
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Transient
    public String getRubricBase64Data() {
        return rubricBase64Data;
    }

    public void setRubricBase64Data(String rubricBase64Data) {
        this.rubricBase64Data = rubricBase64Data;
    }

    @Transient
    public String getRubricFilename() {
        return rubricFilename;
    }

    public void setRubricFilename(String rubricFilename) {
        this.rubricFilename = rubricFilename;
    }
}
