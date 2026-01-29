package com.geotracker.backend.controller;

import com.geotracker.backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:56496")
@RequestMapping("/attendance")
//@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/mark")
    public String mark(@RequestParam String empId,
                       @RequestParam double lat,
                       @RequestParam double lon) {
        System.out.println("Mark Attendance is called");
        return service.markAttendance(empId, lat, lon);
    }
}