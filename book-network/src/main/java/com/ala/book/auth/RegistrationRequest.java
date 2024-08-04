package com.ala.book.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder

public class RegistrationRequest {

    @NotEmpty(message = "Firstname is required")
    @NotBlank(message = "Firstname is required")
    private String firstname;
    @NotEmpty(message = "Lastname is required")
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @Email(message = "email is not formatted")
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is required")
    private String email;
    @Size(min = 8, message = "passowrd should be 8 charachtes long minimum")
    private String password;


}
