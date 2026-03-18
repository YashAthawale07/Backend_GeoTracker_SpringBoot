package com.geotracker.backend.controller;

import com.geotracker.backend.model.Employee;
import com.geotracker.backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // POST /employees
    // Body: { "empId": "EMP102", "name": "John", "email": "...", "phone": "...", "department": "...", "role": "EMPLOYEE", "post": "Engineer" }
    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        System.out.println("Emp Controller called");
        return service.create(emp);
    }

    // GET /employees/{empId}
    @GetMapping("/{empId}")
    public Employee get(@PathVariable String empId) {
        return service.getByEmpId(empId);
    }

    // GET /employees/{empId}/profile — dedicated profile page API
    @GetMapping("/{empId}/profile")
    public ResponseEntity<?> getProfile(@PathVariable String empId) {
        try {
            Employee emp = service.getByEmpId(empId);
            return ResponseEntity.ok(emp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

    // GET /employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    // PUT /employees/update?empId=EMP101&name=John&email=...&phone=...&department=...&role=...&post=...
    // All params except empId are optional — only pass what you want to update
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam String empId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String post) {

        Employee emp = service.updateEmployeeByEmpId(empId, name, email, phone, department, role, post)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok(emp);
    }

    // DELETE /employees/delete?empId=EMP101
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam String empId) {
        boolean deleted = service.deleteEmployeeByEmpId(empId);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }
}