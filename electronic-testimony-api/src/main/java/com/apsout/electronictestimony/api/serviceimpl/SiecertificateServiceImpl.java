package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.exception.SiecertificateNotFoundException;
import com.apsout.electronictestimony.api.repository.SiecertificateRepository;
import com.apsout.electronictestimony.api.service.SiecertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SiecertificateServiceImpl implements SiecertificateService {
    private static final Logger logger = LoggerFactory.getLogger(SiecertificateServiceImpl.class);

    @Autowired
    private SiecertificateRepository repository;

    @Override
    public Siecertificate save(Siecertificate siecertificate) {
        return repository.save(siecertificate);
    }

    @Override
    @Transactional
    public Integer disableAllBy(Enterprise enterprise) {
        return repository.disableAllBy(enterprise.getId());
    }

    public Optional<Siecertificate> findBy(Siecredential siecredential) {
        return repository.findBySiecredentialBySiecredentialId(siecredential);
    }

    public Siecertificate getBy(Siecredential siecredential) {
        Optional<Siecertificate> optional = findBy(siecredential);
        if (optional.isPresent()) {
            return optional.get();
        }
        logger.error(String.format("Certificate not found for siecredentialId: %d", siecredential.getId()));
        throw new SiecertificateNotFoundException(String.format("Certificado no encontrado para siecredentialId: %d", siecredential.getId()));
    }
}
