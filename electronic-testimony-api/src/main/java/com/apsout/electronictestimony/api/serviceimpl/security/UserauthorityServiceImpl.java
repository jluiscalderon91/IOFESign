package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userauthority;
import com.apsout.electronictestimony.api.exception.RoleNotFoundException;
import com.apsout.electronictestimony.api.repository.security.UserauthorityRepository;
import com.apsout.electronictestimony.api.service.security.UserauthorityService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserauthorityServiceImpl implements UserauthorityService {
    private static final Logger logger = LoggerFactory.getLogger(UserauthorityServiceImpl.class);

    @Autowired
    private UserauthorityRepository repository;

    @Override
    public Optional<Userauthority> findBy(int userId, int authorityId) {
        return repository.findByUserIdAndAuthorityIdAndDeleted(userId, authorityId, States.EXISTENT);
    }

    @Override
    public Optional<Userauthority> findBy(User user, Authority authority) {
        return findBy(user.getId(), authority.getId());
    }

    @Override
    public Userauthority getBy(int userId, int authorityId) {
        final Optional<Userauthority> optional = this.findBy(userId, authorityId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RoleNotFoundException(String.format("Userauthority not found by authorityId: %d", authorityId));
        }
    }

    @Override
    @Transactional
    public Userauthority save(Userauthority userauthority) {
        return repository.save(userauthority);
    }
}
