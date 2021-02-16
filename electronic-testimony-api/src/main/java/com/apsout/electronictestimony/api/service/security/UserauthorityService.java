package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userauthority;

import java.util.Optional;

public interface UserauthorityService {

    Optional<Userauthority> findBy(int userId, int authorityId);

    Optional<Userauthority> findBy(User user, Authority authority);

    Userauthority getBy(int userId, int authorityId);

    Userauthority save(Userauthority userauthority);
}
