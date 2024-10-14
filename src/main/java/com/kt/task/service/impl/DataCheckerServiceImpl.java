package com.kt.task.service.impl;

import com.kt.task.exception.CompanyNotFoundException;
import com.kt.task.exception.InvalidCompanyRequestException;
import com.kt.task.repository.CompanyRepository;
import com.kt.task.service.DataCheckerService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataCheckerServiceImpl implements DataCheckerService {

    private static final String ERROR_NAME_EXIST = "message.error-name-exist";
    private static final String ERROR_NAME_EMPTY = "message.error-name-empty";
    private static final String ERROR_ENTITY_NOT_EXIST = "message.error-entity-not-exist";
    private static final String ERROR_ID_EMPTY = "message.error-id-empty";

    private final CompanyRepository companyRepository;

    private final Environment env;

    @Override
    public void checkIfIdNotEmpty(Long id) {
        if (id == null) {
            throw new InvalidCompanyRequestException(
                    env.getProperty(ERROR_ID_EMPTY));
        }
    }

    @Override
    public void checkIfNameNotEmpty(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidCompanyRequestException(
                    env.getProperty(ERROR_NAME_EMPTY));
        }
    }

    @Override
    public void checkIfNewNameAlreadyExists(String oldName, String newName) {
        if (oldName != null && !oldName.equals(newName)) {
            checkIfNameAlreadyExists(newName);
        }
    }

    @Override
    public void checkIfNameAlreadyExists(String name) {
        companyRepository.findByName(name)
                .ifPresent(c -> {
                    throw new InvalidCompanyRequestException(
                            env.getProperty(ERROR_NAME_EXIST));
                });
    }

    @Override
    public void checkIfEntityExists(Long id) {
        final boolean exists = companyRepository.existsById(id);
        if (!exists) {
            throw new CompanyNotFoundException(
                    env.getProperty(ERROR_ENTITY_NOT_EXIST));
        }
    }
}
