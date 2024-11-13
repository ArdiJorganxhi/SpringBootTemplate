package com.example.springboottemplate.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import static com.example.springboottemplate.utils.MdcConstant.X_USER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentityUtils {

    public static Long getLoggedInUser() {
        return Long.valueOf(MDC.get(X_USER_ID));
    }
}
