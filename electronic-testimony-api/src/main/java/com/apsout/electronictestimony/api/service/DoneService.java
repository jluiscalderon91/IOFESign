package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Done;
import com.apsout.electronictestimony.api.entity.Resource;

import java.util.List;
import java.util.Optional;

public interface DoneService {
    Done save(Done done);

    Optional<Done> getBy(Resource resource);

    Optional<Done> getLastBy(int documentId);

    Optional<Done> getLastBy(int documentId, String hashResource);

    Optional<Done> getLastBy(int documentId, int orderResource);

    List<Done> findByFinishedDocument(int documentId, List<Integer> operationIds);

    List<Done> findBy(int documentId, int operationId);

    List<Done> findAllBy(int documentId);

    Optional<Done> findUniqueBy(Assigner assigner);
}
