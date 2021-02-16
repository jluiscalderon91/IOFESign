package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutPerson;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.ParticipantType;
import com.apsout.electronictestimony.api.util.statics.States;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PersonAllocator {
    private static final Logger logger = LoggerFactory.getLogger(PersonAllocator.class);

    public static void ofPostMethod(Person person) {
        person.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        person.setActive(States.ACTIVE);
        person.setDeleted(States.EXISTENT);
    }

    public static Person build(Person person, Enterprise enterprise) {
        String fullName = new StringBuilder(person.getFirstname()).append(" ").append(person.getLastname()).toString();
        person.setEnterpriseName(enterprise.getName());
        person.setFullname(fullName);
        person.setReplaceable(States.NOT_REPLACEABLE);
        person.setEnterpriseIdView(person.getEnterpriseIdView());
        ofPostMethod(person);
        return person;
    }

    public static void forUpdate(Person personDb, Person person, Enterprise enterprise) {
        String fullName = new StringBuilder(person.getFirstname()).append(" ").append(person.getLastname()).toString();
        personDb.setPartnerId(person.getPartnerId());
        personDb.setId(person.getId());
        personDb.setDocumentType(person.getDocumentType());
        personDb.setEnterpriseId(person.getEnterpriseId());
        personDb.setEnterpriseIdView(person.getEnterpriseIdView());
        personDb.setDocumentNumber(person.getDocumentNumber());
        personDb.setFirstname(person.getFirstname());
        personDb.setLastname(person.getLastname());
        personDb.setActive(person.getActive());
        personDb.setEmail(person.getEmail());
        personDb.setFullname(fullName);
        personDb.setEnterpriseName(enterprise.getName());
    }

    public static Person clone(Person person) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(objectMapper.writeValueAsString(person), Person.class);
        } catch (JsonProcessingException e) {
            logger.error("Cloning object person", e);
            return null;
        }
    }

    public static void loadMoreInfo(MoreAboutPerson moreAboutPerson, Participant participant, Operation operation) {
        moreAboutPerson.setParticipantType(participant.getParticipantType());
        moreAboutPerson.setOrderParticipant(participant.getOrderParticipant());
        moreAboutPerson.setOperationId(operation.getId());
        moreAboutPerson.setOperationDescription(operation.getDescription());
    }

    public static void loadMoreInfo(MoreAboutPerson moreAboutPerson, Scope scope, Person person, Operator operator) {
        final Integer participantType = scope.getParticipantType();
        moreAboutPerson.setParticipantType(participantType);
        if (ParticipantType.INVITED == participantType) {
            moreAboutPerson.setEnterpriseNameView("-");
        } else {
            moreAboutPerson.setEnterpriseNameView(person.getEnterpriseByEnterpriseIdView().getName());
        }
        moreAboutPerson.setUploadRubric(operator.getUploadRubric());
        moreAboutPerson.setDigitalSignature(operator.getDigitalSignature());
    }

    public static void loadMoreInfo(MoreAboutPerson moreAboutPerson, Person person, Operation operation, Optional<Assigner> optionalAssigner) {
        moreAboutPerson.setOperationId(operation.getId());
        moreAboutPerson.setOperationDescription(operation.getDescription());
        if (optionalAssigner.isPresent()) {
            Assigner assigner = optionalAssigner.get();
            moreAboutPerson.setOperationCompleted(assigner.getCompleted());
        } else {
            moreAboutPerson.setOperationCompleted(States.INCOMPLETE);
        }
        if (Default.ENTERPRISE_ID_VIEW == person.getEnterpriseIdView()) {
            moreAboutPerson.setEnterpriseTradeNameView("-");
        } else {
            final String enterpriseName = person.getEnterpriseByEnterpriseIdView().getName();
            moreAboutPerson.setEnterpriseTradeNameView(enterpriseName);
        }
    }

    public static void loadMoreInfo(Enterprise enterprise, Enterprise enterpriseView, Scope scope, Job job, List<Role> roles, MoreAboutPerson moreAboutPerson) {
        moreAboutPerson.setJobId(job.getId());
        moreAboutPerson.setJobDescription(job.getDescription());
        moreAboutPerson.setParticipantType(scope.getParticipantType());
        moreAboutPerson.setEnterpriseTradeNameView(enterpriseView.getTradeName());
        moreAboutPerson.setEnterpriseNameView(enterpriseView.getName());
        moreAboutPerson.setEnterpriseDocumentNumberView(enterpriseView.getDocumentNumber());
        moreAboutPerson.setEnterpriseTradeName(enterprise.getTradeName());
        moreAboutPerson.setRoles(StringUtil.concatIds(roles));
        moreAboutPerson.setRolesDescription(StringUtil.concatDescriptions(roles));
        moreAboutPerson.setRolesNameView(StringUtil.concatNameViews(roles));
    }

    public static void loadMoreInfo(Enterprise enterprise, Enterprise enterpriseView, Scope scope, Job job, List<Role> roles, Personrubric personrubric, MoreAboutPerson moreAboutPerson) {
        loadMoreInfo(enterprise, enterpriseView, scope, job, roles, moreAboutPerson);
        final String rubricFilename = personrubric.getName();
        moreAboutPerson.setRubricFilename(rubricFilename);
        moreAboutPerson.setBase64RubricFile(FileUtil.encodeBytes2Base64(personrubric.getData()));
        moreAboutPerson.setRubricFileExtension(FilenameUtils.getExtension(rubricFilename));
    }

    public static void loadMoreInfo(Enterprise enterprise, Job job, MoreAboutPerson moreAboutPerson) {
        moreAboutPerson.setEnterpriseNameView(enterprise.getTradeName());
        moreAboutPerson.setJobId(job.getId());
        moreAboutPerson.setJobDescription(job.getDescription());
    }

    public static void ofApi2Clean(Person person) {
        person.setDocumentType(person.getDocumentType().trim());
        person.setDocumentNumber(person.getDocumentNumber().trim());
        person.setFirstname(person.getFirstname().trim());
        person.setLastname(person.getLastname().trim());
        person.setEmail(person.getEmail().trim());
        person.setCellphone(person.getCellphone().trim());
        person.setEnterpriseDocumentNumber(person.getEnterpriseDocumentNumber().trim());
    }

    public static void ofApiMethod(Person person, Enterprise enterprise, Enterprise enterpriseView) {
        person.setEnterpriseIdView(enterpriseView.getId());
        person.setEnterpriseId(enterprise.getId());
        person.setFullname(person.getFirstname() + " " + person.getLastname());
        person.setReplaceable(States.NOT_REPLACEABLE);
        person.setEnterpriseName(enterprise.getName());
        ofPostMethod(person);
    }

    public static void forUpdateProfile(Person personDb, Person person) {
        personDb.setEmail(person.getEmail());
        personDb.setCellphone(person.getCellphone());
    }
}
