package com.example.attendancesystem;

public class Attendance
{
    private int id;
    private int studentId;
    private String date;
    private String status;

    public Attendance(int id, int studentId, String date, String status)
    {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public String getDate()
    {
        return date;
    }

    public String getStatus()
    {
        return status;
    }
}
