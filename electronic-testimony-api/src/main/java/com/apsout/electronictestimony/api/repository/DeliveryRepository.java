package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Delivery;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {

    Optional<Delivery> findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(byte sent, byte active, byte deleted);
}
