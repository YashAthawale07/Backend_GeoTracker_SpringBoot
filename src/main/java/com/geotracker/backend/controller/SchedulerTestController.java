//package com.geotracker.backend.controller;
//
//import com.geotracker.backend.scheduler.AttendanceScheduler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test")
//public class SchedulerTestController {
//
//    private final AttendanceScheduler scheduler;
//
//    public SchedulerTestController(AttendanceScheduler scheduler) {
//        this.scheduler = scheduler;
//    }
//
//    @PostMapping("/run-scheduler")
//    public String runScheduler() {
//        scheduler.runNow();
//        return "Scheduler executed";
//    }
//}
