package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Employee;
import com.apsout.electronictestimony.api.exception.EmployeeNotFoundException;
import com.apsout.electronictestimony.api.repository.EmployeeRepository;
import com.apsout.electronictestimony.api.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getBy(int personId) {
        Optional<Employee> optional = findBy(personId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EmployeeNotFoundException(String.format("Employe not found by personId: %d", personId));
    }

    @Override
    public Optional<Employee> findBy(int personId) {
        return employeeRepository.findByPersonId(personId);
    }
}
