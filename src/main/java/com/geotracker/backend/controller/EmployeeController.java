package com.geotracker.backend.controller;

import com.geotracker.backend.model.Employee;
import com.geotracker.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
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
}