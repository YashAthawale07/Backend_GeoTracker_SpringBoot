package com.geotracker.backend.repositories;


import com.geotracker.backend.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByEmpId(String empId);
}