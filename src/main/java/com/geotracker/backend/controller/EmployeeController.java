package com.geotracker.backend.controller;

import com.geotracker.backend.model.Employee;
import com.geotracker.backend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:56496")
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        System.out.println("Emp Controller called");
        return service.create(emp);
    }

    @GetMapping("/{empId}")
    public Employee get(@PathVariable String empId) {
        return service.getByEmpId(empId);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
    // ===== Update Employee =====
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam String empId,
            @RequestParam String name) {

        Employee emp = service.updateEmployeeByEmpId(empId, name)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok(emp);
    }
    // ===== Delete Employee =====
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam String empId) {
        boolean deleted = service.deleteEmployeeByEmpId(empId);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Employee> updateEmployee(
//            @PathVariable String id,
//            @RequestBody Employee employee) {
//        Employee updated = service.updateEmployeeByEmpId(id, employee);
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


//    @DeleteMapping("/emp/{empId}")
//    public ResponseEntity<String> deleteEmployeeByEmpId(@PathVariable String empId) {
//        boolean deleted = service.deleteEmployeeByEmpId(empId);
//        if (deleted) {
//            return ResponseEntity.ok("Employee deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Employee not found");
//        }
//    }
}