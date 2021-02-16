package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Partner;
import com.apsout.electronictestimony.api.exception.PartnerNotFoundException;
import com.apsout.electronictestimony.api.repository.PartnerRepository;
import com.apsout.electronictestimony.api.service.PartnerService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService {
    private static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl.class);

    @Autowired
    private PartnerRepository repository;

    public List<Partner> findAll() {
        return repository.findAllByActiveAndDeleted(States.ACTIVE, States.EXISTENT);
    }

    public Partner getBy(int partnerId) {
        Optional<Partner> optional = repository.findByIdAndActiveAndDeleted(partnerId, States.ACTIVE, States.EXISTENT);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new PartnerNotFoundException(String.format("Partner not found for partnerId: %d", partnerId));
        }
    }
}
