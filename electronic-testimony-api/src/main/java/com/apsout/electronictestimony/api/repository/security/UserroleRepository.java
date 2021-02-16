package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userrole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserroleRepository extends CrudRepository<Userrole, Integer> {

    @Query(value = "SELECT ur " +
            "FROM Userrole ur " +
            "WHERE ur.userByUserId = :user " +
            "AND ur.active = 1 " +
            "AND ur.deleted = 0")
    Optional<Userrole> findByUser(@Param("user") User user);
}
