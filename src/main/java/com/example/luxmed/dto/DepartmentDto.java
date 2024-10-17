package com.example.luxmed.dto;

import java.util.List;


public record DepartmentDto(
        String name,
        List<TeamDto> teams
) {
    public static class Builder {
        private String name;
        private List<TeamDto> teams;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder teams(List<TeamDto> teams) {
            this.teams = teams;
            return this;
        }

        public DepartmentDto build() {
            return new DepartmentDto(name, teams);
        }
    }
}
