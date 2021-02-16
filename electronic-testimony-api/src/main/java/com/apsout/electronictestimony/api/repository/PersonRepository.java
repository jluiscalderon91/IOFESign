package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "INNER JOIN scope s ON s.personId = p.id " +
            "INNER JOIN user u ON u.personId = p.id " +
            "INNER JOIN userrole ur ON ur.userId = u.id " +
            "INNER JOIN role r ON ur.roleId = r.id " +
            "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
            "WHERE e.partnerId = :partnerId " +
            "AND (p.enterpriseId IN :enterpriseIds) " +
            "AND s.participantType = :participantType " +
            "AND r.id IN :roleIds " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM person p " +
                    "INNER JOIN scope s ON s.personId = p.id " +
                    "INNER JOIN user u ON u.personId = p.id " +
                    "INNER JOIN userrole ur ON ur.userId = u.id " +
                    "INNER JOIN role r ON ur.roleId = r.id " +
                    "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
                    "WHERE e.partnerId = :partnerId " +
                    "AND (p.enterpriseId IN :enterpriseIds) " +
                    "AND s.participantType = :participantType " +
                    "AND r.id IN :roleIds " +
                    "AND p.active = 1 " +
                    "AND p.deleted = 0 ",
            nativeQuery = true)
    Page<Person> findNotInvited(@Param("partnerId") Integer partnerId,
                                @Param("enterpriseIds") List<Integer> enterpriseIds,
                                @Param("roleIds") List<Integer> roleIds,
                                @Param("participantType") int participantType,
                                Pageable pageable);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "INNER JOIN scope s ON s.personId = p.id " +
            "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
            "WHERE e.partnerId = :partnerId " +
            "AND p.enterpriseId IN :enterpriseIds " +
            "AND s.participantType = :participantType " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM person p " +
                    "INNER JOIN scope s ON s.personId = p.id " +
                    "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
                    "WHERE e.partnerId = :partnerId " +
                    "AND p.enterpriseId IN :enterpriseIds " +
                    "AND s.participantType = :participantType " +
                    "AND p.active = 1 " +
                    "AND p.deleted = 0 ",
            nativeQuery = true)
    Page<Person> findAllInvited(@Param("partnerId") Integer partnerId,
                                @Param("enterpriseIds") List<Integer> enterpriseIds,
                                @Param("participantType") int participantType,
                                Pageable pageable);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "INNER JOIN document d " +
            "ON p.id = d.personId " +
            "WHERE d.id=:documentId " +
            "AND p.deleted = 0 " +
            "AND d.deleted = 0 ", nativeQuery = true)
    Optional<Person> findBy(@Param("documentId") int documentId);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "JOIN employee ee " +
            "ON p.id= ee.personId " +
            "JOIN scope s on p.id= s.personId " +
            "WHERE  p.enterpriseId = :enterpriseId " +
            "AND s.participantType = :participantType " +
            "AND p.fullname " +
            "LIKE CONCAT('%',:fullName,'%') " +
            "AND p.type <> 1 " +
            "AND ee.jobId <> 6 " +
            "AND p.active = 1 " +
            "AND ee.active = 1 " +
            "AND s.active= 1 " +
            "AND p.deleted = 0 " +
            "AND ee.deleted = 0 " +
            "AND s.deleted = 0", nativeQuery = true)
    List<Person> findByEnterpriseIdAndParticipantTypeAndFullNameAndDistinctAssistent(@Param("enterpriseId") int enterpriseId,
                                                                                     @Param("participantType") int participantType,
                                                                                     @Param("fullName") String fullName);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "JOIN employee ee " +
            "ON p.id= ee.personId " +
            "WHERE p.fullname " +
            "LIKE CONCAT('%',:fullName,'%') " +
            "AND p.type <> 1 " +
            "AND ee.jobId <> 6 " +
            "AND p.active = 1 " +
            "AND ee.active = 1 " +
            "AND p.deleted = 0 " +
            "AND ee.deleted = 0 ", nativeQuery = true)
    List<Person> findByParticipantTypeAndFullNameAndDistinctAssistent(@Param("fullName") String fullName);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "JOIN employee ee " +
            "ON p.id= ee.personId " +
            "WHERE p.type = :type " +
            "AND p.documentType = :documentType " +
            "AND p.documentNumber = :documentNumber " +
            "AND ee.jobId <> 6 " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ", nativeQuery = true)
    Optional<Person> findByTypeAndDocumentTypeAndDocumentNumberAndDistinctAssistant(@Param("type") int type,
                                                                                    @Param("documentType") int documentType,
                                                                                    @Param("documentNumber") String documentNumber);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "JOIN employee ee " +
            "ON p.id= ee.personId " +
            "JOIN enterprise e " +
            "ON p.enterpriseId= e.id " +
            "WHERE p.type = :type " +
            "AND e.id = :enterpriseId " +
            "AND p.documentType = :documentType " +
            "AND p.documentNumber = :documentNumber " +
            "AND ee.jobId <> 6 " +
            "AND p.active = 1 " +
            "AND p.deleted = 0", nativeQuery = true)
    Optional<Person> findByTypeAndEnterpriseIdAndDocumentTypeAndDocumentNumberAndDistinctAssistant(@Param("type") int type,
                                                                                                   @Param("enterpriseId") int enterpriseId,
                                                                                                   @Param("documentType") int documentType,
                                                                                                   @Param("documentNumber") String documentNumber);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "JOIN employee ee " +
            "ON p.id= ee.personId " +
            "JOIN enterprise e " +
            "ON p.enterpriseIdView = e.id " +
            "JOIN enterprise e2 " +
            "ON p.enterpriseId = e2.id " +
            "JOIN scope s " +
            "ON s.personId = p.id " +
            "WHERE p.type = :type " +
            "AND p.enterpriseId = :enterpriseId " +
            "AND p.enterpriseIdView = :enterpriseIdView " +
            "AND p.documentType = :documentType " +
            "AND p.documentNumber = :documentNumber " +
            "AND s.participantType = :participantType " +
            "AND ee.jobId = :jobId " +
            "AND p.active = 1 " +
            "AND p.deleted = 0", nativeQuery = true)
    Optional<Person> findByTypeAndEnterpriseIdAndDocTypeAndDocNumber(@Param("enterpriseId") int enterpriseId,
                                                                     @Param("enterpriseIdView") int enterpriseIdView,
                                                                     @Param("participantType") int participantType,
                                                                     @Param("type") int type,
                                                                     @Param("documentType") String documentType,
                                                                     @Param("documentNumber") String documentNumber,
                                                                     @Param("jobId") int jobId);

    @Query("SELECT o.personByPersonId " +
            "FROM Operator o " +
            "WHERE o.documentByDocumentId.id = :documentId " +
            "AND o.active = 1 " +
            "AND o.deleted = 0 " +
            "AND o.documentByDocumentId.deleted = 0 " +
            "ORDER BY o.orderOperation ASC")
    List<Person> findAllByDocumentId(@Param("documentId") int documentId);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "INNER JOIN scope s ON s.personId = p.id " +
            "INNER JOIN user u ON u.personId = p.id " +
            "INNER JOIN userrole ur ON ur.userId = u.id " +
            "INNER JOIN role r ON ur.roleId = r.id " +
            "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
            "WHERE e.partnerId = :partnerId " +
            "AND (p.enterpriseId IN :enterpriseIds OR p.enterpriseIdView IN :enterpriseIds) " +
            "AND s.participantType = :participantType " +
            "AND r.id IN :roleIds " +
            "AND (p.fullname like %:term2Search% OR p.documentNumber like %:term2Search%) " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM person p " +
                    "INNER JOIN scope s ON s.personId = p.id " +
                    "INNER JOIN user u ON u.personId = p.id " +
                    "INNER JOIN userrole ur ON ur.userId = u.id " +
                    "INNER JOIN role r ON ur.roleId = r.id " +
                    "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
                    "WHERE e.partnerId = :partnerId " +
                    "AND (p.enterpriseId IN :enterpriseIds OR p.enterpriseIdView IN :enterpriseIds) " +
                    "AND s.participantType = :participantType " +
                    "AND r.id IN :roleIds " +
                    "AND (p.fullname like %:term2Search% OR p.documentNumber like %:term2Search%) " +
                    "AND p.active = 1 " +
                    "AND p.deleted = 0 ",
            nativeQuery = true)
    Page<Person> findNotInvited(@Param("partnerId") Integer partnerId,
                                @Param("enterpriseIds") List<Integer> enterpriseIds,
                                @Param("roleIds") List<Integer> roleIds,
                                @Param("participantType") int participantType,
                                @Param("term2Search") String term2Search,
                                Pageable pageable);

    @Query(value = "SELECT p.* " +
            "FROM person p " +
            "INNER JOIN scope s ON s.personId = p.id " +
            "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
            "WHERE e.partnerId = :partnerId " +
            "AND p.enterpriseId IN :enterpriseIds " +
            "AND s.participantType = :participantType " +
            "AND (p.fullname like %:term2Search% OR p.documentNumber like %:term2Search%) " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 ",
            countQuery = "SELECT COUNT(*) " +
                    "FROM person p " +
                    "INNER JOIN scope s ON s.personId = p.id " +
                    "INNER JOIN enterprise e ON p.enterpriseId = e.id " +
                    "WHERE e.partnerId = :partnerId " +
                    "AND p.enterpriseId IN :enterpriseIds " +
                    "AND s.participantType = :participantType " +
                    "AND (p.fullname like %:term2Search% OR p.documentNumber like %:term2Search%) " +
                    "AND p.active = 1 " +
                    "AND p.deleted = 0 ",
            nativeQuery = true)
    Page<Person> findAllInvited(@Param("partnerId") Integer partnerId,
                                @Param("enterpriseIds") List<Integer> enterpriseIds,
                                @Param("participantType") int participantType,
                                @Param("term2Search") String term2Search,
                                Pageable pageable);

    List<Person> findAllByIdInAndActiveAndDeleted(List<Integer> personIds, byte active, byte deleted);
}
