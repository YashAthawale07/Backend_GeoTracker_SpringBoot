package com.geotracker.backend.service;


import com.geotracker.backend.model.Attendance;
import com.geotracker.backend.repositories.AttendanceRepository;
import com.geotracker.backend.util.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repo;
    private final GeofenceService geofenceService;
    private final LocationService locationService;

    public AttendanceService(AttendanceRepository repo, GeofenceService geofenceService, LocationService locationService) {
        this.repo = repo;
        this.geofenceService = geofenceService;
        this.locationService = locationService;
    }

    public String markAttendance(String empId, double lat, double lon) {

        if (!TimeValidator.isOfficeTime()){
            System.out.println("Outside office hours");
            return "Outside office hours";
        }


        var geofence = geofenceService.getOfficeGeofence();

        if (!locationService.isInside(lat, lon, geofence)){
            System.out.println("Outside office location");
            return "Outside office location";
        }


//        if (repo.findByEmpIdAndDate(empId, LocalDate.now()).isPresent())
//            return "Attendance already marked";

        if (repo.existsByEmpIdAndDate(empId, LocalDate.now())) {
            System.out.println("Attendance already marked");
            return "Attendance already marked";
        }

        Attendance a = new Attendance();
        a.setEmpId(empId);
        a.setDate(LocalDate.now());
        a.setCheckInTime(LocalTime.now());

        a.setStatus(TimeValidator.isLate(a.getCheckInTime())
                ? "LATE" : "PRESENT");
        System.out.println("Attendance marked: " + a.getStatus());

        repo.save(a);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Attendance marked successfully");
//        return ResponseEntity.ok(response);
        return "Attendance marked: " + a.getStatus();
    }
}
