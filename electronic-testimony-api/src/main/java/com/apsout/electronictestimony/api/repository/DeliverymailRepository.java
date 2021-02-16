package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Deliverymail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliverymailRepository extends CrudRepository<Deliverymail, Integer> {

    Optional<Deliverymail> findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(boolean sent, boolean active, boolean deleted);
}
