package com.inventory.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    //method level validation exception

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodLevelValidation(MethodArgumentNotValidException ex){
        Map<String,Object> errorResponse=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                data->errorResponse.put(data.getField(),data.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<Object> notfound(DataNotFound ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("error",ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> argumentNotValid(IllegalArgumentException ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("error",ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Object> handleMissingPathVar(MissingPathVariableException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        // Collect field errors with clear messages
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),  // e.g., "createCategory.categoryDto[1].categoryName"
                        ConstraintViolation::getMessage,
                        (msg1, msg2) -> msg1    // in case of duplicate keys
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
