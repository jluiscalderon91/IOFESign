package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {

    @Query(value = "SELECT s " +
            "FROM Service s " +
            "WHERE s.active = 1 " +
            "AND s.deleted = 0 ")
    List<Service> findAll();
}
