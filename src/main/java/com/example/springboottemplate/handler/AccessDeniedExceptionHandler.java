package com.example.springboottemplate.handler;

import com.example.springboottemplate.model.GenericResponse;
import com.example.springboottemplate.model.enums.MessageResponse;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@Slf4j
@RestController
@ControllerAdvice
public class AccessDeniedExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<GenericResponse<GenericErrorMessage>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);

        GenericErrorMessage genericErrorMessage = new GenericErrorMessage(LocalDateTime.now(), message, description, request.getContextPath());

        return GenericResponse.error(MessageResponse.ACCESS_DENIED_ERROR, genericErrorMessage);
    }
}
