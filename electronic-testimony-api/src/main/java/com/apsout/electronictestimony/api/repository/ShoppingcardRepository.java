package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Shoppingcard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingcardRepository extends CrudRepository<Shoppingcard, Integer> {

    @Query("SELECT sc " +
            "FROM Shoppingcard sc " +
            "WHERE sc.active = 1 " +
            "AND sc.deleted = 0 ")
    List<Shoppingcard> findAll();

    @Query("SELECT sc " +
            "FROM Shoppingcard sc " +
            "WHERE sc.id = :shoppingcardId " +
            "AND sc.active = 1 " +
            "AND sc.deleted = 0 ")
    Optional<Shoppingcard> findBy(@Param("shoppingcardId") int shoppingcardId);

    @Query("SELECT sc " +
            "FROM Shoppingcard sc " +
            "WHERE sc.partnerId = :partnerId " +
            "AND sc.active = 1 " +
            "AND sc.deleted = 0 " +
            "ORDER BY sc.orderCard ASC ")
    List<Shoppingcard> findAllByPartnerId(@Param("partnerId") int partnerId);
}
