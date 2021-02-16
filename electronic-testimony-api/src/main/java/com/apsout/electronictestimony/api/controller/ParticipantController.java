package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.model.ParticipantModel;
import com.apsout.electronictestimony.api.entity.model.pojo._GroupParticipant;
import com.apsout.electronictestimony.api.modelassembler.OutsideParticipantModelAssembler;
import com.apsout.electronictestimony.api.modelassembler.ParticipantModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.ParticipantService;
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
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private AccessResourceService accessResourceService;

    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:participant:get:workflow:page')")
    @GetMapping(value = "/workflows/{workflowId}/participants/paged")
    public ResponseEntity<CollectionModel<ParticipantModel>> getAllBy(@PathVariable int workflowId,
                                                                      Pageable pageable,
                                                                      HttpServletRequest request) {
        accessResourceService.validate2participants(request, workflowId);
        Page<Participant> participants = participantService.getAllBy(workflowId, pageable);
        ParticipantModelAssembler assembler = new ParticipantModelAssembler();
        PagedModel<ParticipantModel> pagedModel = resourcesAssembler.toModel(participants, assembler);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAuthority('own:participant:add:workflow')")
    @PostMapping(value = "/participants")
    public ResponseEntity<CollectionModel<ParticipantModel>> save(@RequestBody _GroupParticipant groupParticipant) {
        List<Participant> participants = participantService.save(groupParticipant);
        ParticipantModelAssembler assembler = new ParticipantModelAssembler();
        CollectionModel<ParticipantModel> collectionModel = assembler.toCollectionModel(participants);
        return ResponseEntity.ok().body(collectionModel);
    }

    @PreAuthorize("hasAuthority('own:participant:edit:workflow')")
    @PutMapping(value = "/participants")
    public ResponseEntity<CollectionModel<ParticipantModel>> edit(@RequestBody _GroupParticipant groupParticipant) {
        List<Participant> participants = participantService.update(groupParticipant);
        ParticipantModelAssembler assembler = new ParticipantModelAssembler();
        CollectionModel<ParticipantModel> collectionModel = assembler.toCollectionModel(participants);
        return ResponseEntity.ok().body(collectionModel);
    }

    @PreAuthorize("hasAuthority('own:participant:delete')")
    @DeleteMapping(value = "/participants")
    public ResponseEntity<ParticipantModel> delete(@RequestParam("participantId") int participantId) {
        Participant participant = participantService.getBy(participantId);
        participantService.delete(participant);
        ParticipantModelAssembler assembler = new ParticipantModelAssembler();
        ParticipantModel participantModel = assembler.toModel(participant);
        return ResponseEntity.ok().body(participantModel);
    }

    @PreAuthorize("hasAuthority('own:participant:get:workflow')")
    @GetMapping(value = "/workflows/{workflowId}/participants")
    public ResponseEntity<CollectionModel<ParticipantModel>> getBy(@PathVariable int workflowId,
                                                                   @RequestParam String onlyreplaceable,
                                                                   HttpServletRequest request) {
        accessResourceService.validate2participants(request, workflowId);
        List<Participant> participants = participantService.getAllBy(workflowId, Boolean.valueOf(onlyreplaceable));
        ParticipantModelAssembler assembler = new ParticipantModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(participants));
    }

    @PreAuthorize("hasAuthority('outside:participant:get:workflow:replaceable')")
    @GetMapping(value = "/outside/workflows/{workflowId}/participants/replaceables")
    public ResponseEntity<CollectionModel<ParticipantModel>> getBy(@PathVariable int workflowId, HttpServletRequest request) {
        accessResourceService.validate2participants(request, workflowId);
        List<Participant> participants = participantService.getAllReplaceablesBy4Outside(workflowId);
        OutsideParticipantModelAssembler assembler = new OutsideParticipantModelAssembler();
        return ResponseEntity.ok(assembler.toCollectionModel(participants));
    }
}
