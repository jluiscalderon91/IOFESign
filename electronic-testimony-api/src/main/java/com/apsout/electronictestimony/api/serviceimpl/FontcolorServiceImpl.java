package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Fontcolor;
import com.apsout.electronictestimony.api.repository.FontcolorRepository;
import com.apsout.electronictestimony.api.service.FontcolorService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FontcolorServiceImpl implements FontcolorService {
    private static final Logger logger = LoggerFactory.getLogger(FontcolorServiceImpl.class);

    @Autowired
    private FontcolorRepository repository;

    @Override
    public List<Fontcolor> findInitialAll() {
        logger.info("Getting initial list of fontcolors");
        return repository.findAll();
    }

    @Override
    public List<Fontcolor> findAll() {
        return Localstorage.fontcolors.isEmpty() ? findInitialAll() : Localstorage.fontcolors;
    }

    @Override
    public Optional<Fontcolor> findBy(int fontcolorId) {
        return Localstorage.fontcolors
                .stream()
                .filter(fontcolor -> fontcolor.getId().equals(fontcolorId))
                .findFirst();
    }

    @Override
    public Fontcolor getBy(int fontcolorId) {
        Optional<Fontcolor> optional = this.findBy(fontcolorId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Fontcolor not found with fontcolorId: %d", fontcolorId));
    }
}
