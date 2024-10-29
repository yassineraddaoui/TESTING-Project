package com.ala.book.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @Email(message = "enter a valid email")
    @NotEmpty(message = "enter your email")
    @NotNull
    private String email;
    @NotEmpty(message = "enter your password")
    @NotNull
    private String password;

}
