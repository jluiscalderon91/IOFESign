package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.model.RoleModel;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.modelassembler.RoleModelAssembler;
import com.apsout.electronictestimony.api.service.security.RoleService;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:role:get:page')")
    @GetMapping(value = "/roles")
    public ResponseEntity<CollectionModel<RoleModel>> getBy(Pageable pageable, HttpServletRequest request) {
        Page<Role> roles = roleService.findAllBy(pageable);
        RoleModelAssembler assembler = new RoleModelAssembler();
        PagedModel<RoleModel> pagedModel = resourcesAssembler.toModel(roles, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:role:add')")
    @PostMapping(value = "/roles")
    public ResponseEntity<RoleModel> save(@RequestBody Role role) {
        role = roleService.save(role);
        RoleModelAssembler assembler = new RoleModelAssembler();
        RoleModel roleModel = assembler.toModel(role);
        return ResponseEntity.ok().body(roleModel);
    }

    @PreAuthorize("hasAuthority('own:role:edit')")
    @PutMapping(value = "/roles")
    public ResponseEntity<RoleModel> update(@RequestBody Role role) {
        role = roleService.update(role);
        RoleModelAssembler assembler = new RoleModelAssembler();
        RoleModel roleModel = assembler.toModel(role);
        return ResponseEntity.ok(roleModel);
    }

    @PreAuthorize("hasAuthority('own:role:delete')")
    @DeleteMapping(value = "/roles/{roleId}")
    public ResponseEntity<RoleModel> update(@PathVariable("roleId") int roleId) {
        Role role = roleService.deleteBy(roleId);
        RoleModelAssembler assembler = new RoleModelAssembler();
        RoleModel roleModel = assembler.toModel(role);
        return ResponseEntity.ok().body(roleModel);
    }

    @PreAuthorize("hasAuthority('own:role:edit:authority')")
    @PutMapping(value = "/roles/{roleId}/authorities")
    public ResponseEntity<RoleModel> update(@PathVariable("roleId") int roleId, @RequestBody _AuthoritiesList authoritiesList, HttpServletRequest request) {
        Role role = roleService.updateAuthorities(roleId, authoritiesList);
        RoleModelAssembler assembler = new RoleModelAssembler();
        RoleModel roleModel = assembler.toModel(role);
        return ResponseEntity.ok(roleModel);
    }
}
