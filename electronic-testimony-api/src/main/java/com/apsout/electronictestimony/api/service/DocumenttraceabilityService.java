package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documenttraceability;
import com.apsout.electronictestimony.api.entity.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DocumenttraceabilityService {

    void save(Person person, Document document, int stateId, int type);

    void save(int personId, int documentId, int stateId, int type);

    void save(int personId, Document document, int stateId, int type);

    void save(int personId, List<Document> documents, int stateId, int type);

    void save(Person person, int documentId, int stateId, byte visible, int type);

    void save(int personId, int documentId, int stateId, byte visible, int type);

    List<Documenttraceability> findAllBy(int documentId, String types);

    void save(Person person, int documentId, int stateId, int type);

    void save(HttpServletRequest request, int documentId, int stateId, int type);
}
