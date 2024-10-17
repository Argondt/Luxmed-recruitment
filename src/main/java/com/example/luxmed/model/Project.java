package com.example.luxmed.model;

import jakarta.persistence.*;


@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    public Project() {
    }

    public Project(Long id, Team team, Manager manager) {
        this.id = id;
        this.team = team;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
