package com.nashtech.dshop_api.exceptions.handlers;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nashtech.dshop_api.dto.responses.ErrorResponse;
import com.nashtech.dshop_api.exceptions.InvalidTokenException;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.exceptions.UploadFileFailedException;
import com.nashtech.dshop_api.utils.Constant;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            var error = ErrorResponse.builder().code(HttpStatus.CONFLICT.value())
                .message(ex.getCause().getCause().toString()).build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        var error = ErrorResponse.builder().code(HttpStatus.CONFLICT.value())
            .message(ex.getCause().toString()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class, InvalidTokenException.class})
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        String msg = ex instanceof InvalidTokenException ? ex.getMessage() : Constant.FAILED_AUTHORIZATION_MSG;
        var error = ErrorResponse.builder().code(HttpStatus.UNAUTHORIZED.value())
            .message(msg).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UploadFileFailedException.class)
    protected ResponseEntity<ErrorResponse> handleUploadFileFailedException(Exception ex) {
        var error = ErrorResponse.builder().code(HttpStatus.EXPECTATION_FAILED.value())
            .message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        var error = ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleOtherException(Exception ex) {
        var error = ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(Constant.INTERNAL_SERVER_EXCEPTION_MSG).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        var error = ErrorResponse.builder().code(HttpStatus.FORBIDDEN.value())
            .message(Constant.ACCESS_DENIED_MSG).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
