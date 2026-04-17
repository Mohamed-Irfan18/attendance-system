package com.example.attendancesystem;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ Add Attendance
    @PostMapping("/attendance")
    public ResponseEntity<ApiResponse<?>> markAttendance(@Valid @RequestBody Attendance attendance) {

        Integer studentId = attendance.getStudent().getId();

        student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        attendance.setStudent(student);

        Attendance saved = attendanceRepository.save(attendance);

        return ResponseEntity.ok(new ApiResponse<>("Attendance recorded", saved));
    }

    // ✅ Get all
    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAll() {

        List<AttendanceDTO> list = attendanceRepository.findAll()
                .stream()
                .map(a -> new AttendanceDTO(
                        a.getId(),
                        a.getStudent().getId(),
                        a.getStudent().getName(),
                        a.getDate(),
                        a.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(new ApiResponse<>("Success", list));
    }

    // ✅ Filter by student
    @GetMapping("/attendance/student/{id}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getByStudent(@PathVariable Integer id) {

        List<AttendanceDTO> list = attendanceRepository.findByStudent_Id(id)
                .stream()
                .map(a -> new AttendanceDTO(
                        a.getId(),
                        a.getStudent().getId(),
                        a.getStudent().getName(),
                        a.getDate(),
                        a.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(new ApiResponse<>("Success", list));
    }
}