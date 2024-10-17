package com.example.luxmed.dto;

public record ProjectDto(
        ManagerDto manager
) {
    public static class Builder {
        private ManagerDto manager;

        public Builder manager(ManagerDto manager) {
            this.manager = manager;
            return this;
        }

        public ProjectDto build() {
            return new ProjectDto(manager);
        }
    }
}
