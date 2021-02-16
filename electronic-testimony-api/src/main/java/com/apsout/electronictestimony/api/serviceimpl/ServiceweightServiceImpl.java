package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Serviceweight;
import com.apsout.electronictestimony.api.exception.InexistentServiceweightConfigurationException;
import com.apsout.electronictestimony.api.repository.ServiceweightRepository;
import com.apsout.electronictestimony.api.service.ServiceService;
import com.apsout.electronictestimony.api.service.ServiceweightService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceweightServiceImpl implements ServiceweightService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceweightServiceImpl.class);

    @Autowired
    private ServiceweightRepository repository;
    @Autowired
    private ServiceService serviceService;

    @Override
    public List<Serviceweight> findInitialAll() {
        logger.info("Getting initial list of serviceweights");
        return repository.findAll();
    }

    @Override
    public List<Serviceweight> findAll() {
        return Localstorage.serviceweights.isEmpty() ? findInitialAll() : Localstorage.serviceweights;
    }

    @Override
    public Optional<Serviceweight> findBy(int serviceweightId) {
        return Localstorage.serviceweights
                .stream()
                .filter(serviceweight -> serviceweight.getId().equals(serviceweightId))
                .findFirst();
    }

    @Override
    public Serviceweight getBy(int serviceweightId) {
        Optional<Serviceweight> optional = this.findBy(serviceweightId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new InexistentServiceweightConfigurationException(String.format("Service weight configuration wasn't configured for serviceweightId: %d", serviceweightId));
    }

    @Override
    public Serviceweight getBy(com.apsout.electronictestimony.api.util.enums.Service service) {
        int serviceId = serviceService.trasnlateService(service);
        return this.getBy(serviceId);
    }
}
