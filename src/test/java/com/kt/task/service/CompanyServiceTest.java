package com.kt.task.service;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.entity.Company;
import com.kt.task.helper.TestDataProvider;
import com.kt.task.repository.CompanyRepository;
import com.kt.task.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DataCheckerService dataCheckerService;

    @Mock
    private MapperService mapperService;

    @Mock
    private Environment env;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;

    private CompanyDto companyDto;

    private CompanyResponse response;
    private CompanyGetAllResponse getAllResponse;
    private CompanyDeleteResponse deleteResponse;

    private static final Long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Test";
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @BeforeEach
    public void init() {
        company = TestDataProvider.getCompany(COMPANY_NAME);
        companyDto = TestDataProvider.getCompanyDto(null, COMPANY_NAME);
        response = TestDataProvider.getCompanyResponse(companyDto);
        getAllResponse = TestDataProvider.getCompanyGetAllResponse(companyDto, PAGE_NO, PAGE_SIZE);
        deleteResponse = TestDataProvider.getCompanyDeleteResponse(true);
    }

    @Test
    public void CompanyService_GetCompany_ReturnResponse() {
        when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.ofNullable(company));
        when(mapperService.mapToResponse(Mockito.any(Company.class))).thenReturn(response);
        final CompanyResponse response = companyService.getCompany(COMPANY_ID);
        assertNotNull(response.getContent());
    }

    @Test
    public void CompanyService_GetAllCompanies_ReturnResponse() {
        final Page<Company> companiesOnPage = Mockito.mock(Page.class);
        when(companyRepository.findAll(Mockito.any(Pageable.class))).thenReturn(companiesOnPage);
        when(mapperService.mapToResponse(companiesOnPage)).thenReturn(getAllResponse);
        final CompanyGetAllResponse response = companyService.getAllCompanies(1, 10);
        assertNotNull(response.getContent());
    }

    @Test
    public void CompanyService_CreateCompany_ReturnResponse() {
        when(mapperService.mapToEntity(Mockito.any(CompanyDto.class))).thenReturn(company);
        when(companyRepository.save(Mockito.any(Company.class))).thenReturn(company);
        when(mapperService.mapToResponse(Mockito.any(Company.class))).thenReturn(response);
        final CompanyResponse response = companyService.createCompany(companyDto);
        assertNotNull(response.getContent());
    }

    @Test
    public void CompanyService_RemoveCompany_ReturnResponse() {
        when(mapperService.mapToResponse(true)).thenReturn(deleteResponse);
        final CompanyDeleteResponse response = companyService.deleteCompany(COMPANY_ID);
        assertTrue(response.isSuccess());
    }
}