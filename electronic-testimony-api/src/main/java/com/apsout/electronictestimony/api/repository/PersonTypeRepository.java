package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Persontype;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonTypeRepository extends CrudRepository<Persontype, Integer> {

    List<Persontype> findAllByActiveAndDeleted(byte active, byte deleted);
}
