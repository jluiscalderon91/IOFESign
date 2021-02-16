package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.repository.ServiceRepository;
import com.apsout.electronictestimony.api.service.ServiceService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import com.apsout.electronictestimony.api.util.statics.ServiceWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private ServiceRepository repository;

    @Override
    public int trasnlateService(com.apsout.electronictestimony.api.util.enums.Service service) {
        switch (service) {
            case DIGITAL_SIGNATURE:
                return ServiceWeight.DIGITAL_SIGNATURE;
            case TIMESTAMPING:
                return ServiceWeight.TIMESTAMPING;
            case SIE_NOTIFICATION:
                return ServiceWeight.SIE_NOTIFICATION;
            case REVIEW:
                return ServiceWeight.REVIEW;
            default:
                return ServiceWeight.GRAPHIC_SIGNATURE;
        }
    }

    @Override
    public List<com.apsout.electronictestimony.api.entity.Service> findInitialAll() {
        logger.info("Getting initial list of services");
        return repository.findAll();
    }

    @Override
    public List<com.apsout.electronictestimony.api.entity.Service> findAll() {
        return Localstorage.services.isEmpty() ? findInitialAll() : Localstorage.services;
    }
}
