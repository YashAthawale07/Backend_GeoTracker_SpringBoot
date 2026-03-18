package com.geotracker.backend.service;

import com.geotracker.backend.model.Employee;
import com.geotracker.backend.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee create(Employee emp) {
        return repo.save(emp);
    }

    public Employee getByEmpId(String empId) {
        return repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // Update — only updates fields that are passed (non-null)
    public Optional<Employee> updateEmployeeByEmpId(String empId, String name, String email,
                                                    String phone, String department,
                                                    String role, String post) {
        return repo.findByEmpId(empId).map(emp -> {
            if (name != null)       emp.setName(name);
            if (email != null)      emp.setEmail(email);
            if (phone != null)      emp.setPhone(phone);
            if (department != null) emp.setDepartment(department);
            if (role != null)       emp.setRole(role);
            if (post != null)       emp.setPost(post);
            return repo.save(emp);
        });
    }

    public boolean deleteEmployeeByEmpId(String empId) {
        Optional<Employee> empOpt = repo.findByEmpId(empId);
        if (empOpt.isPresent()) {
            repo.delete(empOpt.get());
            return true;
        }
        return false;
    }
}