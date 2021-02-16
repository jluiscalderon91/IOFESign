package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Passwordretriever;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.exception.UUIDNotFoundException;
import com.apsout.electronictestimony.api.exception.UserNotFoundException;
import com.apsout.electronictestimony.api.exception.VerificationNoCompletedException;
import com.apsout.electronictestimony.api.repository.PasswordretrieverRepository;
import com.apsout.electronictestimony.api.service.ApplicationService;
import com.apsout.electronictestimony.api.service.MailerService;
import com.apsout.electronictestimony.api.service.PasswordretrieverService;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.PasswordretrieverAllocator;
import com.apsout.electronictestimony.api.util.allocator.security.UserAllocator;
import com.apsout.electronictestimony.api.util.crypto.Hash;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.others.StringUtil;
import com.apsout.electronictestimony.api.util.statics.Param;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PasswordretrieverServiceImpl implements PasswordretrieverService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordretrieverServiceImpl.class);

    @Autowired
    private PasswordretrieverRepository repository;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailerService mailerService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<Passwordretriever> findBy(String uuid) {
        return repository.findBy(uuid);
    }

    @Override
    public Passwordretriever save(Passwordretriever passwordretriever) {
        return repository.save(passwordretriever);
    }

    @Transactional(rollbackFor = Exception.class)
    public Passwordretriever getUUID() {
        String uuid = applicationService.getUUID();
        Passwordretriever passwordretriever = PasswordretrieverAllocator.build(uuid);
        return repository.save(passwordretriever);
    }

    @Transactional(rollbackFor = Exception.class)
    public Passwordretriever sent(String uuid, String username) {
        Optional<User> optional = userService.findBy(username);
        if (!optional.isPresent()) {
            throw new UserNotFoundException("No hay ninguna cuenta en el IOFESign con la información que has proporcionado.");
        }
        User user = optional.get();
        Person person = user.getPersonByPersonId();
        Optional<Passwordretriever> optional1 = findBy(uuid);
        if (!optional1.isPresent()) {
            throw new UUIDNotFoundException("El código de sesión enviado no está dentro de la lista válida o ya no se encuentra activa.");
        }
        Passwordretriever passwordretriever = optional1.get();
        String hashFirstStep = Hash.sha256(Hash.sha256(LocalDateTime.now().toString() + uuid));
        int disableds = disableExistentsBy(username);
        logger.info(String.format("Disabled for: %s, %d rows", username, disableds));
        PasswordretrieverAllocator.forSent(passwordretriever, username, person.getEmail(), hashFirstStep);
        return repository.save(passwordretriever);
    }

    private int disableExistentsBy(String username) {
        List<Integer> passwordretrieverIds = repository.findAllBy(username).stream().map(Passwordretriever::getId).collect(Collectors.toList());
        return repository.disableAllBy(passwordretrieverIds);
    }

    @Override
    public Optional<Passwordretriever> findBy(byte sent) {
        return repository.findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(sent, States.ACTIVE, States.EXISTENT);
    }

    public Optional<Passwordretriever> find4Send() {
        return this.findBy(States.NOT_SENT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notificate() {
        Optional<Passwordretriever> optional = this.find4Send();
        if (optional.isPresent()) {
            Passwordretriever retriever = optional.get();
            logger.info(String.format("Preparing mail for passwordRetrieverId: %d", retriever.getId()));
            List<String> stringRecipients = Arrays.asList(retriever.getEmail());
            try {
                InternetAddress from = EmailAddress.getFromAddress();
                InternetAddress[] to = EmailAddress.prepareRecipients(stringRecipients);
                String subject = "Código de verificación para recuperación de contraseña";
                String verificationCode = StringUtil.buildRandomInt(6);
                String body = "Su código de verificación es: " + verificationCode;
                mailerService.send(from, Arrays.asList(to), subject, body);
                PasswordretrieverAllocator.forUpdate(retriever, verificationCode);
                logger.info(String.format("Password retriever mail sent to: %s, passwordretrieverId: %d", stringRecipients, retriever.getId()));
            } catch (AddressException e) {
                PasswordretrieverAllocator.forReducePriority(retriever);
                logger.error(String.format("Password retriever mail was reduced its priority send for passwordretrieverId: %d", retriever.getId()), e);
            }
            this.save(retriever);
            logger.info(String.format("Password retriever was updated with passwordretrieverId: %d", retriever.getId()));
        }
    }

    public Passwordretriever verifyCode(String uuid, String hash, String verificationCode) {
        Optional<Passwordretriever> optional = findBy(uuid, hash, verificationCode);
        if (optional.isPresent()) {
            Passwordretriever passwordretriever = optional.get();
            verifyExpirationVerificationCode(passwordretriever);
            String hashSecondStep = Hash.sha256(Hash.sha256(LocalDateTime.now().toString() + uuid));
            PasswordretrieverAllocator.forMatch(passwordretriever, hashSecondStep, States.MATCHED);
            return this.save(passwordretriever);
        }
        throw new VerificationNoCompletedException("El código ingresado no corresponde al enviado. Por favor, verifíquelo e ingrese nuevamente el código.");
    }

    public Optional<Passwordretriever> findBy(String uuid, String hashFirstStep, String verificationCode) {
        return repository.findBy(uuid, hashFirstStep, verificationCode);
    }

    public Optional<Passwordretriever> findBy(String hash1, String hash2) {
        return repository.findBy(hash1, hash2);
    }

    public Passwordretriever getBy(String hash1, String hash2) {
        Optional<Passwordretriever> optional = repository.findBy(hash1, hash2);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Passwordretriever not found by hash1: %s, hash2: %s", hash1, hash2));
    }

    public void validate(String hash1) {
        Optional<Passwordretriever> optional = repository.findByHash1(hash1);
        if (!optional.isPresent()) {
            throw new VerificationNoCompletedException("Usted no cuenta con los permisos necesarios para acceder al recurso solicitado. Por favor, inténtelo nuevamente.");
        } else {
            Passwordretriever passwordretriever = optional.get();
            if (passwordretriever.getSentAt() != null) {
                verifyExpirationVerificationCode(passwordretriever);
            }
        }
    }

    public void validate(String hash1, String hash2) {
        Optional<Passwordretriever> optional = findBy(hash1, hash2);
        if (!optional.isPresent()) {
            throw new VerificationNoCompletedException("Usted no cuenta con los permisos necesarios para acceder al recurso solicitado. Por favor, inténtelo nuevamente.");
        } else {
            Passwordretriever passwordretriever = optional.get();
            verifyExpirationVerificationCode(passwordretriever);
        }
    }

    private void verifyExpirationVerificationCode(Passwordretriever passwordretriever) {
        long now = Instant.now().getEpochSecond();
        long sent = passwordretriever.getSentAt().getTime() / 1000;
        final long diff = now - sent;
        if (diff > Param.VERIFICATION_CODE_VALIDITY * 3600) {
            throw new VerificationNoCompletedException("El código de verificación ya no es válido. Por favor, solicite uno nuevo.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String hash1, String hash2, String password) {
        validate(hash1, hash2);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Passwordretriever passwordretriever = getBy(hash1, hash2);
        User user = userService.getBy(passwordretriever.getUsername());
        UserAllocator.forUpdate(user, encodedPassword);
        PasswordretrieverAllocator.forFinish(passwordretriever);
        this.save(passwordretriever);
        userService.save(user);
    }
}
