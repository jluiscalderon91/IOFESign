package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    List<Role> findAllByActiveAndDeleted(byte active, byte deleted);

    @Query(value = "SELECT r.* " +
            "FROM role r " +
            "WHERE r.id = :roleId " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "LIMIT 1", nativeQuery = true)
    Optional<Role> findById(@Param("roleId") int roleId);

    @Query(value = "SELECT r.* " +
            "FROM role r " +
            "INNER JOIN userrole ur " +
            "ON ur.roleId = r.id " +
            "WHERE ur.userId = :userId " +
            "AND r.active = 1 " +
            "AND ur.active = 1 " +
            "AND r.deleted = 0 " +
            "AND ur.deleted = 0 ", nativeQuery = true)
    List<Role> findAllBy(@Param("userId") int userId);

    @Query(value = "SELECT r.* " +
            "FROM role r " +
            "INNER JOIN userrole ur " +
            "ON ur.roleId = r.id " +
            "INNER JOIN user u " +
            "ON ur.userId = u.id " +
            "WHERE u.username = :username " +
            "AND u.active = 1 " +
            "AND u.deleted = 0 " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND ur.active = 1 " +
            "AND ur.deleted = 0 " +
            "ORDER BY r.id ASC ", nativeQuery = true)
    List<Role> findAllBy(@Param("username") String username);

    @Query(value = "SELECT r.* " +
            "FROM role r " +
            "INNER JOIN userrole ur " +
            "ON ur.roleId = r.id " +
            "INNER JOIN user u " +
            "ON ur.userId = u.id " +
            "INNER JOIN person p " +
            "ON u.personId = p.id " +
            "WHERE p.id = :personId " +
            "AND u.active = 1 " +
            "AND u.deleted = 0 " +
            "AND r.active = 1 " +
            "AND r.deleted = 0 " +
            "AND ur.active = 1 " +
            "AND ur.deleted = 0 " +
            "AND p.active = 1 " +
            "AND p.deleted = 0 " +
            "ORDER BY r.id ASC ", nativeQuery = true)
    List<Role> findAllByPersonId(@Param("personId") int personId);

    @Query("SELECT r " +
            "FROM Role r " +
            "WHERE r.deleted = 0 " +
            "ORDER BY r.orderRole ASC ")
    Page<Role> findAllBy(Pageable pageable);

    @Query(value = "SELECT r.* " +
            "FROM role r " +
            "WHERE r.id = :roleId " +
            "AND r.deleted = 0 " +
            "LIMIT 1", nativeQuery = true)
    Optional<Role> findExistentById(@Param("roleId") int roleId);
}
