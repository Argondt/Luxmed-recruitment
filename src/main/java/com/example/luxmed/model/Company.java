package com.example.luxmed.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.MERGE}, mappedBy = "company")
    private List<Department> departments = new ArrayList<>();

    public Company() {
    }

    public Company(Long id, String name, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
