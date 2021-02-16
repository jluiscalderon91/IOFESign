package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SiecertificateRepository extends CrudRepository<Siecertificate, Integer> {

    @Modifying
    @Query(value = "UPDATE siecertificate sc " +
            "INNER JOIN siecredential sc1 ON sc.siecredentialId = sc1.id " +
            "SET sc.active = 0 " +
            "WHERE sc1.enterpriseId =:enterpriseId " +
            "AND sc.active = 1 " +
            "AND sc.deleted = 0 ", nativeQuery = true)
    int disableAllBy(@Param("enterpriseId") int enterpriseId);

    Optional<Siecertificate> findBySiecredentialBySiecredentialId(Siecredential siecredential);
}
