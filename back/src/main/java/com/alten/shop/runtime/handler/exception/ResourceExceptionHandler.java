package com.alten.shop.runtime.handler.exception;

import com.alten.shop.runtime.handler.ErrorCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = { BadCredentialsException.class, BadCredentialsException.class })
    protected ResponseEntity<Object> handleBadCredentials(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorCode(ex.getMessage(), "BC-00001"),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { DuplicateKeyException.class, DuplicateKeyException.class })
    protected ResponseEntity<Object> handleDuplicatedKey(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorCode("Duplicate unique value for property " + ex.getMessage(), "DC-00001"),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class, ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundResource(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorCode(ex.getMessage(), "NF-00001"),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
