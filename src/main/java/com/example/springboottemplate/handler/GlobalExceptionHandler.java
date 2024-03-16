package com.example.springboottemplate.handler;

import com.example.springboottemplate.exception.ApiException;
import com.example.springboottemplate.exception.NotFoundException;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import com.example.springboottemplate.model.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<GenericResponse<GenericErrorMessage>> handleNotFoundException(NotFoundException e, WebRequest webRequest) {

        String message = e.getMessage();
        String description = webRequest.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, webRequest.getContextPath());

        log.error("Not found exception: {}", genericErrorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(GenericResponse.error(genericErrorMessage));
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<GenericResponse<GenericErrorMessage>> handleApiException(ApiException e, WebRequest webRequest) {

        String message = e.getMessage();
        String description = webRequest.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, webRequest.getContextPath());

        log.error("An internal server exception: {}", genericErrorMessage);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.error(genericErrorMessage));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), "Validation Failed", errors.toString(), request.getContextPath());

        var response = GenericResponse.error(String.valueOf(genericErrorMessage));

        log.error("Validation failed: {}", genericErrorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
