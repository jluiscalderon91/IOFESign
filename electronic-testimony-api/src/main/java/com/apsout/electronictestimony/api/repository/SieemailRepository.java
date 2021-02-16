package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Participant;
import com.apsout.electronictestimony.api.entity.Sieemail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SieemailRepository extends CrudRepository<Sieemail, Integer> {

    @Query("SELECT s " +
            "FROM Sieemail s " +
            "WHERE s.participantByParticipantId = :participant " +
            "AND s.active = 1 " +
            "AND s.deleted = 0 ")
    Optional<Sieemail> findBy(Participant participant);

    @Query("SELECT s " +
            "FROM Sieemail s " +
            "WHERE s.id = :sieemailId " +
            "AND s.deleted = 0 ")
    Optional<Sieemail> findBy(int sieemailId);

    @Query("SELECT s " +
            "FROM Sieemail s " +
            "WHERE s.participantId IN :participantIds " +
            "AND s.active = 1 " +
            "AND s.deleted = 0 ")
    List<Sieemail> findParticipants(List<Integer> participantIds);
}
