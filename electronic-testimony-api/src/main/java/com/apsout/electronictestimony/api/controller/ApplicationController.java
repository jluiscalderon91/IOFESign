package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAuthority('own:application:get:staticdata')")
    @GetMapping(value = "/staticdata")
    public ResponseEntity<HashMap<String, Object>> getInitialData(HttpServletRequest request) {
        _Embedded _embedded = applicationService.getStaticData(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @PreAuthorize("hasAuthority('outside:application:get:staticdata')")
    @GetMapping(value = "/outside/staticdata")
    public ResponseEntity<HashMap<String, Object>> getInitialData4Outside(HttpServletRequest request) {
        _Embedded _embedded = applicationService.getStaticData4Outside(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }

    @PreAuthorize("true")
    @GetMapping(value = "/staticdata/reload")
    public ResponseEntity<HashMap<String, Object>> reloadStaticData(HttpServletRequest request) {
        accessResourceService.validateIfPersonOfRequestIsSuperadmin(request);
        applicationService.reloadStaticData();
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("true")
    @GetMapping(value = "/actualbalance")
    public ResponseEntity<HashMap<String, Object>> getActualBalance(HttpServletRequest request) {
        _Embedded _embedded = applicationService.getActualBalance(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("_embedded", _embedded);
        return ResponseEntity.ok().body(map);
    }
}
