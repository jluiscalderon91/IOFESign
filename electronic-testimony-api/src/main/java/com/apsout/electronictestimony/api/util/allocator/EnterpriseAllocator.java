package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EnterpriseAllocator {

    public static void build(Enterprise enterprise, Person person) {
        enterprise.setExcluded(States.NOT_EXCLUDED);
        enterprise.setCreatedByPersonId(person.getId());
        enterprise.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        enterprise.setDocumentNumber(enterprise.getDocumentNumber().trim());
        ofPostMethod(enterprise);
    }

    public static void ofPostMethod(Enterprise enterprise) {
        enterprise.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        enterprise.setActive(States.ACTIVE);
        enterprise.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Enterprise enterpriseDb, Enterprise enterprise, Person person) {
        enterprise.setDocumentNumber(enterprise.getDocumentNumber().trim());
        enterpriseDb.setName(enterprise.getName());
        enterpriseDb.setDocumentType(enterprise.getDocumentType());
        enterpriseDb.setDocumentNumber(enterprise.getDocumentNumber());
        enterpriseDb.setObservation(enterprise.getObservation());
        enterpriseDb.setTradeName(enterprise.getTradeName());
        enterpriseDb.setActive(enterprise.getActive());
        enterpriseDb.setPartnerId(enterprise.getPartnerId());
        enterpriseDb.setIsPartner(enterprise.getIsPartner());
        enterpriseDb.setIsCustomer(enterprise.getIsCustomer());
        enterpriseDb.setCreatedByPersonId(person.getId());
        enterpriseDb.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }
}
