package com.example.luxmed.service;

import com.example.luxmed.dto.CompanyDto;
import com.example.luxmed.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    CompanyResponseDto addCompany(CompanyDto company);

    CompanyResponseDto updateCompany(Long id, CompanyDto companyDto);

    CompanyResponseDto getCompanyById(Long id);

    Page<CompanyResponseDto> getCompanies(Pageable pageable);

    void deleteCompany(Long id);
}
