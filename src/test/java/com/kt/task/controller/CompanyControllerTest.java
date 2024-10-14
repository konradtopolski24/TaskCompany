package com.kt.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.helper.TestDataProvider;
import com.kt.task.service.CompanyService;
import com.kt.task.service.MapperService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CompanyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private MapperService mapperService;

    private static final String API_PREFIX = "api.prefix";
    private static final String API_VERSION = "api.version";
    private static final String API_COMPANY = "api.company";
    private static final String API_GET = "api.get";
    private static final String API_GET_ALL = "api.get-all";
    private static final String API_CREATE = "api.create";
    private static final String API_UPDATE = "api.update";
    private static final String API_DELETE = "api.delete";

    private static final String SLASH = "/";
    private static final String EMPTY = "";

    private static final Long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Test";
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    private CompanyDto companyDto;
    private CompanyResponse response;
    private CompanyGetAllResponse getAllResponse;
    private CompanyDeleteResponse deleteResponse;

    @BeforeEach
    public void init() {
        companyDto = TestDataProvider.getCompanyDto(COMPANY_ID, COMPANY_NAME);
        response = TestDataProvider.getCompanyResponse(companyDto);
        getAllResponse = TestDataProvider.getCompanyGetAllResponse(companyDto, PAGE_NO, PAGE_SIZE);
        deleteResponse = TestDataProvider.getCompanyDeleteResponse(true);
    }

    @Test
    public void CompanyController_Get_ReturnResponse() throws Exception {
        when(companyService.getCompany(COMPANY_ID)).thenReturn(response);

        final ResultActions result = mockMvc.perform(get(getUrl(API_GET, COMPANY_ID))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id",
                        CoreMatchers.is(response.getContent().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.name",
                        CoreMatchers.is(response.getContent().getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CompanyController_GetAll_ReturnResponse() throws Exception {
        when(companyService.getAllCompanies(PAGE_NO,PAGE_SIZE)).thenReturn(getAllResponse);

        final ResultActions result = mockMvc.perform(get(getUrl(API_GET_ALL, null))
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", PAGE_NO.toString())
                .param("pageSize", PAGE_SIZE.toString()));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(getAllResponse.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CompanyController_Create_ReturnResponse() throws Exception {
        when(companyService.createCompany(companyDto)).thenReturn(response);

        final ResultActions result = mockMvc.perform(post(getUrl(API_CREATE, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id",
                        CoreMatchers.is(response.getContent().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.name",
                        CoreMatchers.is(response.getContent().getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CompanyController_Update_ReturnResponse() throws Exception {
        when(companyService.updateCompany(companyDto)).thenReturn(response);

        final ResultActions result = mockMvc.perform(put(getUrl(API_UPDATE, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id",
                        CoreMatchers.is(response.getContent().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.name",
                        CoreMatchers.is(response.getContent().getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CompanyController_Delete_ReturnResponse() throws Exception {
        when(companyService.deleteCompany(COMPANY_ID)).thenReturn(deleteResponse);

        final ResultActions result = mockMvc.perform(delete(getUrl(API_DELETE, COMPANY_ID))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(deleteResponse.isSuccess())))
                .andDo(MockMvcResultHandlers.print());
    }

    private String getUrl(String methodPropertyKey, Long id) {
        final String prefix = SLASH + env.getProperty(API_PREFIX);
        final String version = SLASH + env.getProperty(API_VERSION);
        final String company = SLASH + env.getProperty(API_COMPANY);
        final String method = SLASH + env.getProperty(methodPropertyKey);
        final String idProperty = id != null ? SLASH + id.intValue() : EMPTY;
        return prefix + version + company + method + idProperty;
    }
}