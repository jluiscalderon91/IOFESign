package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutJob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "job")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Job {
    private Integer id;
    private Integer enterpriseId;
    private String description;
    private Timestamp createAt;
    private Byte active;
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Employee> employeesById;
    private Enterprise enterpriseByEnterpriseId;
    private MoreAboutJob moreAboutJob;

    public Job() {
    }

    public Job(Integer id) {
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
    @Column(name = "enterpriseId", nullable = false)
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Job job = (Job) o;
        return Objects.equals(id, job.id) &&
                Objects.equals(enterpriseId, job.enterpriseId) &&
                Objects.equals(description, job.description) &&
                Objects.equals(createAt, job.createAt) &&
                Objects.equals(active, job.active) &&
                Objects.equals(deleted, job.deleted) &&
                Objects.equals(observation, job.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enterpriseId, description, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "jobByJobId")
    public Collection<Employee> getEmployeesById() {
        return employeesById;
    }

    public void setEmployeesById(Collection<Employee> employeesById) {
        this.employeesById = employeesById;
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
    public MoreAboutJob getMoreAboutJob() {
        return moreAboutJob;
    }

    public void setMoreAboutJob(MoreAboutJob moreAboutJob) {
        this.moreAboutJob = moreAboutJob;
    }
}
