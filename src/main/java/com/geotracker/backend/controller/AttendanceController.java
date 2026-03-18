package com.geotracker.backend.controller;

import com.geotracker.backend.model.Attendance;
import com.geotracker.backend.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    // POST /attendance/mark?empId=EMP101&lat=21.0077&lon=75.5626
    @PostMapping("/mark")
    public String mark(@RequestParam String empId,
                       @RequestParam double lat,
                       @RequestParam double lon) {
        System.out.println("Mark Attendance called for: " + empId);
        return service.markAttendance(empId, lat, lon);
    }

    // GET /attendance/history?empId=EMP101
    @GetMapping("/history")
    public ResponseEntity<List<Attendance>> getHistory(@RequestParam String empId) {
        return ResponseEntity.ok(service.getMyHistory(empId));
    }

    // GET /attendance/history/month?empId=EMP101&month=3&year=2026
    @GetMapping("/history/month")
    public ResponseEntity<List<Attendance>> getHistoryByMonth(
            @RequestParam String empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(service.getMyHistoryByMonth(empId, month, year));
    }

    // GET /attendance/history/range?empId=EMP101&startDate=2026-03-01&endDate=2026-03-18
    @GetMapping("/history/range")
    public ResponseEntity<List<Attendance>> getHistoryByDateRange(
            @RequestParam String empId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getMyHistoryByDateRange(empId, startDate, endDate));
    }

    // GET /attendance/history/status?empId=EMP101&status=LATE
    @GetMapping("/history/status")
    public ResponseEntity<List<Attendance>> getHistoryByStatus(
            @RequestParam String empId,
            @RequestParam String status) {
        return ResponseEntity.ok(service.getMyHistoryByStatus(empId, status));
    }

    // GET /attendance/history/filter?empId=EMP101&status=LATE&startDate=2026-03-01&endDate=2026-03-18
    @GetMapping("/history/filter")
    public ResponseEntity<List<Attendance>> getHistoryByStatusAndRange(
            @RequestParam String empId,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getMyHistoryByStatusAndDateRange(empId, status, startDate, endDate));
    }
}