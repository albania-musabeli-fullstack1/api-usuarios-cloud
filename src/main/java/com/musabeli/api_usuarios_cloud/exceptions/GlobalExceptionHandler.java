package com.musabeli.api_usuarios_cloud.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(HttpStatus status, String message, String path){
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ValidationErrorResponse>>> handlerValidationErrors(MethodArgumentNotValidException ex){

        List<ValidationErrorResponse> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors() ){
            ValidationErrorResponse validationError = new ValidationErrorResponse(
                    error.getField(),
                    error.getDefaultMessage(),
                    String.valueOf(error.getRejectedValue())
            );
            errors.add(validationError);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors",errors));
    }
}