package com.example.attendancesystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class student
{
    @Id
    private int id;
    public String name;

    public student(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
