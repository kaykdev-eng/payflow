package com.payflow.payflow.user;

import jakarta.validation.constraints.Email;

public record UserRegisterRequest(
        String fullName,
        String document,
        @Email
        String email,
        String password,
        UserType userType
) {
}
