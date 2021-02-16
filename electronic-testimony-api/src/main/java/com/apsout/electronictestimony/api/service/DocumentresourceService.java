package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Documentresource;
import com.apsout.electronictestimony.api.entity.Resource;

import java.util.Optional;

public interface DocumentresourceService {

    Documentresource save(Documentresource documentresource);

    Optional<Documentresource> findBy(Resource resource);

    Documentresource getBy(Resource resource);
}
