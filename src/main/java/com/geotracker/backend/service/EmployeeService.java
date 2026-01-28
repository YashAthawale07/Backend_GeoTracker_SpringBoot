package com.geotracker.backend.service;

import com.geotracker.backend.model.Employee;
import com.geotracker.backend.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee create(Employee emp) {
        return repo.save(emp); // 🔥 DB CREATED HERE
    }

    public Employee getByEmpId(String empId) {
        return repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}