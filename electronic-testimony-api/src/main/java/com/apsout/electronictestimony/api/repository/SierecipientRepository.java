package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Sierecipient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SierecipientRepository extends CrudRepository<Sierecipient, Integer> {

    @Query(value = "SELECT s " +
            "FROM Sierecipient s " +
            "WHERE s.sieemailId = :sieemailId " +
            "AND s.address = :address " +
            "AND s.deleted = 0 ")
    Optional<Sierecipient> findBy(int sieemailId, String address);

    @Query(value = "SELECT s " +
            "FROM Sierecipient s " +
            "WHERE s.sieemailId = :sieemailId " +
            "AND s.active = 1 " +
            "AND s.deleted = 0 ")
    List<Sierecipient> findAllBy(int sieemailId);
}
