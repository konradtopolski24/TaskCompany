package com.kt.task.service;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyErrorResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.WebRequest;

public interface MapperService {

    Company mapToEntity(CompanyDto companyDto);

    CompanyResponse mapToResponse(Company company);

    CompanyGetAllResponse mapToResponse(Page<Company> companiesOnPage);

    CompanyDeleteResponse mapToResponse(boolean success);

    CompanyErrorResponse mapToResponse(String message, WebRequest request);
}
