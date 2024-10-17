package com.example.luxmed.service;

import com.example.luxmed.dto.CompanyDto;
import com.example.luxmed.dto.CompanyResponseDto;
import com.example.luxmed.dto.DepartmentDto;
import com.example.luxmed.exception.CompanyNotFoundException;
import com.example.luxmed.mapper.CompanyMapper;
import com.example.luxmed.model.Company;
import com.example.luxmed.model.Department;
import com.example.luxmed.repository.CompanyRepository;
import com.example.luxmed.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    public CompanyServiceImpl(CompanyRepository companyRepository, DepartmentRepository departmentRepository) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public CompanyResponseDto addCompany(CompanyDto companyDto) {
        logger.info("Adding new company with details: {}", companyDto);
        Company companyToSave = CompanyMapper.INSTANCE.toEntity(companyDto);
        if (companyToSave.getDepartments() != null) {
            companyToSave.getDepartments().forEach(department -> department.setCompany(companyToSave));
        }
        Company createdCompany = companyRepository.save(companyToSave);
        logger.info("Successfully saving new company with  id {}", createdCompany.getId());
        return CompanyMapper.INSTANCE.toCompanyResponseDto(createdCompany);
    }

    @Override
    public CompanyResponseDto updateCompany(Long id, CompanyDto companyDto) {
        logger.info("Updating company with id: {}", id);
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("Company not found with id: " + id));
        CompanyMapper.INSTANCE.update(company, companyDto);
        List<String> departmentNames = Optional.ofNullable(companyDto.departments())
                .orElse(Collections.emptyList())
                .stream()
                .map(DepartmentDto::name)
                .collect(Collectors.toList());
        List<Department> departments = departmentNames.stream()
                .map(name -> departmentRepository.findByName(name)
                        .orElseGet(() -> {
                            Department newDepartment = new Department();
                            newDepartment.setName(name);
                            newDepartment.setCompany(company);
                            return newDepartment;
                        }))
                .collect(Collectors.toList());
        company.getDepartments().clear();
        company.getDepartments().addAll(departments);
        Company updatedCompany = companyRepository.save(company);
        logger.info("Successfully updated company with id: {}", id);
        return CompanyMapper.INSTANCE.toCompanyResponseDto(updatedCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponseDto getCompanyById(Long id) {
        logger.info("Fetching company with id: {}", id);
        return companyRepository.findById(id)
                .map(CompanyMapper.INSTANCE::toCompanyResponseDto)
                .orElseThrow(() ->
                        new CompanyNotFoundException("Company not found with id: " + id));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponseDto> getCompanies(Pageable pageable) {
        logger.info("Fetching all companies with pagination: {}", pageable);
        return companyRepository.findAll(pageable)
                .map(CompanyMapper.INSTANCE::toCompanyResponseDto);
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        logger.info("Deleting company with id: {}", id);
        Company byId = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("Company not found with id: " + id));
        companyRepository.delete(byId);
        logger.info("Successfully deleted company with id: {}", id);
    }
}
