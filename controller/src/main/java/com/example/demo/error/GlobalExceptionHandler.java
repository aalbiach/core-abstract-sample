package com.example.demo.error;

import com.example.demo.exception.ApiVersionNotFoundException;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiVersionNotFoundException.class)
    public ResponseEntity<ApiError> handleApiVersionNotFoundException(ApiVersionNotFoundException ex, WebRequest request) {
        log.error("Handling application exception [%s]".formatted(ex.getClass().getName()), ex);

        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, request, ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.error("Handling validation exception [%s]".formatted(ex.getClass().getName()), ex);

        final var apiError = new ApiError(HttpStatus.BAD_REQUEST, "Provided parameters are not valid", request, ex);

        ex.getConstraintViolations()
            .forEach(constraintViolation -> {
                final var apiInnerError = new ApiValidationError(
                    constraintViolation.getPropertyPath().toString().replaceAll("\\w+\\.", ""),
                    constraintViolation.getMessage()
                );

                Optional.ofNullable(constraintViolation.getInvalidValue())
                    .ifPresent(apiInnerError::setRejectedValue);

                apiError.addInnerError(apiInnerError);
            });

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnmanagedException(Exception ex, WebRequest request) {
        log.error("Handling unmanaged exception [%s]".formatted(ex.getClass().getName()), ex);

        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, request, ex));
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(status, request, ex), headers, status);
    }
}
