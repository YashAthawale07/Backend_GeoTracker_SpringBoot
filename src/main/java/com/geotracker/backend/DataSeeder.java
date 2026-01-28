package com.geotracker.backend;


import com.geotracker.backend.model.Employee;
import com.geotracker.backend.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
//@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EmployeeRepository repo;

    public DataSeeder(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            Employee e = new Employee();
            e.setEmpId("EMP101");
            e.setName("Test Employee");
//            e.setRole("EMPLOYEE");
            repo.save(e);
        }
    }
}