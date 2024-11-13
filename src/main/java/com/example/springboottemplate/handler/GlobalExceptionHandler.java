package com.example.springboottemplate.handler;


import com.example.springboottemplate.model.GenericResponse;
import com.example.springboottemplate.model.enums.MessageResponse;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import com.example.springboottemplate.model.exception.NotFoundException;
import com.example.springboottemplate.model.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public final ResponseEntity<GenericResponse<GenericErrorMessage>> handleBaseException(BaseException e, WebRequest request) {
        String message = e.getErrorMessage().getMessage();
        String description = request.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, request.getContextPath());

        return GenericResponse.error(MessageResponse.INTERNAL_SERVER_ERROR, genericErrorMessage);
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<GenericResponse<GenericErrorMessage>> handleNotFoundException(NotFoundException e, WebRequest request) {
        String message = e.getErrorMessage().getMessage();
        String description = request.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, request.getContextPath());

        return GenericResponse.error(MessageResponse.NOT_FOUND_ERROR, genericErrorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), "Validation Failed", errors.toString(), request.getContextPath());

        var response = GenericResponse.error(MessageResponse.BAD_REQUEST_ERROR, genericErrorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
