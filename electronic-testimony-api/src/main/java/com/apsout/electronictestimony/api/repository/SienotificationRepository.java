package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Sienotification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SienotificationRepository extends CrudRepository<Sienotification, Integer> {

    Optional<Sienotification> findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(byte sent, byte active, byte deleted);
}
