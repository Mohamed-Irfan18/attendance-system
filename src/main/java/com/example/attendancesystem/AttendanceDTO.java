package com.example.attendancesystem;

public class AttendanceDTO {

    private Integer id;
    private Integer studentId;
    private String studentName;
    private String date;
    private String status;

    public AttendanceDTO(Integer id, Integer studentId, String studentName, String date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public Integer getId() { return id; }
    public Integer getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
}