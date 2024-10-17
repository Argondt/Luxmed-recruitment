package com.example.luxmed.dto;

import java.util.List;

public record CompanyResponseDto(
        Long id,
        String name,
        List<String> departmentNames
) {
    public static class Builder {
        private Long id;
        private String name;
        private List<String> departmentNames;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder departmentNames(List<String> departmentNames) {
            this.departmentNames = departmentNames;
            return this;
        }

        public CompanyResponseDto build() {
            return new CompanyResponseDto(id, name, departmentNames);
        }
    }
}
