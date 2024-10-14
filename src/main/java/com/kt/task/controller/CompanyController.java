package com.kt.task.controller;

import com.kt.task.dto.CompanyDto;
import com.kt.task.dto.response.CompanyDeleteResponse;
import com.kt.task.dto.response.CompanyGetAllResponse;
import com.kt.task.dto.response.CompanyResponse;
import com.kt.task.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/${api.prefix}/${api.version}/${api.company}")
public class CompanyController {

    private static final String INFO_OPERATION_STARTED = "message.info-operation-started";
    private static final String INFO_OPERATION_FINISHED = "message.info-operation-finished";
    private static final String INFO_GET = "message.info-get";
    private static final String INFO_GET_ALL = "message.info-get-all";
    private static final String INFO_CREATE = "message.info-create";
    private static final String INFO_UPDATE = "message.info-update";
    private static final String INFO_DELETE = "message.info-delete";

    private final CompanyService companyService;

    private final Environment env;

    @GetMapping(path = "/${api.get}/{id}")
    public ResponseEntity<CompanyResponse> get(@PathVariable("id") Long id) {
        log.info(env.getProperty(INFO_OPERATION_STARTED), env.getProperty(INFO_GET));
        final CompanyResponse response = companyService.getCompany(id);
        log.info(env.getProperty(INFO_OPERATION_FINISHED), env.getProperty(INFO_GET));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/${api.get-all}")
    public ResponseEntity<CompanyGetAllResponse> getAll(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        log.info(env.getProperty(INFO_OPERATION_STARTED), env.getProperty(INFO_GET_ALL));
        final CompanyGetAllResponse response = companyService.getAllCompanies(pageNo, pageSize);
        log.info(env.getProperty(INFO_OPERATION_FINISHED), env.getProperty(INFO_GET_ALL));
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/${api.create}")
    public ResponseEntity<CompanyResponse> create(@RequestBody CompanyDto request) {
        log.info(env.getProperty(INFO_OPERATION_STARTED), env.getProperty(INFO_CREATE));
        final CompanyResponse response = companyService.createCompany(request);
        log.info(env.getProperty(INFO_OPERATION_FINISHED), env.getProperty(INFO_CREATE));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/${api.update}")
    public ResponseEntity<CompanyResponse> update(@RequestBody CompanyDto request) {
        log.info(env.getProperty(INFO_OPERATION_STARTED), env.getProperty(INFO_UPDATE));
        final CompanyResponse response = companyService.updateCompany(request);
        log.info(env.getProperty(INFO_OPERATION_FINISHED), env.getProperty(INFO_UPDATE));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/${api.delete}/{id}")
    public ResponseEntity<CompanyDeleteResponse> delete(@PathVariable("id") Long id) {
        log.info(env.getProperty(INFO_OPERATION_STARTED), env.getProperty(INFO_DELETE));
        final CompanyDeleteResponse response = companyService.deleteCompany(id);
        log.info(env.getProperty(INFO_OPERATION_FINISHED), env.getProperty(INFO_DELETE));
        return ResponseEntity.ok(response);
    }
}
