package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Deliverysetting;
import com.apsout.electronictestimony.api.entity.Enterprise;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeliverysettingRepository extends CrudRepository<Deliverysetting, Integer> {

    Optional<Deliverysetting> findByEnterpriseByEnterpriseIdAndActiveAndDeleted(Enterprise enterprise, byte active, byte deleted);
}
