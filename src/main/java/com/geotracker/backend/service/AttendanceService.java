package com.geotracker.backend.service;

import com.geotracker.backend.model.Attendance;
import com.geotracker.backend.model.Geofence;
import com.geotracker.backend.repositories.AttendanceRepository;
import com.geotracker.backend.repositories.EmployeeRepository;
import com.geotracker.backend.util.TimeValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;
    private final GeofenceService geofenceService;
    private final LocationService locationService;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository repo, GeofenceService geofenceService,
                             LocationService locationService, EmployeeRepository employeeRepository) {
        this.repo = repo;
        this.geofenceService = geofenceService;
        this.locationService = locationService;
        this.employeeRepository = employeeRepository;
    }

    // ===== Mark Attendance =====
    public String markAttendance(String empId, double lat, double lon) {

//        if (!TimeValidator.isOfficeTime()) {
//            System.out.println("Outside office hours");
//            return "Outside office hours";
//        }

        Geofence geofence;
        try {
            geofence = geofenceService.getOfficeGeofence();
        } catch (RuntimeException e) {
            System.out.println("Office location not configured");
            return "Office location not configured. Contact admin.";
        }

//        if (!locationService.isInside(lat, lon, geofence)) {
//            System.out.println("Outside office location");
//            return "Outside office location";
//        }

        if (repo.existsByEmpIdAndDate(empId, LocalDate.now())) {
            System.out.println("Attendance already marked");
            return "Attendance already marked";
        }

        // Fetch employee name
        String empName = employeeRepository.findByEmpId(empId)
                .map(e -> e.getName())
                .orElse("Unknown");

        Attendance a = new Attendance();
        a.setEmpId(empId);
        a.setEmpName(empName);   // ← save name
        a.setDate(LocalDate.now());
        a.setCheckInTime(LocalTime.now());
        a.setStatus(TimeValidator.isLate(a.getCheckInTime()) ? "LATE" : "PRESENT");

        System.out.println("Attendance marked: " + a.getStatus());
        repo.save(a);

        return "Attendance marked: " + a.getStatus();
    }

    // ===== Employee: Attendance History =====

    public List<Attendance> getMyHistory(String empId) {
        return repo.findByEmpId(empId);
    }

    public List<Attendance> getMyHistoryByMonth(String empId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return repo.findByEmpIdAndDateBetween(empId, start, end);
    }

    public List<Attendance> getMyHistoryByDateRange(String empId, LocalDate startDate, LocalDate endDate) {
        return repo.findByEmpIdAndDateBetween(empId, startDate, endDate);
    }

    public List<Attendance> getMyHistoryByStatus(String empId, String status) {
        return repo.findByEmpIdAndStatus(empId, status.toUpperCase());
    }

    public List<Attendance> getMyHistoryByStatusAndDateRange(String empId, String status, LocalDate startDate, LocalDate endDate) {
        return repo.findByEmpIdAndStatusAndDateBetween(empId, status.toUpperCase(), startDate, endDate);
    }

    // ===== Admin: Attendance History =====

    public List<Attendance> getAllHistory() {
        return repo.findAll();
    }

    public List<Attendance> getAllHistoryByMonth(int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return repo.findByDateBetween(start, end);
    }

    public List<Attendance> getEmployeeHistory(String empId) {
        return repo.findByEmpId(empId);
    }

    public List<Attendance> getEmployeeHistoryByMonth(String empId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return repo.findByEmpIdAndDateBetween(empId, start, end);
    }

    public List<Attendance> getEmployeeHistoryByDateRange(String empId, LocalDate startDate, LocalDate endDate) {
        return repo.findByEmpIdAndDateBetween(empId, startDate, endDate);
    }

    public List<Attendance> getEmployeeHistoryByStatus(String empId, String status) {
        return repo.findByEmpIdAndStatus(empId, status.toUpperCase());
    }

    public List<Attendance> getAllHistoryByDateRange(LocalDate startDate, LocalDate endDate) {
        return repo.findByDateBetween(startDate, endDate);
    }

    public List<Attendance> getAllHistoryByStatus(String status) {
        return repo.findByStatus(status.toUpperCase());
    }

    public List<Attendance> getAllHistoryByStatusAndDateRange(String status, LocalDate startDate, LocalDate endDate) {
        return repo.findByStatusAndDateBetween(status.toUpperCase(), startDate, endDate);
    }
}