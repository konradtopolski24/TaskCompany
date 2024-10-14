package com.kt.task.service.impl;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.entity.Company;
import com.kt.task.exception.CompanyNotFoundException;
import com.kt.task.repository.CompanyRepository;
import com.kt.task.service.CompanyService;
import com.kt.task.service.DataCheckerService;
import com.kt.task.service.MapperService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private static final String ERROR_ENTITY_NOT_EXIST = "message.error-entity-not-exist";

    private final CompanyRepository companyRepository;

    private final DataCheckerService dataCheckerService;

    private final MapperService mapperService;

    private final Environment env;

    @Override
    public CompanyResponse getCompany(Long id) {
        dataCheckerService.checkIfIdNotEmpty(id);
        final Company company = findCompany(id);
        return mapperService.mapToResponse(company);
    }

    @Override
    public CompanyGetAllResponse getAllCompanies(int pageNo, int pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<Company> companiesOnPage = companyRepository.findAll(pageable);
        return mapperService.mapToResponse(companiesOnPage);
    }

    @Override
    public CompanyResponse createCompany(CompanyDto companyDto) {
        final String name = companyDto.getName();
        dataCheckerService.checkIfNameNotEmpty(name);
        dataCheckerService.checkIfNameAlreadyExists(name);
        final Company company = saveCompany(companyDto);
        return mapperService.mapToResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(CompanyDto companyDto) {
        final Long id = companyDto.getId();
        dataCheckerService.checkIfIdNotEmpty(id);
        final String oldName = findNameById(id);
        final String newName = companyDto.getName();
        dataCheckerService.checkIfNameNotEmpty(newName);
        dataCheckerService.checkIfNewNameAlreadyExists(oldName, newName);
        final Company company = saveCompany(companyDto);
        return mapperService.mapToResponse(company);
    }

    @Override
    public CompanyDeleteResponse deleteCompany(Long id) {
        dataCheckerService.checkIfIdNotEmpty(id);
        dataCheckerService.checkIfEntityExists(id);
        companyRepository.deleteById(id);
        return mapperService.mapToResponse(true);
    }

    private Company findCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(
                        env.getProperty(ERROR_ENTITY_NOT_EXIST)));
    }

    private String findNameById(Long id) {
        return companyRepository.findNameById(id)
                .orElseThrow(() -> new CompanyNotFoundException(
                        env.getProperty(ERROR_ENTITY_NOT_EXIST)));
    }

    private Company saveCompany(CompanyDto companyDto) {
        final Company company = mapperService.mapToEntity(companyDto);
        return companyRepository.save(company);
    }
}
