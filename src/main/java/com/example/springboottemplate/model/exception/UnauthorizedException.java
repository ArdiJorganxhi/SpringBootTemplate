package com.example.springboottemplate.model.exception;

import com.example.springboottemplate.model.error.base.BaseErrorMessage;
import com.example.springboottemplate.model.exception.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(BaseErrorMessage message) {
        super(message);
    }
}
