package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Documenttraceability;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.State;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDocumenttraceability;
import com.apsout.electronictestimony.api.repository.DocumenttraceabilityRepository;
import com.apsout.electronictestimony.api.service.DocumenttraceabilityService;
import com.apsout.electronictestimony.api.service.PersonService;
import com.apsout.electronictestimony.api.service.StateService;
import com.apsout.electronictestimony.api.util.allocator.DocumenttraceabilityAllocator;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumenttraceabilityServiceImpl implements DocumenttraceabilityService {
    private static final Logger logger = LoggerFactory.getLogger(DocumenttraceabilityServiceImpl.class);

    @Autowired
    private DocumenttraceabilityRepository repository;
    @Autowired
    private PersonService personService;
    @Autowired
    private StateService stateService;

    @Override
    public void save(Person person, Document document, int stateId, int type) {
        Documenttraceability traceability = DocumenttraceabilityAllocator.build(person, document, stateId, type);
        repository.save(traceability);
    }

    @Override
    public void save(int personId, int documentId, int stateId, int type) {
        Documenttraceability traceability = DocumenttraceabilityAllocator.build(personId, documentId, stateId, type);
        repository.save(traceability);
    }

    @Override
    public void save(int personId, Document document, int stateId, int type) {
        Documenttraceability traceability = DocumenttraceabilityAllocator.build(personId, document.getId(), stateId, type);
        repository.save(traceability);
    }

    @Override
    public void save(int personId, List<Document> documents, int stateId, int type) {
        documents.stream().forEach(document -> save(personId, document, stateId, type));
    }

    @Override
    public void save(Person person, int documentId, int stateId, byte visible, int type) {
        save(person.getId(), documentId, stateId, visible, type);
    }

    @Override
    public void save(int personId, int documentId, int stateId, byte visible, int type) {
        Documenttraceability traceability = DocumenttraceabilityAllocator.build(personId, documentId, stateId, visible, type);
        repository.save(traceability);
    }

    private void loadMoreInfo(List<Documenttraceability> traceabilities) {
        List<Integer> personIds = traceabilities.stream().map(Documenttraceability::getPersonId).collect(Collectors.toList());
        List<Integer> stateIds = traceabilities.stream().map(Documenttraceability::getStateId).collect(Collectors.toList());
        List<Person> people = personService.findAllBy(personIds);
        List<State> states = stateService.findAllBy(stateIds);
        traceabilities.stream().forEach(traceability -> {
            MoreAboutDocumenttraceability more = new MoreAboutDocumenttraceability();
            Person person = searchOn(people, traceability.getPersonId());
            State state = searchOn2(states, traceability.getStateId());
            more.setFullnameOperator(person.getFullname());
            more.setStateDescription(state.getLongDescription());
            traceability.setMoreAboutDocumenttraceability(more);
        });
    }

    private Person searchOn(List<Person> people, int personId) {
        return people.stream().filter(person -> personId == person.getId()).findFirst().get();
    }

    private State searchOn2(List<State> states, int stateId) {
        return states.stream().filter(state -> stateId == state.getId()).findFirst().get();
    }

    @Override
    public List<Documenttraceability> findAllBy(int documentId, String stringTypes) {
        List<Integer> types = StringUtil.split2Integers(stringTypes, ",");
        final List<Documenttraceability> traceabilities = repository.findAllByDocumentIdAndVisible(documentId, types);
        loadMoreInfo(traceabilities);
        return traceabilities;
    }

    @Override
    public void save(Person person, int documentId, int stateId, int type) {
        Documenttraceability traceability = DocumenttraceabilityAllocator.build(person.getId(), documentId, stateId, type);
        repository.save(traceability);
    }

    @Override
    public void save(HttpServletRequest request, int documentId, int stateId, int type) {
        Person person = personService.getBy(request);
        save(person, documentId, stateId, type);
    }
}
