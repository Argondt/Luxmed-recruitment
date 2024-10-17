package com.example.luxmed.mapper;

import com.example.luxmed.dto.CompanyDto;
import com.example.luxmed.dto.CompanyResponseDto;
import com.example.luxmed.dto.DepartmentDto;
import com.example.luxmed.model.Company;
import com.example.luxmed.model.Department;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    void update(@MappingTarget Company updateCompany, CompanyDto companyDto);

    Company toEntity(CompanyDto companyDto);

    @Mapping(target = "departmentNames", expression = "java(company.getDepartments().stream().map(Department::getName).toList())")
    CompanyResponseDto toCompanyResponseDto(Company company);

    @AfterMapping
    default void addRelation(@MappingTarget Company company, CompanyDto companyDto) {
        if (companyDto.departments() != null) {
            List<Department> departments = companyDto.departments().stream()
                    .map(departmentDto -> getDepartment(company, departmentDto))
                    .collect(Collectors.toList());
            company.setDepartments(departments);
        }
    }

    private static Department getDepartment(Company company, DepartmentDto departmentDto) {
        Department department = DepartmentMapper.INSTANCE.toEntity(departmentDto);
        department.setCompany(company);
        return department;
    }
}
