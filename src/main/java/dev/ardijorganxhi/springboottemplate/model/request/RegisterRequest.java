package dev.ardijorganxhi.springboottemplate.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;

    private String surname;

    private String email;

    private String password;
}
