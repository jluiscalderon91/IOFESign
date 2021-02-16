package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.model.SieemailModel;
import com.apsout.electronictestimony.api.modelassembler.SieemailModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.SieemailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO review the way to improvement CrossOrigin operation
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class SieemailController {
    @Autowired
    private SieemailService sieemailService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAuthority('own:sie:add:mailbody:participant')")
    @PostMapping(value = "/sieemails")
    public ResponseEntity<SieemailModel> save(@RequestBody Sieemail sieemail, HttpServletRequest request) {
        Sieemail sieemail1 = sieemailService.save(sieemail);
        SieemailModelAssembler assembler = new SieemailModelAssembler();
        SieemailModel sieemailModel = assembler.toModel(sieemail1);
        return ResponseEntity.ok(sieemailModel);
    }

    @PreAuthorize("hasAuthority('own:sie:get:mailbody:participant')")
    @GetMapping(value = "/participants/{participantId}/sieemails")
    public ResponseEntity<Map<String, Object>> getBy(@PathVariable int participantId, HttpServletRequest request) {
        Optional<Sieemail> optionalSieemail = sieemailService.findByParticipantId(participantId);
        if (optionalSieemail.isPresent()) {
            HashMap<String, Object> map = new HashMap<>();
            SieemailModelAssembler assembler = new SieemailModelAssembler();
            SieemailModel sieemailModel = assembler.toModel(optionalSieemail.get());
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("sieemail", sieemailModel);
            Map<String, Object> outerMap = new HashMap<>();
            outerMap.put("_embedded", innerMap);
            return ResponseEntity.ok(outerMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAuthority('own:sie:edit:mailbody:participant')")
    @PutMapping(value = "/sieemails")
    public ResponseEntity<SieemailModel> update(@RequestBody Sieemail sieemail, HttpServletRequest request) {
        Sieemail sieemail1 = sieemailService.update(sieemail);
        SieemailModelAssembler assembler = new SieemailModelAssembler();
        SieemailModel sieemailModel = assembler.toModel(sieemail1);
        return ResponseEntity.ok(sieemailModel);
    }

}
