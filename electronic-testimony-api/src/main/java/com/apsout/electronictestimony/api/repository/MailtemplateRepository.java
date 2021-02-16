package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Mailtemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MailtemplateRepository extends CrudRepository<Mailtemplate, Integer> {

    @Query(value = "SELECT mt " +
            "FROM Mailtemplate mt " +
            "WHERE mt.enterpriseId =:enterpriseId " +
            "AND mt.type = :type " +
            "AND mt.recipientType = :recipientType " +
            "AND mt.active = 1 " +
            "AND mt.deleted = 0 ")
    Optional<Mailtemplate> findBy(@Param("enterpriseId") int enterpriseId, @Param("type") int type, @Param("recipientType") int recipientType);
}
