package com.example.luxmed.dto;

public record ManagerDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        ProjectDto project
) {
    public static class Builder {
        private String name;
        private String lastName;
        private String email;
        private String phoneNumber;
        private ProjectDto project;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ManagerDto build() {
            return new ManagerDto(name, lastName, email, phoneNumber, project);
        }
    }
}
