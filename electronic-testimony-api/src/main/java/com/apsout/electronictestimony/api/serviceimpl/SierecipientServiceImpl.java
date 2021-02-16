package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Sierecipient;
import com.apsout.electronictestimony.api.repository.SierecipientRepository;
import com.apsout.electronictestimony.api.service.SierecipientService;
import com.apsout.electronictestimony.api.util.allocator.SierecipientAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SierecipientServiceImpl implements SierecipientService {
    private static final Logger logger = LoggerFactory.getLogger(SierecipientServiceImpl.class);

    @Autowired
    private SierecipientRepository repository;

    @Override
    @Transactional
    public Sierecipient save(Sierecipient sierecipient) {
        return repository.save(sierecipient);
    }

    @Override
    @Transactional
    public List<Sierecipient> save(List<Sierecipient> sierecipients) {
        return sierecipients.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<Sierecipient> findBy(int sieemailId, String address) {
        return repository.findBy(sieemailId, address);
    }

    @Override
    @Transactional
    public List<Sierecipient> update(List<Sierecipient> sierecipients) {
        return sierecipients.stream().map(sierecipient -> {
            final int sieemailId = sierecipient.getSieemailId();
            final String address = sierecipient.getAddress();
            Optional<Sierecipient> optional = findBy(sieemailId, address);
            if (optional.isPresent()) {
                Sierecipient sierecipient1 = optional.get();
                sierecipient1.setActive(States.ACTIVE);
                return save(sierecipient1);
            } else {
                final Sierecipient build = SierecipientAllocator.build(sieemailId, address);
                return save(build);
            }
        }).collect(Collectors.toList());
    }

    public List<Sierecipient> save(Sieemail sieemail) {
        final List<Sierecipient> oldSierecipients = sieemail.getSierecipientsById().stream().collect(Collectors.toList());
        return save(oldSierecipients);
    }

    public List<Sierecipient> getAllBy(Sieemail sieemail) {
        return repository.findAllBy(sieemail.getId());
    }
}
