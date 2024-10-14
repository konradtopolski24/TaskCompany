package com.kt.task.exception.handler;

import com.kt.task.dto.response.CompanyErrorResponse;
import com.kt.task.exception.CompanyNotFoundException;
import com.kt.task.exception.InvalidCompanyRequestException;
import com.kt.task.service.MapperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class CompanyExceptionHandler {

    private static final String ERROR_OPERATION_EXCEPTION = "message.error-operation-exception";

    private final MapperService mapperService;

    private final Environment env;

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<CompanyErrorResponse> handleException(CompanyNotFoundException ex, WebRequest request) {
        final CompanyErrorResponse response = mapperService.mapToResponse(ex.getMessage(), request);
        log.error(env.getProperty(ERROR_OPERATION_EXCEPTION));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCompanyRequestException.class)
    public ResponseEntity<CompanyErrorResponse> handleException(InvalidCompanyRequestException ex, WebRequest request) {
        final CompanyErrorResponse response = mapperService.mapToResponse(ex.getMessage(), request);
        log.error(env.getProperty(ERROR_OPERATION_EXCEPTION));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
