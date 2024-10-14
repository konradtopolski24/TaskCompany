package com.kt.task.repository;

import com.kt.task.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.name = :name")
    Optional<Company> findByName(String name);

    @Query("SELECT c.name FROM Company c WHERE c.id = :id")
    Optional<String> findNameById(Long id);
}
