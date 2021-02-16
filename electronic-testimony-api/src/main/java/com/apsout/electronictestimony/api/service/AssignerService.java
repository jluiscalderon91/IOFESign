package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.Document;

import java.util.List;
import java.util.Optional;

public interface AssignerService {
    Assigner save(Assigner assigner);

    Optional<Assigner> findFirstByAndOrderOperationDesc(int enterpriseId, int documentId);

    Assigner getBy(int personId, int documentId);

    Optional<Assigner> findBy(int personId, int documentId);

    Optional<Assigner> findLastCompletedBy(int documentId);

    byte checkStatusBatchBy(int personId, String uuid);

    Assigner findBy(int personId, String hashIdentifier);

    Optional<Assigner> findFirstBy(int documentId);

    List<Assigner> getAllBy(Document document);

    void invalidate(Assigner assigner);

    Optional<Assigner> findBy(int operatorId);
}
