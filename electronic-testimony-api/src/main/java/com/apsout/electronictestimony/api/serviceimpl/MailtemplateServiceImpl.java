package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Mailtemplate;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.exception.ResourceNotFoundException;
import com.apsout.electronictestimony.api.repository.MailtemplateRepository;
import com.apsout.electronictestimony.api.service.DocumentService;
import com.apsout.electronictestimony.api.service.MailtemplateService;
import com.apsout.electronictestimony.api.service.ResourceService;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.statics.Replaceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MailtemplateServiceImpl implements MailtemplateService {
    private static final Logger logger = LoggerFactory.getLogger(MailtemplateServiceImpl.class);

    @Autowired
    private MailtemplateRepository repository;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ResourceService resourceService;

    public Optional<Mailtemplate> findBy(int enterpriseId, int type, int recipientType) {
        return repository.findBy(enterpriseId, type, recipientType);
    }

    public Optional<Mailtemplate> findBy(Enterprise enterprise, int type, int recipientType) {
        return repository.findBy(enterprise.getId(), type, recipientType);
    }

    public Mailtemplate findReplacedBy(int enterpriseId, int type, int recipientType, int documentId) {
        Optional<Mailtemplate> optional = repository.findBy(enterpriseId, type, recipientType);
        Document document = documentService.getBy(documentId);
        final String url = new StringBuilder(Global.ROOT_API_V1)
                .append("/public/outside/documents/")
                .append(documentId)
                .append("/")
                .append(document.getHashIdentifier())
                .append("/stream").toString();
        String dateToday = DateUtil.today("dd-MM-yyyy");
        String datetimeToday = DateUtil.moment("dd-MM-yyyy hh:mm:ss");
        String subjectDocument = document.getSubject();
        String subject;
        String body;
        Mailtemplate mailtemplate;
        if (optional.isPresent()) {
            mailtemplate = optional.get();
            subject = mailtemplate.getSubject();
            body = mailtemplate.getBody();
        } else {
            subject = Replaceable.RegexSubject;
            body = "Haga <a href=\"" + Replaceable.RegexURLResource + "\" target=\"_blank\" rel=\"noopener\">clic aqu&iacute;</a> para descargar los archivos adjuntos";
            mailtemplate = new Mailtemplate();
        }
        String verificationCode = "";
        if (body.contains(Replaceable.RegexVerificationCode)) {
            List<Resource> resources = resourceService.findAllBy(document);
            if (resources.isEmpty()) {
                throw new ResourceNotFoundException(String.format("Resources not found for documentId: %d", documentId));
            }
            Resource resource = resources.get(0);
            verificationCode = resource.getResumeHash();
        }
        String replacedSubject = subject
                .replaceAll(Pattern.quote(Replaceable.RegexSubject), subjectDocument)
                .replaceAll(Pattern.quote(Replaceable.RegexDate), dateToday)
                .replaceAll(Pattern.quote(Replaceable.RegexDatetime), datetimeToday);
        String replacedBody = body
                .replaceAll(Pattern.quote(Replaceable.RegexSubject), subjectDocument)
                .replaceAll(Pattern.quote(Replaceable.RegexDate), dateToday)
                .replaceAll(Pattern.quote(Replaceable.RegexDatetime), datetimeToday)
                .replaceAll(Pattern.quote(Replaceable.RegexURLResource), url)
                .replaceAll(Pattern.quote(Replaceable.RegexVerificationCode), verificationCode);
        mailtemplate.setSubject(replacedSubject);
        mailtemplate.setBody(replacedBody);
        return mailtemplate;
    }
}
