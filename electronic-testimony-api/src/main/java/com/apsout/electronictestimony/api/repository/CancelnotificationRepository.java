package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CancelnotificationRepository extends CrudRepository<Cancelnotification, Integer> {

//    @Query("SELECT p " +
//            "FROM Passwordretriever p " +
//            "WHERE p.uuid = :uuid " +
//            "AND p.active = 1 " +
//            "AND p.deleted = 0 ")
//    Optional<Passwordretriever> findBy(@Param("uuid") String uuid);

//    @Query("SELECT p " +
//            "FROM Passwordretriever p " +
//            "WHERE p.username = :username " +
//            "AND p.sent = 0 " +
//            "AND p.active = 1 " +
//            "AND p.deleted = 0 ")
//    List<Passwordretriever> findAllBy(@Param("username") String username);

//    @Modifying
//    @Query(value = "UPDATE Passwordretriever p " +
//            "SET p.active = 0 " +
//            "WHERE p.id IN :passwordretrieverIds")
//    int disableAllBy(@Param("passwordretrieverIds") List<Integer> passwordretrieverIds);

    Optional<Cancelnotification> findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(byte sent, byte active, byte deleted);

//    @Query("SELECT p " +
//            "FROM Passwordretriever p " +
//            "WHERE p.uuid = :uuid " +
//            "AND p.hashFirstStep = :hashFirstStep " +
//            "AND p.verificationCode = :verificationCode " +
//            "AND p.sent = 1 " +
//            "AND p.active = 1 " +
//            "AND p.deleted = 0 ")
//    Optional<Passwordretriever> findBy(@Param("uuid") String uuid,
//                                       @Param("hashFirstStep") String hashFirstStep,
//                                       @Param("verificationCode") String verificationCode);

//    @Query("SELECT p " +
//            "FROM Passwordretriever p " +
//            "WHERE p.hashFirstStep = :hash1 " +
//            "AND p.active = 1 " +
//            "AND p.deleted = 0 ")
//    Optional<Passwordretriever> findByHash1(@Param("hash1") String hash1);

//    @Query("SELECT p " +
//            "FROM Passwordretriever p " +
//            "WHERE p.hashFirstStep = :hash1 " +
//            "AND p.hashSecondStep = :hash2 " +
//            "AND p.sent = 1 " +
//            "AND p.active = 1 " +
//            "AND p.deleted = 0 ")
//    Optional<Passwordretriever> findBy(@Param("hash1") String hash1,
//                                       @Param("hash2") String hash2);
}
