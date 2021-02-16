package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.Module;
import com.apsout.electronictestimony.api.entity.security.*;
import com.apsout.electronictestimony.api.exception.AuthorityNotFoundException;
import com.apsout.electronictestimony.api.repository.security.AuthorityRepository;
import com.apsout.electronictestimony.api.service.ModuleService;
import com.apsout.electronictestimony.api.service.security.*;
import com.apsout.electronictestimony.api.util.allocator.AuthorityAllocator;
import com.apsout.electronictestimony.api.util.allocator.RoleauthorityAllocator;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.Roles;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    @Autowired
    private AuthorityRepository repository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RoleauthorityService roleauthorityService;

    @Override
    public List<Authority> findInitialAll() {
        return repository.findAll();
    }

    @Override
    public List<Authority> findAll() {
        return Localstorage.authorities.isEmpty() ? findInitialAll() : Localstorage.authorities;
    }

    @Override
    public List<Authority> findAllBy(User user) {
        List<Role> roles = roleService.findAllBy(user);
        List<Authority> authoritiesByRoles = roles.stream()
                .flatMap(role -> this.findAllBy(role).stream())
                .collect(Collectors.toList());
        List<Authority> authoritiesByUser = repository.findAllBy(user.getId());
        return Stream.concat(authoritiesByRoles.stream(), authoritiesByUser.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Authority> findAllBy(Role role) {
        return findAllBy(role.getId());
    }

    @Override
    public Page<Authority> findAllBy(Pageable pageable) {
        return repository.findAllByDeleted(States.EXISTENT, pageable);
    }

    @Override
    public Authority save(Authority authority) {
        AuthorityAllocator.build(authority);
        final Authority authoritySaved = this.onlySave(authority);
        this.associateAuthorityTo(authority, Roles.SUPERADMIN);
        return authoritySaved;
    }

    private void associateAuthorityTo(Authority authority, int roleId) {
        Role role = roleService.getBy(roleId);
        Roleauthority roleauthority = RoleauthorityAllocator.build(role, authority);
        roleauthorityService.save(roleauthority);
    }

    @Override
    public Authority onlySave(Authority authority) {
        return repository.save(authority);
    }

    @Override
    public Authority update(Authority oldAuthority) {
        Authority newAuthority = this.getBy(oldAuthority.getId());
        Module module = moduleService.getBy(oldAuthority.getModule());
        AuthorityAllocator.forUpdate(oldAuthority, newAuthority);
        final Authority authorityUpdated = onlySave(newAuthority);
        logger.info(String.format("Authority updated with authorityId: %d", newAuthority.getId()));
        return authorityUpdated;
    }

    public Optional<Authority> findBy(int authorityId) {
        return repository.findById(authorityId);
    }

    public Authority getBy(int authorityId) {
        Optional<Authority> optional = findBy(authorityId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new AuthorityNotFoundException(String.format("Authority not found for authorityId: %d", authorityId));
    }

    public Authority deleteBy(int authorityId) {
        Authority authority = getBy(authorityId);
        AuthorityAllocator.forDelete(authority);
        final Authority authorityDeleted = onlySave(authority);
        logger.info(String.format("Authority deleted with authorityId: %d", authorityId));
        return authorityDeleted;
    }

    @Override
    public List<Authority> findAllBy(int roleId) {
        return repository.findAllByRole(roleId);
    }

    @Override
    public List<Authority> findAllByPerson(int personId) {
        User user = userService.getBy(personId);
        return repository.findAllBy(user.getId());
    }
}
