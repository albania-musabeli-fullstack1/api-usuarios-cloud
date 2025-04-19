package com.musabeli.api_usuarios_cloud.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(HttpStatus status, String message, String path){
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }

    /*
    * Manejador de excepciones para errores de validación de campos
    * Se activa cuando la petición tiene parámetros inválidos
    */
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
        log.error("Error en validación de campos: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors",errors));
    }


    /**
     * Handler de excepciones para recursos no encontrados (no existe en la base de datos)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        log.warn("Registro no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = this.buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    /**
     * Maneja excepciones para errores de tipo de datos
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handlerArgumentTypeError(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ){
        String message = "El tipo de dato para el parámetro " + ex.getName() + " es incorrecto." + " Valor recibido: " + ex.getValue();
        log.error("Error tipo de dato en parámetro '{}': valor '{}'", ex.getName(), ex.getValue());

        ErrorResponse errorResponse = this.buildErrorResponse(
                HttpStatus.NOT_FOUND,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Manejador de excepciones para errores al inicio de sesión
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handlerLoginException(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ){
        log.warn("Error al iniciar sesión: {}", ex.getMessage());

        ErrorResponse errorResponse = this.buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }


    /**
     * Manejador de excepciones para registro de usuarios con correo ya existente
     */
    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handlerUserEmailExistsException(
            UserEmailExistsException ex,
            HttpServletRequest request
    ){
        log.warn("Error en registro: {}", ex.getMessage());

        ErrorResponse errorResponse = this.buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}