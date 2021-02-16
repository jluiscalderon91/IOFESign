package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.exception.DocumentNotFoundException;
import com.apsout.electronictestimony.api.exception.NotificationNotFoundException;
import com.apsout.electronictestimony.api.exception.UrlExpiredException;
import com.apsout.electronictestimony.api.exception.UrlInactiveException;
import com.apsout.electronictestimony.api.repository.NotificationRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.NotificationAllocator;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.statics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository repository;
    @Autowired
    private MailerService mailerService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private MailtemplateService mailtemplateService;

    @Override
    @Transactional
    public Notification save(Notification notification) {
        final Notification notificationSaved = repository.save(notification);
        final Integer personId = notificationSaved.getOperatorByOperatorId().getPersonByPersonId().getId();
        logger.info(String.format("Notification saved with personId: %d, notificationId: %d", personId, notificationSaved.getId()));
        return notificationSaved;
    }

    @Override
    public Optional<Notification> findBy(byte enabled, byte sent) {
        return repository.findFirstByEnabledAndSentAndActiveAndDeletedOrderByPriorityAsc(enabled, sent, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public Optional<Notification> find4Send() {
        return this.findBy(States.DISABLED, States.NOT_SENT);
    }

    @Override
    public void notificate() {
        Optional<Notification> optional = this.find4Send();
        if (optional.isPresent()) {
            Notification notification = optional.get();
            logger.info(String.format("Preparing mail for notificationId: %d", notification.getId()));
            final Operator operator = notification.getOperatorByOperatorId();
            Person person = operator.getPersonByPersonId();
            Document document = documentService.findByOperatorId(operator.getId());
            InternetAddress from = EmailAddress.getFromAddress();
            List<InternetAddress> to = EmailAddress.prepareRecipients(person.getEmail());
            Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseId();
            Optional<Mailtemplate> optMailtemplate = mailtemplateService.findBy(enterprise, MailtemplateType.DOCUMENT_PENDING_SIGNATURE, notification.getType());
            final String subject = this.buildSubject(optMailtemplate, document);
            final String body = this.buildBody(optMailtemplate, person, document, notification.getType());
            mailerService.send(from, to, subject, body, true);
            logger.info(String.format("Notification sent from: %s, to: %s, notificationId: %d", from, to, notification.getId()));
            NotificationAllocator.forUpdate(notification);
            this.save(notification);
        }
    }

    private String buildBody(Optional<Mailtemplate> optMailtemplate, Person person, Document document, int notificationType) {
        String urlSignatureOperator;
        if (RecipientType.INVITED == notificationType) {
            urlSignatureOperator = documentService.buildUrl(person, document);
        } else {
            urlSignatureOperator = Global.ROOT_URL_MAIL;
        }
        if (optMailtemplate.isPresent()) {
            String subject = buildSubject(optMailtemplate, document);
            Mailtemplate mailtemplate = optMailtemplate.get();
            String dateToday = DateUtil.today("dd-MM-yyyy");
            String datetimeToday = DateUtil.moment("dd-MM-yyyy hh:mm:ss");
            String somebody = person.getFullname();
            String projectName = subject.contains("PROYECTO") ? subject.substring(subject.indexOf("PROYECTO")).trim() : "";
            return mailtemplate.getBody()
                    .replaceAll(Pattern.quote(Replaceable.RegexSubject), subject)
                    .replaceAll(Pattern.quote(Replaceable.RegexDate), dateToday)
                    .replaceAll(Pattern.quote(Replaceable.RegexDatetime), datetimeToday)
                    .replaceAll(Pattern.quote(Replaceable.RegexSomebody), somebody)
                    .replaceAll(Pattern.quote(Replaceable.RegexUrlSignature), urlSignatureOperator)
                    .replaceAll(Pattern.quote(Replaceable.RegexProjectName), projectName);
        } else {
            if (RecipientType.INVITED == notificationType) {
                return new StringBuilder("Hola,<br><br>")
                        .append("Has recibido un documento que requiere tu revisión y/o firma digital, puedes verlo haciendo clic en el siguiente enlace:<br><br>")
                        .append(urlSignatureOperator).toString();
            } else {
                return new StringBuilder("Hola,<br><br>")
                        .append("Has recibido un documento que requiere tu revisión y/o firma digital, puede verlo accediendo a su bandeja en el siguiente enlace:<br><br>")
                        .append(urlSignatureOperator).toString();
            }
        }
    }

    private String buildSubject(Optional<Mailtemplate> optional, Document document) {
        if (optional.isPresent()) {
            Mailtemplate mailtemplate = optional.get();
            String subject = document.getSubject();
            return mailtemplate.getSubject().replaceAll(Pattern.quote(Replaceable.RegexSubject), subject);
        } else {
            return "Aviso IOFESign: " + document.getSubject();
        }
    }

    public Optional<Notification> find4InvalidateBy(int operatorId) {
        return repository.findByOperatorIdAndActiveAndDeleted(operatorId, States.ACTIVE, States.EXISTENT);
    }

    public void invalidateBy(Operator operator) {
        Optional<Notification> optional = this.find4InvalidateBy(operator.getId());
        if (optional.isPresent()) {
            Notification notification = optional.get();
            NotificationAllocator.forDisable(notification);
            this.save(notification);
        }
    }

    public Optional<Notification> findBy(int personId, Document document) {
        Optional<Operator> optional = operatorService.getOptionalBy(personId, document.getId());
        if (optional.isPresent()) {
            final int operatorId = optional.get().getId();
            return this.find4InvalidateBy(operatorId);
        } else {
            logger.warn(String.format("Operator not found by personId: %d, documentId: %d", personId, document.getId()));
            throw new DocumentNotFoundException("Recurso inexistente para los parámetros enviados, comuníquese con el administrador.");
        }
    }

    public void verifyBy(int personId, String hashIdentifier) {
        Document document = documentService.getBy(hashIdentifier);
        Optional<Notification> optional = this.findBy(personId, document);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            if (notification.getSent() == States.NOT_SENT) {
                throw new UrlInactiveException("La URL está siendo procesada y pronto estará disponible.");
            }
            long now = Instant.now().getEpochSecond();
            long sent = notification.getSentAt().getTime() / 1000;
            final long diff = now - sent;
            logger.info(String.format("Difference between moments by personId: %d, hashIdentifier: %s, diff: %d seconds", personId, hashIdentifier, diff));
            if (notification.getActive() == States.INACTIVE) {
                throw new UrlInactiveException("La URL ya fue usada y se encuentra desactivada.");
            } else {
                if (diff > Param.URL_VALIDITY * 3600) {
                    throw new UrlExpiredException("La URL se encuentra expirada, comuníquese con el administrador de sistemas.");
                }
            }
            logger.info(String.format("Valid URL for personId: %d, hashIdentifier: %s, diff: %d seconds", personId, hashIdentifier, diff));
        } else {
            logger.error(String.format("Notification not found for personId: %d, documentId: %d", personId, document.getId()));
            throw new NotificationNotFoundException("El posible que usted ya haya realizado la firma o que el documento esté siendo firmado por otro usuario o que el enlace sea incorrecto. Por favor, comuníquese con el administrador.");
        }
    }

    //TODO verificar el parámetro PersonId que pudo ser agregado para que pase la funcionalidad
    @Transactional
    public void resend(int personId, String hashIdentifier) {
        Document document = documentService.getBy(hashIdentifier);
        Optional<Notification> optional = this.findBy(personId, document);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            NotificationAllocator.forUpdateResend(notification);
            this.save(notification);
            logger.info(String.format("Ordered for resend URL for personId: %d, hashIdentifier: %s", personId, hashIdentifier));
        } else {
            logger.error(String.format("Notification not found for personId: %d, hashIdentifier: %s", personId, hashIdentifier));
            throw new NotificationNotFoundException("El recurso no se encuentra disponible, comuníquese con el administrador de sistemas.");
        }
    }
}
