package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Pagestamp;
import com.apsout.electronictestimony.api.repository.PagestampRepository;
import com.apsout.electronictestimony.api.service.PagestampService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagestampServiceImpl implements PagestampService {
    private static final Logger logger = LoggerFactory.getLogger(PagestampServiceImpl.class);

    @Autowired
    private PagestampRepository repository;

    @Override
    public List<Pagestamp> findInitialAll() {
        logger.info("Getting initial list of pagestamps");
        return repository.findAll();
    }

    @Override
    public List<Pagestamp> findAll() {
        return Localstorage.pagesstamps.isEmpty() ? findInitialAll() : Localstorage.pagesstamps;
    }

    public Optional<Pagestamp> findBy(int pagestampId) {
        return Localstorage.pagesstamps
                .stream()
                .filter(pagestamp -> pagestamp.getId().equals(pagestampId))
                .findFirst();
    }

    public Pagestamp getBy(int pagestampId) {
        Optional<Pagestamp> optional = this.findBy(pagestampId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Pagestamp not found with pagestampId: %d", pagestampId));
    }
}
