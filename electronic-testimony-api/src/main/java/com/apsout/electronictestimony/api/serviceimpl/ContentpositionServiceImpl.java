package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Contentposition;
import com.apsout.electronictestimony.api.repository.ContentpositionRepository;
import com.apsout.electronictestimony.api.service.ContentpositionService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentpositionServiceImpl implements ContentpositionService {
    private static final Logger logger = LoggerFactory.getLogger(ContentpositionServiceImpl.class);

    @Autowired
    private ContentpositionRepository repository;

    public List<Contentposition> findInitialAll(){
        logger.info("Getting initial list of conntentpositions");
        return repository.findAll();
    }

    @Override
    public List<Contentposition> findAll() {
        return Localstorage.contentpositions.isEmpty() ? findInitialAll() : Localstorage.contentpositions;
    }

    public Optional<Contentposition> findBy(int contentpositionId) {
        return Localstorage.contentpositions
                .stream()
                .filter(contentposition -> contentposition.getId().equals(contentpositionId))
                .findFirst();
    }

    public Contentposition getBy(int pagestampId) {
        Optional<Contentposition> optional = this.findBy(pagestampId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Contentposition not found with contentpositionId: %d", pagestampId));
    }
}
