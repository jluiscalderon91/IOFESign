package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Fontsize;
import com.apsout.electronictestimony.api.repository.FontsizeRepository;
import com.apsout.electronictestimony.api.service.FontsizeService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FontsizeServiceImpl implements FontsizeService {
    private static final Logger logger = LoggerFactory.getLogger(FontsizeServiceImpl.class);

    @Autowired
    private FontsizeRepository repository;

    @Override
    public List<Fontsize> findInitialAll() {
        logger.info("Getting initial list of fontsizes");
        return repository.findAll();
    }

    @Override
    public List<Fontsize> findAll() {
        return Localstorage.fontsizes.isEmpty() ? findInitialAll() : Localstorage.fontsizes;
    }

    public Optional<Fontsize> findBy(int fontsizeId) {
        return Localstorage.fontsizes
                .stream()
                .filter(fontsize -> fontsize.getId().equals(fontsizeId))
                .findFirst();
    }

    public Fontsize getBy(int fontsizeId) {
        Optional<Fontsize> optional = this.findBy(fontsizeId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Fontsize not found with fontsizeId: %d", fontsizeId));
    }
}
