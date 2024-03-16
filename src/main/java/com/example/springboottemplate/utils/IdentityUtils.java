package com.example.springboottemplate.utils;

import com.example.springboottemplate.model.authentication.IdentityUser;
import com.example.springboottemplate.model.authentication.UserAuthentication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentityUtils {

    public static IdentityUser getUser() {
        final UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return userAuthentication.getDetails();
    }

}
