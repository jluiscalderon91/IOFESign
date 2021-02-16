package com.apsout.electronictestimony.api.util.allocator.security;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.util.statics.States;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserAllocator {

    public static User build(Person person, String username, Optional<User> optionalUser) {
        User user = new User();
        user.setPersonId(person.getId());
        user.setPersonByPersonId(person);
        if (optionalUser.isPresent()) {
            User userDb = optionalUser.get();
            user.setUsername(username + userDb.getNextUserNumber());
            user.setNextUserNumber(userDb.getNextUserNumber() + 1);
        } else {
            user.setUsername(username);
            user.setNextUserNumber(1);
        }
        user.setSentCreds(States.NOT_SENT);
        ofPostMethod(user);
        return user;
    }

    public static void forUpdate(User user, String encryptedPassword) {
        user.setPassword(encryptedPassword);
        user.setSentCreds(States.SENT);
        user.setSentCredsAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void ofPostMethod(User user) {
        user.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setActive(States.ACTIVE);
        user.setDeleted(States.EXISTENT);
    }

    public static Collection<GrantedAuthority> toGrantedRoles(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public static Collection<GrantedAuthority> toGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getCode())).collect(Collectors.toList());
    }

    public static String buildUserName(Person person) {
        String firstname = person.getFirstname();
        String lastname = person.getLastname();
        String part1 = String.valueOf(firstname.charAt(0));
        String part2 = lastname.split(Pattern.quote(" "))[0];
        return (part1 + part2).toLowerCase();
    }

    public static void forResetPassword(User user) {
        user.setSentCreds(States.NOT_SENT);
        user.setSentCredsAt(null);
        user.setPassword(null);
    }

}
