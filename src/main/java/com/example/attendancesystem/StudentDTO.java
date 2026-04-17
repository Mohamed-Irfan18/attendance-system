package com.example.attendancesystem;

public class StudentDTO {

    private Integer id;
    private String name;

    public StudentDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}