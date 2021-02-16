package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Operator;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.OperatorModel;
import com.apsout.electronictestimony.api.entity.model.PersonModel;
import com.apsout.electronictestimony.api.modelassembler.OperatorModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.PersonModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.ParticipantService;
import com.apsout.electronictestimony.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// TODO review the way to improvement CrossOrigin operation
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AccessResourceService accessResourceService;
    @Autowired
    private ParticipantService participantService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:person:get:document')")
    @GetMapping(value = "/documents/{documentId}/people")
    public ResponseEntity<CollectionModel<PersonModel>> getBy3(@PathVariable int documentId) {
        List<Person> people = personService.findAllPeopleBy(documentId);
        PersonModelAssembler assembler = new PersonModelAssembler();
        CollectionModel<PersonModel> collectionModel = assembler.toCollectionModel(people);
        return ResponseEntity.ok().body(collectionModel);
    }

    @PreAuthorize("hasAuthority('own:person:get:page')")
    @GetMapping(value = "/partners/{partnerId}/enterprises/{enterpriseIdView}/roles/{roleId}/participanttypes/{participantType}/people")
    public ResponseEntity<CollectionModel<PersonModel>> getAllBy(@PathVariable int partnerId,
                                                                 @PathVariable int enterpriseIdView,
                                                                 @PathVariable int roleId,
                                                                 @PathVariable int participantType,
                                                                 @RequestParam String findby,
                                                                 Pageable pageable,
                                                                 HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseIdView);
        Page<Person> personPage = personService.getAllBy2(partnerId, enterpriseIdView, roleId, participantType, findby, pageable, request);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PagedModel<PersonModel> pagedModel = resourcesAssembler.toModel(personPage, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:person:get:detail')")
    @GetMapping(value = "/people/{personId}")
    public ResponseEntity<PersonModel> getBy(@PathVariable int personId,
                                             HttpServletRequest request) {
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        Person person = personService.getBy(personId);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @PreAuthorize("hasAuthority('own:person:add')")
    @PostMapping(value = "/people")
    public ResponseEntity<PersonModel> save(@RequestBody Person person) {
        HashMap<String, Boolean> foundMap = new HashMap<>();
        person = personService.save(person, foundMap);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel operatorModel = assembler.toModel(person);
        boolean found = foundMap.get("found");
        if (!found) {
            return ResponseEntity.ok().body(operatorModel);
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(operatorModel);
        }
    }

    @PreAuthorize("hasAuthority('own:person:edit')")
    @PutMapping(value = "/people")
    public ResponseEntity<PersonModel> update(@RequestBody Person person, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfPersonId(request, person.getId());
        personService.update(person);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @PreAuthorize("hasAuthority('own:person:delete')")
    @DeleteMapping(value = "/people/{personId}")
    public ResponseEntity<PersonModel> delete(@PathVariable int personId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfPersonId(request, personId);
        Person person = personService.getBy(personId);
        personService.delete(person);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @PreAuthorize("hasAuthority('own:person:get:workflow')")
    @GetMapping(value = "/workflows/{workflowId}/people")
    public ResponseEntity<CollectionModel<PersonModel>> getByWorkflow(@PathVariable int workflowId, HttpServletRequest request) {
        accessResourceService.validate2participants(request, workflowId);
        List<Person> people = personService.findAllBy(workflowId);
        PersonModelAssembler assembler = new PersonModelAssembler();
        CollectionModel<PersonModel> personModels = assembler.toCollectionModel(people);
        return ResponseEntity.ok().body(personModels);
    }

    @PreAuthorize("hasAuthority('own:person:search')")
    @GetMapping(value = "/enterprises/{enterpriseId}/enterprises/{enterpriseIdView}/participanttypes/{participantType}/persontype/{personType}/documenttype/{documentType}/documentnumber/{documentNumber}/jobs/{jobId}/people")
    public ResponseEntity<PersonModel> getBy3(@PathVariable int enterpriseId,
                                              @PathVariable int enterpriseIdView,
                                              @PathVariable int participantType,
                                              @PathVariable int personType,
                                              @PathVariable String documentType,
                                              @PathVariable String documentNumber,
                                              @PathVariable int jobId,
                                              HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        Optional<Person> optionalPerson = personService.findBy(enterpriseId, enterpriseIdView, participantType, personType, documentType, documentNumber, jobId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            PersonModelAssembler assembler = new PersonModelAssembler();
            PersonModel pesonModel = assembler.toModel(person);
            return ResponseEntity.ok().body(pesonModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAuthority('own:person:add:workflow')")
    @PutMapping(value = "/people/{personId}/workflows")
    public ResponseEntity<PersonModel> saveWorkflows(@PathVariable int personId, @RequestBody Person person) {
        Person person4workflow = personService.saveWorkflows(person);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel pesonModel = assembler.toModel(person4workflow);
        return ResponseEntity.ok().body(pesonModel);
    }

    @PreAuthorize("hasAuthority('own:person:edit:password')")
    @PutMapping(value = "/people/{personId}/resetpassword")
    public ResponseEntity<PersonModel> resetUserPassword(@PathVariable int personId, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfPersonId(request, personId);
        Person person = personService.resetUserPassword(personId);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok().body(personModel);
    }

    @PreAuthorize("hasAuthority('own:person:search:participant')")
    @GetMapping(value = "/enterprises/{enterpriseId}/participants/{participantType}/people")
    public ResponseEntity<CollectionModel<PersonModel>> getBy(@PathVariable int enterpriseId,
                                                              @PathVariable int participantType,
                                                              @RequestParam("findby") String fullname,
                                                              HttpServletRequest request) {
        accessResourceService.validateIfBelongEnterpriseId(request, enterpriseId);
        List<Person> personList = personService.findBy(enterpriseId, participantType, fullname);
        PersonModelAssembler assembler = new PersonModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(personList));
    }

    @PreAuthorize("hasAuthority('own:person:edit:document')")
    @PutMapping(value = "/documents/{documentId}/people")
    public ResponseEntity<CollectionModel<OperatorModel>> update(@PathVariable int documentId,
                                                                 @RequestParam("reasignIdentifiers") String reasignIdentifiers) {
        List<Operator> operators = participantService.update(documentId, reasignIdentifiers);
        OperatorModelAssembler assembler = new OperatorModelAssembler();
        CollectionModel<OperatorModel> operatorModel = assembler.toCollectionModel(operators);
        return ResponseEntity.ok().body(operatorModel);
    }

    //TODO verify grants
    @PreAuthorize("true")
    @PutMapping(value = "/people/{personId}/profile")
    public ResponseEntity<PersonModel> update(@PathVariable("personId") int personId,
                                              @RequestBody Person person,
                                              HttpServletRequest request) {
        accessResourceService.validateIfPersonIdIsPersonOfRequest(request, personId);
        personService.updateProfile(person, personId);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel2(person);
        return ResponseEntity.ok().body(personModel);
    }
}
