package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Employee;
import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EmployeeAllocator {
    public static void ofPostMethod(Employee employee) {
        employee.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        employee.setActive(States.ACTIVE);
        employee.setDeleted(States.EXISTENT);
    }

    public static Employee build(Person person) {
        Employee employee = new Employee();
        employee.setJobId(person.getJobId());
        employee.setPersonId(person.getId());
        ofPostMethod(employee);
        return employee;
    }

    public static Employee build(Person person, Job job) {
        Employee employee = new Employee();
        employee.setPersonId(person.getId());
        employee.setJobId(job.getId());
        ofPostMethod(employee);
        return employee;
    }

    public static void forUpdate(Employee employee, Person person) {
        employee.setJobId(person.getJobId());
    }
}
