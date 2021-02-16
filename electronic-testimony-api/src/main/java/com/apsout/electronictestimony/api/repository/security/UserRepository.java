package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsernameAndActiveAndDeleted(String username, byte acive, byte deleted);

    Optional<User> findFirstBySentCredsAndActiveAndDeleted(byte sentCreds, byte active, byte deleted);

    @Query(value = "SELECT u " +
            "FROM User u " +
            "WHERE u.username = :username " +
            "AND u.active = 1 " +
            "AND u.deleted = 0")
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT u.* " +
            "FROM user u " +
            "WHERE u.username " +
            "LIKE CONCAT(:username,'%') " +
            "AND u.active = 1 " +
            "AND u.deleted = 0 " +
            "ORDER BY nextUserNumber DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<User> findByUsername4Create(@Param("username") String username);

    @Query(value = "SELECT u " +
            "FROM User u " +
            "WHERE u.personId = :personId " +
            "AND u.active = 1 " +
            "AND u.deleted = 0")
    Optional<User> findByPersonId(@Param("personId") int personId);
}
