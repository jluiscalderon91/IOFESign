package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.AuthorityModel;
import com.apsout.electronictestimony.api.entity.model.PersonModel;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.modelassembler.AuthorityModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.PersonModelAssembler;
import com.apsout.electronictestimony.api.service.PersonService;
import com.apsout.electronictestimony.api.service.security.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class AuthorityController {

    @Autowired
    private PersonService personService;
    @Autowired
    private AuthorityService authorityService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:authority:get:page')")
    @GetMapping(value = "/authorities")
    public ResponseEntity<CollectionModel<AuthorityModel>> getBy(Pageable pageable, HttpServletRequest request) {
        Page<Authority> authorities = authorityService.findAllBy(pageable);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        PagedModel<AuthorityModel> pagedModel = resourcesAssembler.toModel(authorities, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:authority:get:role')")
    @GetMapping(value = "/roles/{roleId}/authorities")
    public ResponseEntity<CollectionModel<AuthorityModel>> getBy(@PathVariable("roleId") int roleId, HttpServletRequest request) {
        List<Authority> authorities = authorityService.findAllBy(roleId);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        CollectionModel<AuthorityModel> pagedModel = assembler.toCollectionModel(authorities);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:authority:add')")
    @PostMapping(value = "/authorities")
    public ResponseEntity<AuthorityModel> save(@RequestBody Authority authority) {
        authority = authorityService.save(authority);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        AuthorityModel authorityModel = assembler.toModel(authority);
        return ResponseEntity.ok().body(authorityModel);
    }

    @PreAuthorize("hasAuthority('own:authority:edit')")
    @PutMapping(value = "/authorities")
    public ResponseEntity<AuthorityModel> update(@RequestBody Authority authority) {
        authority = authorityService.update(authority);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        AuthorityModel authorityModel = assembler.toModel(authority);
        return ResponseEntity.ok(authorityModel);
    }

    @PreAuthorize("hasAuthority('own:authority:delete')")
    @DeleteMapping(value = "/authorities/{authorityId}")
    public ResponseEntity<AuthorityModel> update(@PathVariable("authorityId") int authorityId) {
        Authority authority = authorityService.deleteBy(authorityId);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        AuthorityModel authorityModel = assembler.toModel(authority);
        return ResponseEntity.ok().body(authorityModel);
    }

    @PreAuthorize("hasAuthority('own:person:edit:authority')")
    @PutMapping(value = "/people/{personId}/authorities")
    public ResponseEntity<PersonModel> update(@PathVariable("personId") int personId, @RequestBody _AuthoritiesList authoritiesList, HttpServletRequest request) {
        Person person = personService.updateAuthorities(personId, authoritiesList);
        PersonModelAssembler assembler = new PersonModelAssembler();
        PersonModel personModel = assembler.toModel(person);
        return ResponseEntity.ok(personModel);
    }

    @PreAuthorize("hasAuthority('own:authority:get:person')")
    @GetMapping(value = "/people/{personId}/authorities")
    public ResponseEntity<CollectionModel<AuthorityModel>> getByPerson(@PathVariable("personId") int personId, HttpServletRequest request) {
        List<Authority> authorities = authorityService.findAllByPerson(personId);
        AuthorityModelAssembler assembler = new AuthorityModelAssembler();
        CollectionModel<AuthorityModel> pagedModel = assembler.toCollectionModel(authorities);
        return ResponseEntity.ok(pagedModel);
    }
}
