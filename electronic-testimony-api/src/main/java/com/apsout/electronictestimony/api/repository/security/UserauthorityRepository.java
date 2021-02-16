package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.Userauthority;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserauthorityRepository extends CrudRepository<Userauthority, Integer> {

    Optional<Userauthority> findByUserIdAndAuthorityIdAndDeleted(int userId, int authorityId, byte deleted);
}
