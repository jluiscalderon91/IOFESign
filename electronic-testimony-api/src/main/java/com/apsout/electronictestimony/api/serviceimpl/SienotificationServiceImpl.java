package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.repository.SienotificationRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.SienotificationAllocator;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SienotificationServiceImpl implements SienotificationService {
    private static final Logger logger = LoggerFactory.getLogger(SienotificationServiceImpl.class);

    @Autowired
    private SienotificationRepository repository;
    @Autowired
    private SiemailerServiceImpl siemailerService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private SiecredentialService siecredentialService;
    @Autowired
    private SieemailoperatorService sieemailoperatorService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private SierecipientService sierecipientService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;

    @Override
    @Transactional
    public Sienotification save(Sienotification sienotification) {
        final Sienotification notificationSaved = repository.save(sienotification);
        final Integer personId = notificationSaved.getOperatorByOperatorId().getPersonByPersonId().getId();
        logger.info(String.format("Notification saved after operation of personId: %d, sienotificationId: %d", personId, notificationSaved.getId()));
        return notificationSaved;
    }

    @Override
    public Optional<Sienotification> findBy(byte sent) {
        return repository.findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(sent, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public Optional<Sienotification> find4Send() {
        return this.findBy(States.NOT_SENT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notificate() {
        Optional<Sienotification> optional = this.find4Send();
        if (optional.isPresent()) {
            Sienotification sienotification = optional.get();
            logger.info(String.format("Preparing mail for sienotificationId: %d", sienotification.getId()));
            final Operator operator = sienotification.getOperatorByOperatorId();
            Person person = operator.getPersonByPersonId();
            Document document = documentService.findByWithoutConditions(operator.getId());
            Enterprise enterprise;
            if (Default.ENTERPRISE_ID_VIEW == person.getEnterpriseIdView()) {
                enterprise = person.getEnterpriseByEnterpriseId();
            } else {
                enterprise = person.getEnterpriseByEnterpriseIdView();
            }
            Siecredential siecredential = siecredentialService.getLastActiveBy(enterprise);
            Sieemailoperator sieemailoperator = sieemailoperatorService.getBy(operator.getId());
            Sieemail sieemail = sieemailoperator.getSieemailBySieemailId();
            List<Sierecipient> sierecipients = sierecipientService.getAllBy(sieemail);
            if (sierecipients.isEmpty()) {
                logger.warn(String.format("Recipient list is empty for sieemailId: %d", sieemail.getId()));
                return;
            }
            try {
                List<String> stringRecipients = sierecipients.stream().map(sierecipient -> sierecipient.getAddress()).collect(Collectors.toList());
                InternetAddress from = EmailAddress.build(siecredential.getUsername());
                InternetAddress[] to = EmailAddress.prepareRecipients(stringRecipients);
                String subject = !sieemail.getSubject().isEmpty() ? sieemail.getSubject() : "Subject example";
                String body = !sieemail.getBody().isEmpty() ? sieemail.getBody() : "Body example";
                List<File> attachments = resourceService.findAllFilesBy(document, ScopeResource.PUBLIC);
                siemailerService.send(enterprise, from, to, subject, body, attachments);
                SienotificationAllocator.forUpdate(sienotification);
                headbalanceallocationService.consumeBalance(enterprise, document, new Person(Default.SUPERADM_PERSON_ID), com.apsout.electronictestimony.api.util.enums.Service.SIE_NOTIFICATION);
                logger.info(String.format("Sie notification mail sent for sienotificationId: %d", sienotification.getId()));
            } catch (MessagingException e) {
                SienotificationAllocator.forReducePriority(sienotification);
                logger.error(String.format("Sie notification mail was reduced its priority send for sienotificationId: %d", sienotification.getId()), e);
            }
            this.save(sienotification);
            logger.info(String.format("Sienotification was updated with sienotificationId: %d", sienotification.getId()));
        }
    }
}
