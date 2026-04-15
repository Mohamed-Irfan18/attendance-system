package com.example.attendancesystem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AttendanceController
{
    private List<student> students = new ArrayList<>();
    private List<Attendance> attendanceList = new ArrayList<>();

    @PostMapping("/students")
    public String addStudent(@RequestBody student student)
    {
        students.add(student);
        return "Student added";
    }

    @GetMapping("/students")
    public List<student> getStudents()
    {
        return students;
    }

    @PostMapping("/attendance")
        public ResponseEntity<String> markAttendance(@RequestBody Attendance attendance)
        {
            boolean studentExists = false;

            for(student s : students)
            {
                if(s.getId() == attendance.getStudentId())
                {
                    studentExists = true;
                    break;
                }
            }

            if(!studentExists)
            {
                return ResponseEntity.status(404).body("Student not found");
            }

            for(Attendance a : attendanceList)
            {
                if(a.getStudentId() == attendance.getStudentId() && a.getDate().equals(attendance.getDate()))
                {
                    return ResponseEntity.status(400).body("Attendance already marked for this date");
                }
            }
            attendanceList.add(attendance);
            return ResponseEntity.ok("Attendance Marked");
        }

        @GetMapping("/attendance")
        public List<Attendance> getAttendance()
        {
            return attendanceList;
        }

        @GetMapping("/attendance/student/{id}")
        public List<Attendance> getAttendanceByStudent(@PathVariable int id)
        {
            List<Attendance> result =  new ArrayList<>();

            for(Attendance a : attendanceList)
            {
                if(a.getStudentId() == id)
                {
                    result.add(a);
                }
            }
            return result;
        }

        @GetMapping("/attendance/date/{date}")
        public List<Attendance> getAttendanceByDate(@PathVariable String date)
        {
            List<Attendance> result = new ArrayList<>();

            for(Attendance a : attendanceList)
            {
                if(a.getDate().equals(date))
                {
                    result.add(a);
                }
            }
            return result;
        }


}