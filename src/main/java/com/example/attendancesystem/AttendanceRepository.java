package com.example.attendancesystem;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudent_Id(Integer id);
    List<Attendance> findByDate(String date);
}