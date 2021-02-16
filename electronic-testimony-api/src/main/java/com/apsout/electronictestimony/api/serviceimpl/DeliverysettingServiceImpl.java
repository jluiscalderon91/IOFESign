package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Deliverysetting;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.repository.DeliverysettingRepository;
import com.apsout.electronictestimony.api.service.DeliverysettingService;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliverysettingServiceImpl implements DeliverysettingService {
    private static final Logger logger = LoggerFactory.getLogger(DeliverysettingServiceImpl.class);

    @Autowired
    private DeliverysettingRepository repository;

    @Override
    public Deliverysetting save(Deliverysetting deliverysetting) {
        return repository.save(deliverysetting);
    }

    @Override
    public Optional<Deliverysetting> findByEnterprise(Enterprise enterprise) {
        return repository.findByEnterpriseByEnterpriseIdAndActiveAndDeleted(enterprise, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public Deliverysetting getByEnterprise(Enterprise enterprise) {
        Optional<Deliverysetting> optional = this.findByEnterprise(enterprise);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Deliverysetting not found for enterpriseId: %d", enterprise.getId()));
    }

}
