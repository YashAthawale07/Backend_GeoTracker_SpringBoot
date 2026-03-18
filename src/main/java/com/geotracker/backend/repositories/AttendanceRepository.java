package com.geotracker.backend.repositories;

import com.geotracker.backend.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    boolean existsByEmpIdAndDate(String empId, LocalDate date);
    Optional<Attendance> findByEmpIdAndDate(String empId, LocalDate date);

    // Employee filters
    List<Attendance> findByEmpId(String empId);
    List<Attendance> findByEmpIdAndDateBetween(String empId, LocalDate startDate, LocalDate endDate);
    List<Attendance> findByEmpIdAndStatus(String empId, String status);
    List<Attendance> findByEmpIdAndStatusAndDateBetween(String empId, String status, LocalDate startDate, LocalDate endDate);

    // Admin filters (all employees)
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Attendance> findByStatus(String status);
    List<Attendance> findByStatusAndDateBetween(String status, LocalDate startDate, LocalDate endDate);
}