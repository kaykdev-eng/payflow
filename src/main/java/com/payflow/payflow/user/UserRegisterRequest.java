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
        @Email(message = "Field cannot be blank ")
        @NotBlank
        String email,
        @NotBlank(message = "Field cannot be blank ")
        @Size(min = 8, max = 50, message = "The password must be at least 6 to 8 characters long.")
        String password,
        @NotNull
        UserType userType
) {
}
