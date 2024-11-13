package com.example.springboottemplate.model.error;

import com.example.springboottemplate.model.error.base.BaseErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericErrorMessage implements BaseErrorMessage {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String path;

    @Override
    public String getMessage() {
        return message;
    }
}
