package com.example.springboottemplate.model.response;


import com.example.springboottemplate.model.error.GenericErrorMessage;
import lombok.Data;

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
    public static <T> GenericResponse<T> success(T data) {
        return new GenericResponse<>(true, "Success", data);
    }

    public static <T> GenericResponse<T> empty(String message) {
        return new GenericResponse<>(true, message, null);
    }

    public static <T> GenericResponse<T> error(T data) {
        return new GenericResponse<>(false, null, data);
    }


}
