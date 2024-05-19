package com.nashtech.dshop_api.exceptions.handlers;

import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nashtech.dshop_api.dto.responses.ErrorResponse;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(
        RuntimeException exception, WebRequest request) {
        var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value())
            .message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors()
                          .stream()
                          .map(e -> (FieldError) e)
                        //   .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
                        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        var error = ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.value())
            .message("Validation Error").errors(errors).build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleAlreadyExistException(ResourceAlreadyExistException ex) {
        var error = ErrorResponse.builder().code(HttpStatus.CONFLICT.value())
            .message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
