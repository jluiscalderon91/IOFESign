package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    private PagedResourcesAssembler resourcesAssembler = new PagedResourcesAssembler(null, null);

    @PreAuthorize("hasAuthority('own:mail:resend:url')")
    @PostMapping("/notifications/{personId}/people/{hashIdentifier}/documents/resend")
    public ResponseEntity<Object> resendUrl(@PathVariable String personId, @PathVariable String hashIdentifier) {
        notificationService.resend(Integer.parseInt(personId), hashIdentifier);
        return ResponseEntity.ok(null);
    }
}
