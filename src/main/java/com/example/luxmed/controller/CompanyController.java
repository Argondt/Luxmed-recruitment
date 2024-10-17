package com.example.luxmed.controller;

import com.example.luxmed.dto.CompanyDto;
import com.example.luxmed.dto.CompanyResponseDto;
import com.example.luxmed.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyResponseDto companyResponseDto = companyService.addCompany(companyDto);
        return new ResponseEntity<>(companyResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {
        CompanyResponseDto companyDTO = companyService.getCompanyById(id);
        return companyDTO != null ? ResponseEntity.ok(companyDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<Page<CompanyResponseDto>> getCompanies(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "id") String sort) {
        Pageable pageable = (page != null && size != null)
                ? PageRequest.of(page, size, Sort.by(sort))
                : Pageable.unpaged();
        Page<CompanyResponseDto> companies = companyService.getCompanies(pageable);
        return ResponseEntity.ok(companies);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable("id") Long companyId,
                                                            @RequestBody CompanyDto companyDto) {
        CompanyResponseDto companyResponseDto = companyService.updateCompany(companyId, companyDto);
        return new ResponseEntity<>(companyResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

}
