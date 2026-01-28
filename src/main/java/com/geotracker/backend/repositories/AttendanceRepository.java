package com.geotracker.backend.repositories;

import com.geotracker.backend.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    boolean existsByEmpIdAndDate(String userId, LocalDate date);
    Optional<Attendance> findByEmpIdAndDate(String empId, LocalDate date);
//    List<Attendance> findByCheckOutTimeIsNull();
}
