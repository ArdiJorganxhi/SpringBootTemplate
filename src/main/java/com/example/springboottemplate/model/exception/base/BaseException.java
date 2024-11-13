package com.example.springboottemplate.model.exception.base;

import com.example.springboottemplate.model.error.base.BaseErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class BaseException extends RuntimeException {

    protected final BaseErrorMessage errorMessage;
}
