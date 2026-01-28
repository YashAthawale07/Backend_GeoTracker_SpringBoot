//package com.geotracker.backend.scheduler;
//
//
//
//import com.geotracker.backend.repositories.EmployeeRepository;
//import com.geotracker.backend.service.AttendanceService;
//import com.geotracker.backend.service.GeofenceService;
//import com.geotracker.backend.service.LocationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//@Component
//public class AttendanceScheduler {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private AttendanceService attendanceService;
//
//    @Autowired
//    private LocationService locationService;
//
//    @Autowired
//    private GeofenceService geofenceService;
//
//    private static final double OFFICE_LAT = 12.345; // replace with actual office lat
//    private static final double OFFICE_LON = 67.890; // replace with actual office lon
//
//    @Scheduled(fixedRate = 60000) // every 1 minute
//    public void runScheduler() {
//        System.out.println("⏰ Scheduler triggered at " + LocalDateTime.now());
//
//        var employees = employeeRepository.findAll();
//
//        for (var emp : employees) {
//            // Get employee current location
//            double lat = emp.getCurrentLat(); // you might need to temporarily add these fields or fetch from another service
//            double lon = emp.getCurrentLon();
//
//            // First, attempt auto check-in using your existing method
//            String result = attendanceService.markAttendance(emp.getEmpId(), lat, lon);
//            System.out.println(result);
//
//            // Then, handle auto check-out if employee is outside office and has checked in
//            autoCheckout(emp.getEmpId(), lat, lon);
//        }
//    }
//
//    private void autoCheckout(String empId, double lat, double lon) {
//        var attendanceOpt = attendanceService.getAttendanceByEmpIdAndDate(empId, LocalDate.now());
//
//        if (attendanceOpt.isEmpty()) return;
//
//        var attendance = attendanceOpt.get();
//
//        // If employee has checked in but not checked out
//        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() == null) {
//
//            var geofence = geofenceService.getOfficeGeofence();
//            boolean inOffice = locationService.isInside(lat, lon, geofence);
//
//            // If employee is outside office, auto checkout
//            if (!inOffice) {
//                attendance.setCheckOutTime(LocalTime.now());
//
//                // Calculate working hours
//                long hoursWorked = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toHours();
//                attendance.setWorkingHours(hoursWorked);
//
//                // Calculate extra hours if more than 8
//                if (hoursWorked > 8)
//                    attendance.setExtraHours(hoursWorked - 8);
//
//                attendanceService.saveAttendance(attendance);
//
//                System.out.println("Auto checkout for empId = " + empId);
//            }
//        }
//    }
//
//
//    public void runNow() {
//        autoCheckout();
//    }
//}
