package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Profileupdateattempt;
import com.apsout.electronictestimony.api.repository.ProfileupdateattemptRepository;
import com.apsout.electronictestimony.api.service.ProfileupdateattemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileupdateattemptServiceImpl implements ProfileupdateattemptService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileupdateattemptServiceImpl.class);

    @Autowired
    private ProfileupdateattemptRepository repository;

    @Override
    public Profileupdateattempt save(Profileupdateattempt profileupdateattempt) {
        return repository.save(profileupdateattempt);
    }

    @Override
    public List<Profileupdateattempt> findLastTwoBy(int personId) {
        return repository.findLastTwoBy(personId);
    }
}
