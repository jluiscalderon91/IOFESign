package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.Roleauthority;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleauthorityRepository extends CrudRepository<Roleauthority, Integer> {

    Optional<Roleauthority> findByRoleIdAndAuthorityIdAndDeleted(int roleId, int authorityId, byte deleted);
}
