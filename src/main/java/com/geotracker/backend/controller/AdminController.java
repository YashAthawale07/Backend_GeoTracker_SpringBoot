package com.geotracker.backend.controller;

import com.geotracker.backend.model.Attendance;
import com.geotracker.backend.model.Geofence;
import com.geotracker.backend.service.AttendanceService;
import com.geotracker.backend.service.GeofenceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final GeofenceService geofenceService;
    private final AttendanceService attendanceService;

    public AdminController(GeofenceService geofenceService, AttendanceService attendanceService) {
        this.geofenceService = geofenceService;
        this.attendanceService = attendanceService;
    }

    // ===== Geofence =====

    // GET /admin/geofence
    @GetMapping("/geofence")
    public ResponseEntity<?> getOfficeLocation() {
        try {
            Geofence geofence = geofenceService.getOfficeGeofence();
            return ResponseEntity.ok(geofence);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /admin/geofence
    // Body: { "latitude": 21.0077, "longitude": 75.5626, "radius": 150 }
    @PostMapping("/geofence")
    public ResponseEntity<Geofence> setOfficeLocation(@RequestBody Geofence geofence) {
        Geofence saved = geofenceService.setOfficeGeofence(
                geofence.getLatitude(),
                geofence.getLongitude(),
                geofence.getRadius()
        );
        return ResponseEntity.ok(saved);
    }

    // ===== Admin: All Employees Attendance =====

    // GET /admin/attendance
    @GetMapping("/attendance")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllHistory());
    }

    // GET /admin/attendance/all/month?month=3&year=2026
    @GetMapping("/attendance/all/month")
    public ResponseEntity<List<Attendance>> getAllAttendanceByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(attendanceService.getAllHistoryByMonth(month, year));
    }

    // GET /admin/attendance/all/range?startDate=2026-03-01&endDate=2026-03-18
    @GetMapping("/attendance/all/range")
    public ResponseEntity<List<Attendance>> getAllAttendanceByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getAllHistoryByDateRange(startDate, endDate));
    }

    // GET /admin/attendance/all/status?status=LATE
    @GetMapping("/attendance/all/status")
    public ResponseEntity<List<Attendance>> getAllAttendanceByStatus(@RequestParam String status) {
        return ResponseEntity.ok(attendanceService.getAllHistoryByStatus(status));
    }

    // GET /admin/attendance/all/filter?status=LATE&startDate=2026-03-01&endDate=2026-03-18
    @GetMapping("/attendance/all/filter")
    public ResponseEntity<List<Attendance>> getAllAttendanceByStatusAndRange(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getAllHistoryByStatusAndDateRange(status, startDate, endDate));
    }

    // ===== Admin: Specific Employee Attendance =====

    // GET /admin/attendance/EMP101
    @GetMapping("/attendance/{empId}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendance(@PathVariable String empId) {
        return ResponseEntity.ok(attendanceService.getEmployeeHistory(empId));
    }

    // GET /admin/attendance/EMP101/month?month=3&year=2026
    @GetMapping("/attendance/{empId}/month")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceByMonth(
            @PathVariable String empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(attendanceService.getEmployeeHistoryByMonth(empId, month, year));
    }

    // GET /admin/attendance/EMP101/range?startDate=2026-03-01&endDate=2026-03-18
    @GetMapping("/attendance/{empId}/range")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceByRange(
            @PathVariable String empId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getEmployeeHistoryByDateRange(empId, startDate, endDate));
    }

    // GET /admin/attendance/EMP101/status?status=LATE
    @GetMapping("/attendance/{empId}/status")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceByStatus(
            @PathVariable String empId,
            @RequestParam String status) {
        return ResponseEntity.ok(attendanceService.getEmployeeHistoryByStatus(empId, status));
    }
}