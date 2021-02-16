package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.security.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> find4Send();

    void notificate();

    User getBy(String username);

    Optional<User> find4Create(String username);

    boolean hasRole(User user, int roleId);

    boolean hasRole(Person person, int roleId);

    Optional<User> findBy(int personId);

    User getBy(int personId);

    User getBy(Person person);

    User getBy(HttpServletRequest request);

    User updatePassword(User user, String clearPassword);

    Optional<User> findBy(String username);

    boolean hasRole(String username, int... roleIds);

    boolean requestUserHasRole(HttpServletRequest request, int... roleIds);
}
