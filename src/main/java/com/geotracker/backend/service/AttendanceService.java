package com.geotracker.backend.service;


import com.geotracker.backend.model.Attendance;
import com.geotracker.backend.repositories.AttendanceRepository;
import com.geotracker.backend.util.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

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

        if (!TimeValidator.isOfficeTime())
            return "Outside office hours";

        var geofence = geofenceService.getOfficeGeofence();

        if (!locationService.isInside(lat, lon, geofence))
            return "Outside office location";

//        if (repo.findByEmpIdAndDate(empId, LocalDate.now()).isPresent())
//            return "Attendance already marked";

        if (repo.existsByEmpIdAndDate(empId, LocalDate.now())) {
            return "Attendance already marked";
        }

        Attendance a = new Attendance();
        a.setEmpId(empId);
        a.setDate(LocalDate.now());
        a.setCheckInTime(LocalTime.now());

        a.setStatus(TimeValidator.isLate(a.getCheckInTime())
                ? "LATE" : "PRESENT");

        repo.save(a);
        return "Attendance marked: " + a.getStatus();
    }
}
