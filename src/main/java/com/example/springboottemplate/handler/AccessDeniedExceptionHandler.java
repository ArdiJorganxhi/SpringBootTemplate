package com.example.springboottemplate.handler;

import com.example.springboottemplate.exception.UnauthorizedException;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import com.example.springboottemplate.model.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class, UnauthorizedException.class})
    public ResponseEntity<GenericResponse<GenericErrorMessage>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, request.getContextPath());

        log.error("Access denied exception: {}", genericErrorMessage);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GenericResponse.error(genericErrorMessage));
    }
}
