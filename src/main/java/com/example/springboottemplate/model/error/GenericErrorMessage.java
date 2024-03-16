package com.example.springboottemplate.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericErrorMessage {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String path;
}
