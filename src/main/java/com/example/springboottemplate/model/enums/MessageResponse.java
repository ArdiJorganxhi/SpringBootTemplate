package com.example.springboottemplate.model.enums;

import org.springframework.http.HttpStatus;

public enum MessageResponse {

    REGISTRATION_IS_SUCCESSFUL(HttpStatus.CREATED, "Registration is successful"),
    LOGIN_IS_SUCCESSFUL(HttpStatus.OK, "Login is successful"),
    USER_FOUND(HttpStatus.OK, "User found"),
    USER_UPDATED(HttpStatus.OK, "User is updated"),
    USER_DELETED(HttpStatus.OK, "User is deleted"),
    ACCESS_DENIED_ERROR(HttpStatus.FORBIDDEN, "Forbidden"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occured"),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Not found"),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "Bad Request");

    public final HttpStatus status;
    public final String label;

    MessageResponse(HttpStatus status, String label) {
        this.status = status;
        this.label = label;
    }
}
