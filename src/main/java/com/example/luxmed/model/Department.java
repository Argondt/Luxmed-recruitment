package com.example.luxmed.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Team> teams;


    public Department(Long id, String name, Company company, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.teams = teams;
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

    public Department() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
