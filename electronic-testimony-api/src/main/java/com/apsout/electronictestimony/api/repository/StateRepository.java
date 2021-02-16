package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends CrudRepository<State, Integer> {
    Optional<State> findByIdAndActiveAndDeleted(int id, byte active, byte deleted);

    List<State> findAllByActiveAndDeletedOrderByOrderStateAsc(byte active, byte deleted);

    List<State> findAllByIdInAndActiveAndDeleted(List<Integer> stateIds, byte active, byte deleted);
}
