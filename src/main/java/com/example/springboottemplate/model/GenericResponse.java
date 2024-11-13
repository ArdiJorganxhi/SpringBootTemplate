package com.example.springboottemplate.model;

import com.example.springboottemplate.model.enums.MessageResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class GenericResponse<T> {

    private boolean success;
    private LocalDateTime timestamp;
    private String message;
    private T data;

    public GenericResponse(boolean success, String message, T data) {
        this.success = success;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<GenericResponse<T>> success(MessageResponse message, T data) {
        return ResponseEntity
                .status(message.status)
                .body(new GenericResponse<>(true, message.label, data));
    }

    public static <T> ResponseEntity<GenericResponse<T>> error(MessageResponse message, T data) {
        return ResponseEntity
                .status(message.status)
                .body(new GenericResponse<>(false, message.label, data));
    }
}