package com.payflow.payflow.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank
        String fullName,
        @NotBlank
        String document,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 8, max = 50, message = "The password must be at least 6 to 8 characters long.")
        String password,
        @NotNull
        UserType userType
) {
}
