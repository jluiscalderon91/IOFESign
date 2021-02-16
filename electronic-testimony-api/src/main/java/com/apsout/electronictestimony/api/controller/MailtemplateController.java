package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Mailtemplate;
import com.apsout.electronictestimony.api.entity.model.MailtemplateModel;
import com.apsout.electronictestimony.api.modelassembler.MailtemplateModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.MailtemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class MailtemplateController {

    @Autowired
    private MailtemplateService mailtemplateService;
    @Autowired
    private AccessResourceService accessResourceService;

    @PreAuthorize("hasAuthority('own:document:mail:template:get')")
    @GetMapping(value = "/enterprises/{enterpriseId}/documents/{documentId}/mailtemplateTypes/{mailtemplateTypeId}/participantTypes/{participantTypeId}/mailtemplates")
    public ResponseEntity<MailtemplateModel> delete(@PathVariable int enterpriseId,
                                                    @PathVariable int documentId,
                                                    @PathVariable int mailtemplateTypeId,
                                                    @PathVariable int participantTypeId,
                                                    HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfDocumentId(request, documentId);
        Mailtemplate mailtemplate = mailtemplateService.findReplacedBy(enterpriseId, mailtemplateTypeId, participantTypeId, documentId);
        MailtemplateModelAssembler assembler = new MailtemplateModelAssembler();
        MailtemplateModel model = assembler.toModel(mailtemplate);
        return ResponseEntity.ok().body(model);
    }
}
