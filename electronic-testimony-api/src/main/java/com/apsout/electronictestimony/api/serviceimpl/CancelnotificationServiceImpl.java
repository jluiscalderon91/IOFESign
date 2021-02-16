package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.repository.CancelnotificationRepository;
import com.apsout.electronictestimony.api.service.CancelnotificationService;
import com.apsout.electronictestimony.api.service.MailerService;
import com.apsout.electronictestimony.api.service.MailtemplateService;
import com.apsout.electronictestimony.api.service.ObservationcancelService;
import com.apsout.electronictestimony.api.util.allocator.CancelnotificationAllocator;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.statics.MailtemplateType;
import com.apsout.electronictestimony.api.util.statics.RecipientType;
import com.apsout.electronictestimony.api.util.statics.Replaceable;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CancelnotificationServiceImpl implements CancelnotificationService {
    private static final Logger logger = LoggerFactory.getLogger(CancelnotificationServiceImpl.class);

    @Autowired
    private CancelnotificationRepository repository;
    @Autowired
    private MailerService mailerService;
    @Autowired
    private ObservationcancelService observationcancelService;
    @Autowired
    private MailtemplateService mailtemplateService;

    @Override
    public Cancelnotification save(Cancelnotification passwordretriever) {
        return repository.save(passwordretriever);
    }

    @Override
    public Optional<Cancelnotification> findBy(byte sent) {
        return repository.findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(sent, States.ACTIVE, States.EXISTENT);
    }

    public Optional<Cancelnotification> find4Send() {
        return this.findBy(States.NOT_SENT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notificate() {
        Optional<Cancelnotification> optional = this.find4Send();
        if (optional.isPresent()) {
            Cancelnotification cancelnotification = optional.get();
            Document document = cancelnotification.getDocumentByDocumentId();
            Person person = cancelnotification.getPersonByPersonId();
            Enterprise enterprise = person.getEnterpriseByEnterpriseId();
            logger.info(String.format("Preparing mail for cancelnotificationId: %d", cancelnotification.getId()));
            List<String> stringRecipients = Arrays.asList(cancelnotification.getEmail());
            InternetAddress from = EmailAddress.getFromAddress();
            try {
                InternetAddress[] to = EmailAddress.prepareRecipients(stringRecipients);
                Optional<Mailtemplate> optMailtemplate = mailtemplateService.findBy(enterprise, MailtemplateType.DOCUMENT_CANCELLED, RecipientType.USER_AND_INVITEDUSER);
                String subject = this.buildSubject(optMailtemplate, document);
                String body = this.buildBody(person, optMailtemplate, document);
                mailerService.send(from, Arrays.asList(to), subject, body, true);
                CancelnotificationAllocator.forUpdate(cancelnotification);
                logger.info(String.format("Cancel notification mail sent to: %s, cancelnotificationId: %d", stringRecipients, cancelnotification.getId()));
            } catch (MessagingException e) {
                CancelnotificationAllocator.forReducePriority(cancelnotification);
                logger.error(String.format("Cancel notification mail was reduced its priority send for cancelnotificationId: %d", cancelnotification.getId()), e);
            }
            this.save(cancelnotification);
            logger.info(String.format("Cancel notification was updated with cancelnotificationId: %d", cancelnotification.getId()));
        }
    }

    private String buildSubject(Optional<Mailtemplate> optional, Document document) {
        if (optional.isPresent()) {
            Mailtemplate mailtemplate = optional.get();
            String subject = document.getSubject();
            return mailtemplate.getSubject().replaceAll(Pattern.quote(Replaceable.RegexSubject), subject);
        } else {
            return "Anulación de documento - IOFESign";
        }
    }

    private String buildBody(Person person, Optional<Mailtemplate> optMailtemplate, Document document) {
        String subject = buildSubject(optMailtemplate, document);
        Optional<Observationcancel> optObsCancelDoc = observationcancelService.findByDocument(document);
        String fullname = person.getFullname();
        String reasonCancellation = optObsCancelDoc.isPresent() ? optObsCancelDoc.get().getDescription() : "No especificado";
        String datetimeCancellation = optObsCancelDoc.isPresent() ? optObsCancelDoc.get().getCreateAt().toString() : "No especificado";
        String dateToday = DateUtil.today("dd-MM-yyyy");
        String datetimeToday = DateUtil.moment("dd-MM-yyyy hh:mm:ss");
        if (optMailtemplate.isPresent()) {
            Mailtemplate mailtemplate = optMailtemplate.get();
            return mailtemplate.getBody()
                    .replaceAll(Pattern.quote(Replaceable.RegexSubject), subject)
                    .replaceAll(Pattern.quote(Replaceable.RegexCancellerFullname), fullname)
                    .replaceAll(Pattern.quote(Replaceable.RegexReasonCancellation), reasonCancellation)
                    .replaceAll(Pattern.quote(Replaceable.RegexDatetimeCancellation), datetimeCancellation)
                    .replaceAll(Pattern.quote(Replaceable.RegexDate), dateToday)
                    .replaceAll(Pattern.quote(Replaceable.RegexDatetime), datetimeToday);
        } else {
            return new StringBuilder().append("Hola!<br><br>")
                    .append("Por medio de la presente te comunicamos que un documento fue anulado con el siguiente detalle:<br><br>")
                    .append("<b>Asunto del documento:</b> ")
                    .append(subject)
                    .append("<br>")
                    .append("<b>Persona que realizó la anulación:</b> ")
                    .append(fullname)
                    .append("<br>")
                    .append("<b>Motivo de la anulación:</b> ")
                    .append(reasonCancellation)
                    .append("<br>")
                    .append("<b>Fecha y hora de anulación:</b> ")
                    .append(datetimeCancellation).toString();
        }
    }
}
