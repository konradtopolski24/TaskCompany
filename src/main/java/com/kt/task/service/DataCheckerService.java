package com.kt.task.service;

import com.kt.task.repository.CompanyRepository;

public interface DataCheckerService {

    void checkIfIdNotEmpty(Long id);

    void checkIfNameNotEmpty(String name);

    void checkIfNewNameAlreadyExists(String oldName, String newName);

    void checkIfNameAlreadyExists(String name);

    void checkIfEntityExists(Long id);
}
