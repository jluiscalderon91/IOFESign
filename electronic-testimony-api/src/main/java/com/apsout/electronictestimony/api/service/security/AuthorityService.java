package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {

    List<Authority> findInitialAll();

    List<Authority> findAll();

    List<Authority> findAllBy(User user);

    List<Authority> findAllBy(Role role);

    Page<Authority> findAllBy(Pageable pageable);

    Authority onlySave(Authority authority);

    Authority save(Authority authority);

    Authority update(Authority authority);

    Optional<Authority> findBy(int authorityId);

    Authority getBy(int authorityId);

    Authority deleteBy(int authorityId);

    List<Authority> findAllBy(int roleId);

    List<Authority> findAllByPerson(int personId);
}
