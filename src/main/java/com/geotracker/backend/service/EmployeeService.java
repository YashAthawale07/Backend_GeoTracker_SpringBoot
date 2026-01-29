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
        return repo.save(emp); // 🔥 DB CREATED HERE
    }

    public Employee getByEmpId(String empId) {
        return repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }


    // Update employee by empId
    public Optional<Employee> updateEmployeeByEmpId(String empId, String newName) {
        return repo.findByEmpId(empId).map(emp -> {
            emp.setName(newName);
            return repo.save(emp);
        });
    }

    // Delete employee by empId
    public boolean deleteEmployeeByEmpId(String empId) {
        Optional<Employee> empOpt = repo.findByEmpId(empId);
        if (empOpt.isPresent()) {
            repo.delete(empOpt.get());
            return true;
        }
        return false;
    }

//    public boolean deleteEmployeeByEmpId(String empId) {
//        Employee emp = repo.findByEmpId(empId);
//        if (emp != null) {
//            repo.delete(emp);
//            return true;
//        }
//        return false;
//    }
//
//    public Employee updateEmployee(String id, Employee emp) {
//        Optional<Employee> existing = repo.findById(id);
//        if(existing.isPresent()) {
//            Employee e = existing.get();
//            e.setName(emp.getName());
//            e.setEmpId(emp.getEmpId());
//            return repo.save(e);
//        }
//        return null;
//    }

}