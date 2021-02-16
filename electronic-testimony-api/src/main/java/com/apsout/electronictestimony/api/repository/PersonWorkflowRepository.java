package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Personworkflow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonWorkflowRepository extends CrudRepository<Personworkflow, Integer> {

    Optional<Personworkflow> findByPersonIdAndWorkflowIdAndDeleted(int personId, int workflowId, byte deleted);
}
