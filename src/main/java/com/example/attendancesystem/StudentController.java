package com.example.attendancesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudents() {
        List<StudentDTO> students = studentRepository.findAll()
                .stream()
                .map(student -> new StudentDTO(student.getId(), student.getName()))
                .toList();

        return ResponseEntity.ok(new ApiResponse<>("Success", students));
    }
}
