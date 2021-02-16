package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Fonttype;
import com.apsout.electronictestimony.api.repository.FonttypeRepository;
import com.apsout.electronictestimony.api.service.FonttypeService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FonttypeServiceImpl implements FonttypeService {
    private static final Logger logger = LoggerFactory.getLogger(FonttypeServiceImpl.class);

    @Autowired
    private FonttypeRepository repository;

    @Override
    public List<Fonttype> findInitialAll() {
        logger.info("Getting initial list of fonttypes");
        return repository.findAll();
    }

    @Override
    public List<Fonttype> findAll() {
        return Localstorage.fonttypes.isEmpty() ? findInitialAll() : Localstorage.fonttypes;
    }

    public Optional<Fonttype> findBy(int fonttypeId) {
        return Localstorage.fonttypes
                .stream()
                .filter(fonttype -> fonttype.getId().equals(fonttypeId))
                .findFirst();
    }

    public Fonttype getBy(int fonttypeId) {
        Optional<Fonttype> optional = this.findBy(fonttypeId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Fonttype not found with fonttypeId: %d", fonttypeId));
    }
}
