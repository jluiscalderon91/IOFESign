package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.State;

import java.util.List;

public interface StateService {

    List<State> findInitialAll();

    List<State> findAll();

    State getBy(int stateId);

    List<State> findAllBy(List<Integer> stateIds);
}
