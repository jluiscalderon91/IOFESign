package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Deliverymail;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.exception.DocumentNotFinishedException;
import com.apsout.electronictestimony.api.repository.DeliverymailRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.ContentdeliverymailAllocator;
import com.apsout.electronictestimony.api.util.allocator.DeliverymailAllocator;
import com.apsout.electronictestimony.api.util.allocator.DocumentAllocator;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.DocumentState;
import com.apsout.electronictestimony.api.util.statics.TraceabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class DeliverymailServiceImpl implements DeliverymailService {
    private static Logger logger = LogManager.getLogger(DeliverymailServiceImpl.class);

    @Autowired
    private DeliverymailRepository repository;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliverysettingService deliverysettingService;
    @Autowired
    private DocumenttraceabilityService traceability;
    @Autowired
    private ContentdeliverymailService contentdeliverymailService;
    @Autowired
    private MailerService mailerService;
    @Autowired
    private PersonService personService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Deliverymail register(int documentId, Contentdeliverymail contentdeliverymail, HttpServletRequest request) {
        Document document = documentService.getBy(documentId);
        Person person = personService.getBy(request);
        final boolean finished = documentService.isFinished(document);
        final boolean canceled = documentService.isCanceled(document);
        if (!finished || canceled) {
            logger.warn(String.format("Document not finished yet or was canceled for documentId: %d", document.getId()));
            throw new DocumentNotFinishedException(String.format("El documento aún no cuenta con las firmas necesarias para ser enviada por correo electrónico", document.getId()));
        }
        Deliverymail deliverymail = DeliverymailAllocator.build(document);
        repository.save(deliverymail);
        ContentdeliverymailAllocator.build(deliverymail, contentdeliverymail);
        contentdeliverymailService.save(contentdeliverymail);
        traceability.save(person, document, DocumentState.SCHEDULE_ATTACH_DELIVERY, TraceabilityType.MAILING);
        return deliverymail;
    }

    @Override
    public Deliverymail save(Deliverymail deliverymail) {
        return repository.save(deliverymail);
    }

    @Override
    public Optional<Deliverymail> findBy(boolean sent) {
        return repository.findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(sent, Boolean.TRUE, Boolean.FALSE);
    }

    public Optional<Deliverymail> find4Send() {
        return this.findBy(Boolean.FALSE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deliver() {
        Optional<Deliverymail> optionalDeliverymail = this.find4Send();
        if (optionalDeliverymail.isPresent()) {
            Deliverymail deliverymail = optionalDeliverymail.get();
            logger.info(String.format("Preparing mail for deliverymail: %d", deliverymail.getId()));
            Contentdeliverymail contentdeliverymail = contentdeliverymailService.getBy(deliverymail);
            Document document = deliverymail.getDocumentByDocumentId();
            InternetAddress from = EmailAddress.getFromAddress();
            try {
                InternetAddress[] to = EmailAddress.prepareRecipients(contentdeliverymail.getRecipient(), ";");
                InternetAddress[] cc = EmailAddress.prepareRecipients(contentdeliverymail.getCc(), ";");
                String subject = contentdeliverymail.getSubject() == null || contentdeliverymail.getSubject().isEmpty() ? "-" : contentdeliverymail.getSubject();
                String body = contentdeliverymail.getBody();
                MimeMessage mimeMessage;
                final Boolean attachFiles = contentdeliverymail.getAttachFiles();
                if (attachFiles) {
                    List<File> attachments = resourceService.findAllFilesBy(document, ScopeResource.PRIVATE);
                    mimeMessage = mailerService.buildMessage(from, to, cc, subject, body, attachments);
                } else {
                    mimeMessage = mailerService.buildMessage(from, to, cc, subject, body);
                }
                mailerService.send(mimeMessage);
                DeliverymailAllocator.forUpdate(deliverymail);
                DocumentAllocator.forUpdateWithState(document, DocumentState.ATTACH_SENT);
                traceability.save(Default.IOFE_SIGN, document, DocumentState.ATTACH_SENT, TraceabilityType.MAILING);
                logger.info(String.format("Deliverymail sent for deliverymailId: %d", deliverymail.getId()));
            } catch (MessagingException e) {
                DeliverymailAllocator.forReducePriority(deliverymail);
                DocumentAllocator.forUpdateWithState(document, DocumentState.FINISHED);
                traceability.save(Default.IOFE_SIGN, document, DocumentState.ERROR_SENDING_ATTACH, TraceabilityType.MAILING);
                logger.error(String.format("Deliverymail was reduced its priority send for deliverymailId: %d", deliverymail.getId()), e);
            }
            documentService.save(document);
            this.save(deliverymail);
            logger.info(String.format("Deliverymail was updated with sienotificationId: %d", deliverymail.getId()));
        }
    }
}
