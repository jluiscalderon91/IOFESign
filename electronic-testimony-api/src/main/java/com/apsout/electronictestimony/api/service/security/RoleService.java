package com.apsout.electronictestimony.api.service.security;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    List<Role> findInitialAll();

    List<Role> findAll();

    Role getBy(int roleId);

    List<Role> findAllBy(User user);

    List<Role> findAllBy(String username);

    Role getUpperRoleBy(User user);

    List<Role> findAllBy(Person person);

    Role findBy(String roleId);

    Page<Role> findAllBy(Pageable pageable);

    Role onlySave(Role role);

    Role save(Role role);

    Role update(Role role);

    Role deleteBy(int roleId);

    Role getExistentBy(int roleId);

    Role updateAuthorities(int roleId, _AuthoritiesList authoritiesList);
}
