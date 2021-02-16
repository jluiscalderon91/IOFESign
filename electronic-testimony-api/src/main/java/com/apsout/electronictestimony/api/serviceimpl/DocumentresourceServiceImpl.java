package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Documentresource;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.exception.DocumentresourceNotFoundException;
import com.apsout.electronictestimony.api.repository.DocumentresourceRepository;
import com.apsout.electronictestimony.api.service.DocumentresourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentresourceServiceImpl implements DocumentresourceService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentresourceServiceImpl.class);

    @Autowired
    private DocumentresourceRepository repository;

    @Override
    public Documentresource save(Documentresource documentresource) {
        return repository.save(documentresource);
    }

    @Override
    public Optional<Documentresource> findBy(Resource resource) {
        return repository.findBy(resource);
    }

    @Override
    public Documentresource getBy(Resource resource) {
        final Optional<Documentresource> optional = repository.findBy(resource);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new DocumentresourceNotFoundException(String.format("Resource hash not found by resourceId: %d", resource.getId()));
    }
}
