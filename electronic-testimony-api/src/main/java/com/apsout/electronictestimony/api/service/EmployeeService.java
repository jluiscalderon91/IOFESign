package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);

    Employee getBy(int personId);

    Optional<Employee> findBy(int personId);
}
