package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OperatorService {

    Operator save(Operator operator);

    Operator update(Operator operator);

    Page<Operator> getAllBy(int enterpriseId, Pageable pageable);

    Optional<Integer> getMaxOrderOperation(int enterpriseId);

    Optional<Operator> findByPerson(int personId);

    Optional<Operator> findNextBy(Enterprise enterprise, Document document);

    Optional<Operator> getFirstBy(Enterprise enterprise, Document document);

    Optional<Operator> getFirstBy(Enterprise enterprise, Document document, int orderOperation);

    Operator getBy(Document document, int orderOperation);

    Operator getBy(int operatorId);

    Operator delete(Operator operator);

    Integer getCountBy(int enterpriseId);

    Operator onlySave(Operator operator);

    Operator getBy(int personId, int documentId);

    Optional<Operator> getOptionalBy(int personId, int documentId);

    Operator getNextConcreteOperatorBy(Enterprise enterprise, Document document);

    List<Operator> getAllBy(Document document);

    Optional<Operator> findNextBy(Document document);

    Operator getNextConcreteOperatorBy(Document document);
}
