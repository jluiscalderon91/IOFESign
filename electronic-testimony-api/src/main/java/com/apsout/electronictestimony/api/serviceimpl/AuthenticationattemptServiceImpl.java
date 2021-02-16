package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Authenticationattempt;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.repository.AuthenticationattemptRepository;
import com.apsout.electronictestimony.api.service.AuthenticationattemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationattemptServiceImpl implements AuthenticationattemptService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationattemptServiceImpl.class);

    @Autowired
    private AuthenticationattemptRepository repository;

    @Override
    public Authenticationattempt save(Authenticationattempt authenticationattempt) {
        return repository.save(authenticationattempt);
    }

    @Override
    public List<Authenticationattempt> findLastBy(User user, int numRows) {
        return repository.findLastBy(user.getId(), numRows);
    }
}
