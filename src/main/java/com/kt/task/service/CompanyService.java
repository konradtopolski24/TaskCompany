package com.kt.task.service;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;

public interface CompanyService {

    CompanyResponse getCompany(Long id);

    CompanyGetAllResponse getAllCompanies(int pageNo, int pageSize);

    CompanyResponse createCompany(CompanyDto companyDto);

    CompanyResponse updateCompany(CompanyDto companyDto);

    CompanyDeleteResponse deleteCompany(Long id);
}
