package com.assignment.nasaapi.errorhandling;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AsteroidError extends RuntimeException{
    private HttpStatus status;
    private String message;
    private String errorCode;
}
