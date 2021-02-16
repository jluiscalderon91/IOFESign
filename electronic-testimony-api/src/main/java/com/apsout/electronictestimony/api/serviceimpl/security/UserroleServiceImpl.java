package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userrole;
import com.apsout.electronictestimony.api.exception.UserNotFoundException;
import com.apsout.electronictestimony.api.repository.security.UserroleRepository;
import com.apsout.electronictestimony.api.service.security.UserroleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserroleServiceImpl implements UserroleService {
    private static final Logger logger = LoggerFactory.getLogger(UserroleServiceImpl.class);

    @Autowired
    UserroleRepository userroleRepository;

    @Override
    public Userrole save(Userrole userrole) {
        return userroleRepository.save(userrole);
    }

    public Optional<Userrole> findBy(User user){
        return userroleRepository.findByUser(user);
    }

    public Userrole getBy(User user){
        Optional<Userrole> optional = userroleRepository.findByUser(user);
        if (optional.isPresent()){
            return optional.get();
        }
        logger.error(String.format("Userrole not found for userId: %d", user.getId()));
        throw new UserNotFoundException(String.format("Userrole not found for userId: %d", user.getId()));
    }
}
