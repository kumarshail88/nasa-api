package com.assignment.nasaapi.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AsteroidAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Unexpected error",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}
