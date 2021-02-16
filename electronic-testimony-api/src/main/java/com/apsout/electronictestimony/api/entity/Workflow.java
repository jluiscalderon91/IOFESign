package com.apsout.electronictestimony.api.entity;

import com.apsout.electronictestimony.api.entity.common.MoreAboutWorkflow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "workflow")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workflow {
    private Integer id;
    private Integer enterpriseId;
    private String description;
    private Byte batch;
    private Integer maxParticipants;
    private Byte completed;
    private Byte ready2Use;
    private Byte deliver;
    private String code;
    private Integer type;
    private Byte dynamic;
    private Timestamp createAt;
    private Byte active;
    private MoreAboutWorkflow moreAboutWorkflow;
    @JsonIgnore
    private Byte deleted;
    private String observation;
    @JsonIgnore
    private Collection<Document> documentsById;
    @JsonIgnore
    private Collection<Participant> participantsById;
    @JsonIgnore
    private Collection<Personworkflow> personworkflowsById;
    @JsonIgnore
    private Collection<Stamplayoutfile> stamplayoutfilesById;
    @JsonIgnore
    private Collection<Stamptestfile> stamptestfilesById;
    @JsonIgnore
    private Collection<Stationcounter> stationcountersById;
    @JsonIgnore
    private Enterprise enterpriseByEnterpriseId;
    @JsonIgnore
    private Collection<Workflowstampdatetime> workflowstampdatetimesById;
    @JsonIgnore
    private Collection<Workflowstampimage> workflowstampimagesById;
    @JsonIgnore
    private Collection<Workflowstamplegend> workflowstamplegendsById;
    @JsonIgnore
    private Collection<Workflowstampqrcode> workflowstampqrcodesById;
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
    @Column(name = "batch", nullable = true)
    public Byte getBatch() {
        return batch;
    }

    public void setBatch(Byte batch) {
        this.batch = batch;
    }

    @Basic
    @Column(name = "maxParticipants", nullable = true)
    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    @Basic
    @Column(name = "completed", nullable = true)
    public Byte getCompleted() {
        return completed;
    }

    public void setCompleted(Byte completed) {
        this.completed = completed;
    }

    @Basic
    @Column(name = "ready2Use", nullable = true)
    public Byte getReady2Use() {
        return ready2Use;
    }

    public void setReady2Use(Byte ready2Use) {
        this.ready2Use = ready2Use;
    }

    @Basic
    @Column(name = "deliver", nullable = true)
    public Byte getDeliver() {
        return deliver;
    }

    public void setDeliver(Byte deliver) {
        this.deliver = deliver;
    }

    @Basic
    @Column(name = "code", nullable = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "dynamic", nullable = true)
    public Byte getDynamic() {
        return dynamic;
    }

    public void setDynamic(Byte dynamic) {
        this.dynamic = dynamic;
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
        Workflow workflow = (Workflow) o;
        return Objects.equals(id, workflow.id) &&
                Objects.equals(enterpriseId, workflow.enterpriseId) &&
                Objects.equals(description, workflow.description) &&
                Objects.equals(batch, workflow.batch) &&
                Objects.equals(maxParticipants, workflow.maxParticipants) &&
                Objects.equals(completed, workflow.completed) &&
                Objects.equals(ready2Use, workflow.ready2Use) &&
                Objects.equals(deliver, workflow.deliver) &&
                Objects.equals(code, workflow.code) &&
                Objects.equals(type, workflow.type) &&
                Objects.equals(dynamic, workflow.dynamic) &&
                Objects.equals(createAt, workflow.createAt) &&
                Objects.equals(active, workflow.active) &&
                Objects.equals(deleted, workflow.deleted) &&
                Objects.equals(observation, workflow.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enterpriseId, description, batch, maxParticipants, completed, ready2Use, deliver, code, type, dynamic, createAt, active, deleted, observation);
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Document> getDocumentsById() {
        return documentsById;
    }

    public void setDocumentsById(Collection<Document> documentsById) {
        this.documentsById = documentsById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Participant> getParticipantsById() {
        return participantsById;
    }

    public void setParticipantsById(Collection<Participant> participantsById) {
        this.participantsById = participantsById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Personworkflow> getPersonworkflowsById() {
        return personworkflowsById;
    }

    public void setPersonworkflowsById(Collection<Personworkflow> personworkflowsById) {
        this.personworkflowsById = personworkflowsById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Stamplayoutfile> getStamplayoutfilesById() {
        return stamplayoutfilesById;
    }

    public void setStamplayoutfilesById(Collection<Stamplayoutfile> stamplayoutfilesById) {
        this.stamplayoutfilesById = stamplayoutfilesById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Stamptestfile> getStamptestfilesById() {
        return stamptestfilesById;
    }

    public void setStamptestfilesById(Collection<Stamptestfile> stamptestfilesById) {
        this.stamptestfilesById = stamptestfilesById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Stationcounter> getStationcountersById() {
        return stationcountersById;
    }

    public void setStationcountersById(Collection<Stationcounter> stationcountersById) {
        this.stationcountersById = stationcountersById;
    }

    @ManyToOne
    @JoinColumn(name = "enterpriseId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Enterprise getEnterpriseByEnterpriseId() {
        return enterpriseByEnterpriseId;
    }

    public void setEnterpriseByEnterpriseId(Enterprise enterpriseByEnterpriseId) {
        this.enterpriseByEnterpriseId = enterpriseByEnterpriseId;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Workflowstampdatetime> getWorkflowstampdatetimesById() {
        return workflowstampdatetimesById;
    }

    public void setWorkflowstampdatetimesById(Collection<Workflowstampdatetime> workflowstampdatetimesById) {
        this.workflowstampdatetimesById = workflowstampdatetimesById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Workflowstampimage> getWorkflowstampimagesById() {
        return workflowstampimagesById;
    }

    public void setWorkflowstampimagesById(Collection<Workflowstampimage> workflowstampimagesById) {
        this.workflowstampimagesById = workflowstampimagesById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Workflowstamplegend> getWorkflowstamplegendsById() {
        return workflowstamplegendsById;
    }

    public void setWorkflowstamplegendsById(Collection<Workflowstamplegend> workflowstamplegendsById) {
        this.workflowstamplegendsById = workflowstamplegendsById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Workflowstampqrcode> getWorkflowstampqrcodesById() {
        return workflowstampqrcodesById;
    }

    public void setWorkflowstampqrcodesById(Collection<Workflowstampqrcode> workflowstampqrcodesById) {
        this.workflowstampqrcodesById = workflowstampqrcodesById;
    }

    @OneToMany(mappedBy = "workflowByWorkflowId")
    public Collection<Workflowstamprubric> getWorkflowstamprubricsById() {
        return workflowstamprubricsById;
    }

    public void setWorkflowstamprubricsById(Collection<Workflowstamprubric> workflowstamprubricsById) {
        this.workflowstamprubricsById = workflowstamprubricsById;
    }

    @Transient
    public MoreAboutWorkflow getMoreAboutWorkflow() {
        return moreAboutWorkflow;
    }

    public void setMoreAboutWorkflow(MoreAboutWorkflow moreAboutWorkflow) {
        this.moreAboutWorkflow = moreAboutWorkflow;
    }
}
