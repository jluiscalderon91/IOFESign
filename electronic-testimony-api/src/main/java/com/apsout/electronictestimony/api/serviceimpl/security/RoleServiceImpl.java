package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.pojo._AuthoritiesList;
import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.Roleauthority;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.exception.RoleNotFoundException;
import com.apsout.electronictestimony.api.repository.security.RoleRepository;
import com.apsout.electronictestimony.api.service.security.AuthorityService;
import com.apsout.electronictestimony.api.service.security.RoleService;
import com.apsout.electronictestimony.api.service.security.RoleauthorityService;
import com.apsout.electronictestimony.api.util.allocator.RoleAllocator;
import com.apsout.electronictestimony.api.util.allocator.RoleauthorityAllocator;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository repository;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RoleauthorityService roleauthorityService;

    @Override
    public List<Role> findInitialAll() {
        return repository.findAllByActiveAndDeleted(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<Role> findAll() {
        return Localstorage.roles.isEmpty() ? findInitialAll() : Localstorage.roles;
    }

    @Override
    public Role getBy(int roleId) {
        final Optional<Role> optional = repository.findById(roleId);
        if (optional.isPresent()) {
            logger.info(String.format("Role founded by roleId: %d", roleId));
            return optional.get();
        } else {
            throw new RoleNotFoundException(String.format("Role not found by roleId: %d", roleId));
        }
    }

    @Override
    public List<Role> findAllBy(User user) {
        return repository.findAllBy(user.getId());
    }

    public List<Role> findAllBy(String username) {
        return repository.findAllBy(username);
    }

    public Role getUpperRoleBy(User user) {
        return this.findAllBy(user.getUsername()).get(0);
    }

    public List<Role> findAllBy(Person person) {
        return repository.findAllByPersonId(person.getId());
    }

    public Role findBy(String roleId) {
        int roleIdInt = Integer.parseInt(roleId);
        return getBy(roleIdInt);
    }

    public Page<Role> findAllBy(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    public Role save(Role role) {
        RoleAllocator.build(role);
        return this.onlySave(role);
    }

    public Role onlySave(Role role) {
        return repository.save(role);
    }

    public Role update(Role oldRole) {
        Role newRole = this.getExistentBy(oldRole.getId());
        if (States.NOT_EDITABLE == newRole.getEditable()) {
            throw new RuntimeException("Role can't edit because that is restricted");
        }
        RoleAllocator.forUpdate(oldRole, newRole);
        final Role roleUpdated = onlySave(newRole);
        logger.info(String.format("Role updated with roleId: %d", newRole.getId()));
        return roleUpdated;
    }

    public Role deleteBy(int roleId) {
        Role role = this.getExistentBy(roleId);
        if (States.NOT_EDITABLE == role.getEditable()) {
            throw new RuntimeException("Role can't delete because that is restricted");
        }
        RoleAllocator.forDelete(role);
        final Role roleDeleted = onlySave(role);
        logger.info(String.format("Role deleted with roleId: %d", roleId));
        return roleDeleted;
    }

    @Override
    public Role getExistentBy(int roleId) {
        final Optional<Role> optional = repository.findExistentById(roleId);
        if (optional.isPresent()) {
            logger.info(String.format("Existent role founded by roleId: %d", roleId));
            return optional.get();
        } else {
            throw new RoleNotFoundException(String.format("Existent role not found by roleId: %d", roleId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateAuthorities(int roleId, _AuthoritiesList authoritiesList) {
        Role role = this.getBy(roleId);
        List<Authority> authorities = authoritiesList.getAuthorities();
        authorities.stream().forEach(authority -> {
            Authority authoritydb = authorityService.getBy(authority.getId());
            Optional<Roleauthority> optional = roleauthorityService.findBy(role, authority);
            if (optional.isPresent()) {
                if (States.INACTIVE == authority.getActive()) {
                    Roleauthority roleauthority = optional.get();
                    RoleauthorityAllocator.forDelete(roleauthority);
                    roleauthorityService.save(roleauthority);
                }
            } else {
                if (States.ACTIVE == authority.getActive()) {
                    Roleauthority roleauthority = RoleauthorityAllocator.build(role, authority);
                    roleauthorityService.save(roleauthority);
                }
            }
        });
        return role;
    }
}
