package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Integer> {

    @Query(value = "SELECT a.* " +
            "FROM authority a " +
            "INNER JOIN userauthority ua " +
            "ON ua.authorityId = a.id " +
            "WHERE ua.userId = :userId " +
            "AND a.active = 1 " +
            "AND ua.active = 1 " +
            "AND a.deleted = 0 " +
            "AND ua.deleted = 0 " +
            "ORDER BY a.module ASC ", nativeQuery = true)
    List<Authority> findAllBy(@Param("userId") int userId);

    @Query(value = "SELECT a " +
            "FROM Authority a " +
            "WHERE a.active = 1 " +
            "AND a.deleted = 0 " +
            "ORDER BY a.module ASC, a.createAt ASC ")
    List<Authority> findAll();

    @Query(value = "SELECT ra.authorityByAuthorityId " +
            "FROM Roleauthority ra " +
            "WHERE ra.roleId = :roleId " +
            "AND ra.active = 1 " +
            "AND ra.deleted = 0 " +
            "ORDER BY ra.authorityByAuthorityId.module ASC ")
    List<Authority> findAllByRole(@Param("roleId") int roleId);

    @Query("SELECT a " +
            "FROM Authority a " +
            "WHERE a.deleted =:deleted " +
            "ORDER BY a.module ASC, a.createAt ASC ")
    Page<Authority> findAllByDeleted(byte deleted, Pageable pageable);

    @Query(value = "SELECT a " +
            "FROM Authority a " +
            "WHERE a.id =:authorityId " +
            "AND a.deleted = 0")
    Optional<Authority> findById(int authorityId);
}
