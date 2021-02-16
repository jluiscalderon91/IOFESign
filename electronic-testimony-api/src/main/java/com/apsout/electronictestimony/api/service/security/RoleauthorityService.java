package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.Roleauthority;

import java.util.Optional;

public interface RoleauthorityService {

    Optional<Roleauthority> findBy(int roleId, int authorityId);

    Optional<Roleauthority> findBy(Role role, Authority authority);

    Roleauthority getBy(int roleId, int authorityId);

    Roleauthority save(Roleauthority roleauthority);
}
