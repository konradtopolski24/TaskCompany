package com.kt.task.helper;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.entity.Company;

import java.util.Collections;

public class TestDataProvider {

    private TestDataProvider() {
    }

    public static CompanyDto getCompanyDto(Long id, String name) {
        return CompanyDto.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Company getCompany(String name) {
        return Company.builder()
                .name(name)
                .build();
    }

    public static CompanyResponse getCompanyResponse(CompanyDto companyDto) {
        return CompanyResponse.builder()
                .content(companyDto)
                .build();
    }

    public static CompanyGetAllResponse getCompanyGetAllResponse(CompanyDto companyDto,
                                                                 Integer pageNo,
                                                                 Integer pageSize) {
        return CompanyGetAllResponse.builder()
                .content(Collections.singletonList(companyDto))
                .pageSize(pageSize)
                .last(true)
                .pageNo(pageNo)
                .build();
    }

    public static CompanyDeleteResponse getCompanyDeleteResponse(boolean success) {
        return CompanyDeleteResponse.builder()
                .success(success)
                .build();
    }
}
