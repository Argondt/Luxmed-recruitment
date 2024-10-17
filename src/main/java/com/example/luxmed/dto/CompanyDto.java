package com.example.luxmed.dto;


import java.util.List;

public record CompanyDto(
        String name,
        List<DepartmentDto> departments
) {
    public static class Builder {
        private String name;
        private List<DepartmentDto> departments;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder departments(List<DepartmentDto> departments) {
            this.departments = departments;
            return this;
        }

        public CompanyDto build() {
            return new CompanyDto(name, departments);
        }
    }
}
