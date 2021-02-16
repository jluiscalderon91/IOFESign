package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Relatedperson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RelatedpersonRepository extends CrudRepository<Relatedperson, Integer> {

    @Query(value = "SELECT rp " +
            "FROM Relatedperson rp " +
            "WHERE rp.personId =:personId " +
            "AND rp.active = 1 " +
            "AND rp.deleted = 0")
    Optional<Relatedperson> findByPersonId(int personId);

    @Query(value = "SELECT rp " +
            "FROM Relatedperson rp " +
            "WHERE rp.personIdRelated =:personIdRelated " +
            "AND rp.active = 1 " +
            "AND rp.deleted = 0")
    Optional<Relatedperson> findByPersonIdRelated(int personIdRelated);
}
