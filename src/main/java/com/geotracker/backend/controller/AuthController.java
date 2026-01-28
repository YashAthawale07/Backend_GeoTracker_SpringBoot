package com.geotracker.backend.controller;

//import com.geotracker.backend.config.JWTConfig;
import com.geotracker.backend.model.Employee;
import com.geotracker.backend.repositories.EmployeeRepository;
import com.geotracker.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {

//    private final EmployeeRepository repo;
//
//    public AuthController(EmployeeRepository repo, JwtUtil jwtUtil) {
//        this.repo = repo;
//        this.jwtUtil = jwtUtil;
//    }
@GetMapping("/yash")
    public void yash(){
    System.out.println("Yash called");
}
//    private final JwtUtil jwtUtil;   // ✅ Inject instance of JwtUtil
//
//    @PostMapping("/login")
//    public String login(@RequestBody Employee emp) {
//        return repo.findByEmployeeId(emp.getEmployeeId())
//                .filter(e -> e.getPassword().equals(emp.getPassword()))
//                .map(e -> jwtUtil.generateToken(e.getEmployeeId())) // ✅ Use instance
//                .orElse("INVALID CREDENTIALS");
//    }
}