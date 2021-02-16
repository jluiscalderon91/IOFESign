package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @Query(value = "SELECT e " +
            "FROM Employee e " +
            "WHERE e.personId = :personId " +
            "AND e.deleted = 0")
    Optional<Employee> findByPersonId(@Param("personId") int personId);
}
