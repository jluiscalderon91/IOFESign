package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Participanttype;
import com.apsout.electronictestimony.api.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipanttypeRepository extends CrudRepository<Participanttype, Integer> {

    List<Participanttype> findAllByActiveAndDeletedOrderByOrderParticipanttypeAsc(byte active, byte deleted);

    @Query("SELECT s.participantType " +
            "FROM Scope s " +
            "WHERE s.personByPersonId = :person " +
            "AND s.active = 1 " +
            "AND s.deleted = 0 " +
            "AND s.personByPersonId.active = 1 " +
            "AND s.personByPersonId.deleted = 0 ")
    Integer findBy(@Param("person") Person person);
}
