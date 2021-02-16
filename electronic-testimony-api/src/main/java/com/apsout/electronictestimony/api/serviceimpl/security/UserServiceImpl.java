package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.exception.UserNotFoundException;
import com.apsout.electronictestimony.api.repository.security.UserRepository;
import com.apsout.electronictestimony.api.service.MailerService;
import com.apsout.electronictestimony.api.service.security.RoleService;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.security.UserAllocator;
import com.apsout.electronictestimony.api.util.others.EmailAddress;
import com.apsout.electronictestimony.api.util.security.JWTTokenUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailerService mailerService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public User save(User user) {
        final User saved = userRepository.save(user);
        logger.info(String.format("User saved with userId: %d, username: %s", user.getId(), user.getUsername()));
        return saved;
    }

    public Optional<User> find4Send() {
        return userRepository.findFirstBySentCredsAndActiveAndDeleted(States.NOT_SENT, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public void notificate() {
        Optional<User> optional = this.find4Send();
        if (optional.isPresent()) {
            User user = optional.get();
            logger.info(String.format("Preparing mail for userId: %d", user.getId()));
            Person person = user.getPersonByPersonId();
            InternetAddress from = EmailAddress.getFromAddress();
            List<InternetAddress> to = EmailAddress.prepareRecipients(person.getEmail());
            final String subject = "Credenciales de acceso al sistema de firma digital";
            final String clearPassword = RandomString.make(10);
            final String body = buildBody(person, user, clearPassword);
            mailerService.send(from, to, subject, body);
            logger.info(String.format("Notification sent from: %s, to: %s, notificationId: %d", from, to, user.getId()));
            UserAllocator.forUpdate(user, bCryptPasswordEncoder.encode(clearPassword));
            this.save(user);
        }
    }

    private String buildBody(Person person, User user, String clearPassword) {
        return new StringBuilder("Estimado: ")
                .append(person.getFullname())
                .append("\n")
                .append("Portal web: ")
                .append(Global.ROOT_FRONT)
                .append("\n")
                .append("Se le remite sus credenciales de acceso al sistema:")
                .append("\n")
                .append("Usuario: ")
                .append(user.getUsername())
                .append("\n")
                .append("Contraseña: ")
                .append(clearPassword).toString();
    }

    public User getBy(String username) {
        Optional<User> optional = this.findBy(username);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException(String.format("User not found by username: %s", username));
        }
    }

    public Optional<User> find4Create(String username) {
        return userRepository.findByUsername4Create(username);
    }

//    public boolean hasRole(String username, int roleId) {
//        List<Role> roles = roleService.findAllBy(username);
//        return roles.stream().anyMatch(role -> role.getId() == roleId);
//    }

    public boolean hasRole(User user, int roleId) {
        return hasRole(user.getUsername(), roleId);
    }

    public boolean hasRole(Person person, int roleId) {
        User user = this.getBy(person);
        return hasRole(user, roleId);
    }

//    public boolean requestUserHasRole(HttpServletRequest request, int roleId) {
//        String username = jwtTokenUtil.getSubjectOf(request);
//        return hasRole(username, roleId);
//    }

    public Optional<User> findBy(int personId) {
        return userRepository.findByPersonId(personId);
    }

    public User getBy(int personId) {
        Optional<User> optional = this.findBy(personId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new UserNotFoundException(String.format("User not found for personId: %d", personId));
    }

    public User getBy(Person person) {
        return getBy(person.getId());
    }

    public User getBy(HttpServletRequest request) {
        String username = jwtTokenUtil.getSubjectOf(request);
        return this.getBy(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public User updatePassword(User user, String clearPassword) {
        String encriptedPassword = bCryptPasswordEncoder.encode(clearPassword);
        UserAllocator.forUpdate(user, encriptedPassword);
        return save(user);
    }

    public Optional<User> findBy(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean hasRole(String username, int... roleIds) {
        List<Role> userRoles = roleService.findAllBy(username);
        return userRoles.stream()
                .filter(role -> Arrays.stream(roleIds).anyMatch(roleId -> role.getId().equals(roleId)))
                .findFirst()
                .isPresent();
    }

    public boolean requestUserHasRole(HttpServletRequest request, int... roleIds) {
        String username = jwtTokenUtil.getSubjectOf(request);
        return hasRole(username, roleIds);
    }
}
