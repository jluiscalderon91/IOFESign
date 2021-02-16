package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Partner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Integer> {

    List<Partner> findAllByActiveAndDeleted(byte active, byte deleted);

    Optional<Partner> findByIdAndActiveAndDeleted(int partnerId, byte active, byte deleted);
}
