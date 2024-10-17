package com.example.luxmed.service;


import com.example.luxmed.dto.CompanyDto;
import com.example.luxmed.dto.CompanyResponseDto;
import com.example.luxmed.dto.DepartmentDto;
import com.example.luxmed.dto.TeamDto;
import com.example.luxmed.exception.CompanyNotFoundException;
import com.example.luxmed.repository.CompanyRepository;
import com.example.luxmed.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class CompanyServiceImplTest {

    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void shouldCreateCompanySuccessfully() {
        //given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto("Hr", "Recruitment")
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto = prepareCompanyDto("TestCompanyOne", departments);

        //when
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);

        //then
        Assertions.assertNotNull(companyResponseDto);
        Assertions.assertEquals(companyResponseDto.name(), companyDto.name());
        Assertions.assertEquals(companyResponseDto.departmentNames(),
                companyDto.departments().stream().map(DepartmentDto::name).toList());
    }

    @Test
    public void shouldCreateCompanyWhenDepartmentsAreNotAssign() {

        //given
        CompanyDto companyDto = new CompanyDto("TestCompanyOne", List.of());

        //when
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);

        //then
        Assertions.assertNotNull(companyResponseDto);
        Assertions.assertEquals(companyResponseDto.name(), companyDto.name());
        Assertions.assertEquals(companyResponseDto.departmentNames(),
                companyDto.departments().stream().map(DepartmentDto::name).toList());
    }

    @Test
    public void shouldHandleDepartmentsWithNullNames() {

        //given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto(null, null)
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto = new CompanyDto("TestCompanyOne", departments);

        //when
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);

        //then
        Assertions.assertNotNull(companyResponseDto);
        Assertions.assertEquals(companyResponseDto.name(), companyDto.name());
        Assertions.assertEquals(2, departments.size());
        Assertions.assertEquals("It", departments.get(1).name());
        Assertions.assertNull(departments.get(0).name());
    }

    @Test
    public void shouldCreateCompanyWithDepartmentsAssigned() {

        //given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto("Hr", "Recruitment")
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto = new CompanyDto("TestCompanyOne", departments);

        //when
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);

        //then
        Assertions.assertNotNull(companyResponseDto);
        Assertions.assertEquals(companyResponseDto.name(), companyDto.name());
        Assertions.assertEquals(2,
                companyDto.departments().stream().map(DepartmentDto::name).toList().size());
    }

    @Test
    public void shouldGetAllCompanies() {
        //given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto("Hr", "Recruitment")
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto = prepareCompanyDto("TestCompanyOne", departments);
        CompanyDto companyDto1 = prepareCompanyDto("TestCompanyTwo", departments);
        CompanyDto companyDto2 = prepareCompanyDto("TestCompanyThree", departments);

        //when
        companyService.addCompany(companyDto1);
        companyService.addCompany(companyDto2);
        companyService.addCompany(companyDto);
        Pageable pageable = PageRequest.of(0, 2);

        Page<CompanyResponseDto> companies = companyService.getCompanies(pageable);
        //then
        Assertions.assertEquals(3, companies.getTotalElements());
        Assertions.assertTrue(companies.getContent().stream()
                .anyMatch(dto -> dto.name().equals("TestCompanyTwo")));
    }

    @Test
    public void shouldGetCompanyById() {
        // given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto("Hr", "Recruitment")
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto = prepareCompanyDto("TestCompanyOne", departments);
        CompanyDto companyDto1 = prepareCompanyDto("TestCompanyTwo", departments);

        // when
        companyService.addCompany(companyDto1);
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);
        CompanyResponseDto actualResponse = companyService.getCompanyById(companyResponseDto.id());

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals("TestCompanyOne", actualResponse.name());
    }

    @Test
    public void shouldDeleteCompanySuccessfully() {
        // given
        List<DepartmentDto> departments = List.of(prepareDepartmentDto("Hr", "Recruitment")
                , prepareDepartmentDto("It", "Administration")
        );
        CompanyDto companyDto1 = prepareCompanyDto("TestCompanyOne", departments);
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto1);
        Assertions.assertNotNull(companyResponseDto);

        // when
        companyService.deleteCompany(companyResponseDto.id());

        // then
        Assertions.assertThrows(CompanyNotFoundException.class, () -> {
            companyService.getCompanyById(companyResponseDto.id());
        });
    }

    @Test
    public void shouldThrowExceptionWhenCompanyNotExiting() {
        //given
        Long nonExistentCompanyId = 999L;

        //then
        CompanyNotFoundException exception = Assertions.assertThrows(CompanyNotFoundException.class, () -> {
            companyService.deleteCompany(nonExistentCompanyId);
        });
        Assertions.assertEquals("Company not found with id: " + nonExistentCompanyId, exception.getMessage());
    }

    private CompanyDto prepareCompanyDto(String companyName, List<DepartmentDto> departmentDtos) {
        return new CompanyDto.Builder()
                .name(companyName)
                .departments(departmentDtos)
                .build();
    }

    private DepartmentDto prepareDepartmentDto(String departmentName, String teamName) {
        return new DepartmentDto.Builder()
                .name(departmentName)
                .teams(List.of(
                        new TeamDto.Builder().name(teamName).project(null).build()
                ))
                .build();
    }

    @AfterEach
    public void clearDatabase() {
        companyRepository.deleteAll();
        departmentRepository.deleteAll();
    }
}
