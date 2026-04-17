package com.example.attendancesystem;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "date"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private student student;

    @NotBlank
    private String date;

    @NotBlank
    private String status;

    public Attendance() {}

    public Integer getId() { return id; }
    public student getStudent() { return student; }
    public void setStudent(student student) { this.student = student; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}