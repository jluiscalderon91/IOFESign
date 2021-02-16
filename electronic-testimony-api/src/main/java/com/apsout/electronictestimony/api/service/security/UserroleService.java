package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userrole;

import java.util.Optional;

public interface UserroleService {
    Userrole save(Userrole userrole);

    Optional<Userrole> findBy(User user);

    Userrole getBy(User user);
}
