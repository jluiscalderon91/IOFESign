package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.Roleauthority;
import com.apsout.electronictestimony.api.exception.RoleNotFoundException;
import com.apsout.electronictestimony.api.repository.security.RoleauthorityRepository;
import com.apsout.electronictestimony.api.service.security.RoleauthorityService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleauthorityServiceImpl implements RoleauthorityService {
    private static final Logger logger = LoggerFactory.getLogger(RoleauthorityServiceImpl.class);

    @Autowired
    private RoleauthorityRepository repository;

    @Override
    public Optional<Roleauthority> findBy(int roleId, int authorityId) {
        return repository.findByRoleIdAndAuthorityIdAndDeleted(roleId, authorityId, States.EXISTENT);
    }

    public Optional<Roleauthority> findBy(Role role, Authority authority) {
        return findBy(role.getId(), authority.getId());
    }

    @Override
    public Roleauthority getBy(int roleId, int authorityId) {
        final Optional<Roleauthority> optional = this.findBy(roleId, authorityId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RoleNotFoundException(String.format("Roleauthority not found by authorityId: %d", authorityId));
        }
    }

    @Override
    @Transactional
    public Roleauthority save(Roleauthority roleauthority) {
        return repository.save(roleauthority);
    }
}
