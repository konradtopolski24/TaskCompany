package com.kt.task.service.impl;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyErrorResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.entity.Company;
import com.kt.task.service.MapperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    @Override
    public Company mapToEntity(CompanyDto companyDto) {
        return modelMapper.map(companyDto, Company.class);
    }

    @Override
    public CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .content(mapToDto(company))
                .build();
    }

    @Override
    public CompanyGetAllResponse mapToResponse(Page<Company> companiesOnPage) {
        final List<Company> companies = companiesOnPage.getContent();
        final List<CompanyDto> content = mapToDto(companies);
        return CompanyGetAllResponse.builder()
                .content(content)
                .pageNo(companiesOnPage.getNumber())
                .pageSize(companiesOnPage.getSize())
                .totalElements(companiesOnPage.getTotalElements())
                .totalPages(companiesOnPage.getTotalPages())
                .build();
    }

    @Override
    public CompanyDeleteResponse mapToResponse(boolean success) {
        return CompanyDeleteResponse.builder()
                .success(success)
                .build();
    }

    @Override
    public CompanyErrorResponse mapToResponse(String message, WebRequest request) {
        return CompanyErrorResponse.builder()
                .date(new Date())
                .message(message)
                .requestInfo(request.getDescription(false))
                .build();
    }

    public CompanyDto mapToDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }

    private List<CompanyDto> mapToDto(List<Company> companies) {
        return companies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
