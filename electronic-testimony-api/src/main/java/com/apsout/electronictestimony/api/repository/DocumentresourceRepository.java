package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Documentresource;
import com.apsout.electronictestimony.api.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DocumentresourceRepository extends CrudRepository<Documentresource, Integer> {

    @Query(value = "SELECT d " +
            "FROM Documentresource d " +
            "WHERE d.resourceByResourceId = :resource " +
            "AND d.active = 1 " +
            "AND d.deleted = 0")
    Optional<Documentresource> findBy(@Param("resource") Resource resource);
}
