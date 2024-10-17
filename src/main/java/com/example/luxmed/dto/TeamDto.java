package com.example.luxmed.dto;

public record TeamDto(
        String name,
        ProjectDto project
) {
    public static class Builder {
        private String name;
        private ProjectDto project;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder project(ProjectDto project) {
            this.project = project;
            return this;
        }

        public TeamDto build() {
            return new TeamDto(name, project);
        }
    }
}
