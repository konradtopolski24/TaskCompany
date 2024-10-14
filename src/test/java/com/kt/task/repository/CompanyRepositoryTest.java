package com.kt.task.repository;

import com.kt.task.entity.Company;
import com.kt.task.helper.TestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private Company companyA;

    private Company companyB;

    private static final String COMPANY_A_NAME = "TestA";
    private static final String COMPANY_B_NAME = "TestB";
    private static final String COMPANY_NEW_NAME = "TestUpdated";

    @BeforeEach
    public void init() {
        companyA = TestDataProvider.getCompany(COMPANY_A_NAME);
        companyB = TestDataProvider.getCompany(COMPANY_B_NAME);
    }

    @Test
    public void CompanyRepository_Save_ReturnSavedCompany() {
        final Company savedCompany = companyRepository.save(companyA);
        assertNotNull(savedCompany);
        assertTrue(savedCompany.getId() > 0);
    }

    @Test
    public void CompanyRepository_FindAll_ReturnMoreThanOneCompany() {
        companyRepository.save(companyA);
        companyRepository.save(companyB);
        final List<Company> foundCompanies = companyRepository.findAll();
        assertNotNull(foundCompanies);
        assertEquals(2, foundCompanies.size());
    }

    @Test
    public void CompanyRepository_FindById_ReturnCompany() {
        companyRepository.save(companyA);
        final Company foundCompany = companyRepository.findById(companyA.getId()).orElseThrow();
        assertNotNull(foundCompany);
    }

    @Test
    public void CompanyRepository_FindByName_ReturnCompanyNotNull() {
        companyRepository.save(companyA);
        final Company foundCompany = companyRepository.findByName(companyA.getName()).orElseThrow();
        assertNotNull(foundCompany);
    }

    @Test
    public void CompanyRepository_FindNameById_ReturnName() {
        companyRepository.save(companyA);
        final String foundName = companyRepository.findNameById(companyA.getId()).orElseThrow();
        assertNotNull(foundName);
    }

    @Test
    public void CompanyRepository_Update_ReturnCompanyNotNull() {
        companyRepository.save(companyA);
        final Company savedCompany = companyRepository.findById(companyA.getId()).orElseThrow();
        savedCompany.setName(COMPANY_NEW_NAME);
        final Company updatedCompany = companyRepository.save(savedCompany);
        final String updatedCompanyName = updatedCompany.getName();
        assertNotNull(updatedCompanyName);
        assertEquals(COMPANY_NEW_NAME, updatedCompanyName);
    }

    @Test
    public void CompanyRepository_DeleteById_ReturnCompanyIsEmpty() {
        companyRepository.save(companyA);
        final Long id = companyA.getId();
        companyRepository.deleteById(id);
        final Optional<Company> foundCompany = companyRepository.findById(id);
        assertTrue(foundCompany.isEmpty());
    }
}