package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    Page<Person> getAllBy2(int partnerId, int enterpriseId, int roleId, int participantType, String term2Search, Pageable pageable, HttpServletRequest request);

    Person getBy(int personId);

    Optional<Person> findBy(int personId);

    Optional<Person> findByDocumentId(int documentId);

    Person getByDocumentId(int documentId);

    Person save(Person person, HashMap<String, Boolean> foundMap);

    Person update(Person person);

    Optional<Person> findBy(int enterpriseId, int enterpriseIdView, int participantType, int type, String documentType, String documentNumber, int jobId);

    Person delete(Person person);

    List<Person> findAllBy(int workflowId);

    List<Person> findBy(int enterpriseId, int participantType, String fullName);

    Person searchBy(int type, int enterpriseId, int documentType, String documentNumber);

    Person findBy(int personId, String hashIdentifier);

    Person saveWorkflows(Person person);

    Person getBy(HttpServletRequest request);

    List<Person> findAllPeopleBy(int documentId);

    Person onlySave(Person person);

    Person resetUserPassword(int personId);

    Person updateAuthorities(int personId, _AuthoritiesList authoritiesList);

    List<Person> findAllBy(List<Integer> personIds);

    Person updateProfile(Person person, int personId);

    Person updateRubric(int personId, String uuid, String rubricFilename, String rubricFileBase64);
}
