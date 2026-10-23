package com.payflow.payflow.user;

import java.time.LocalDateTime;

public record UserRegisterResponse(
        Long id,
        String fullName,
        String document,
        String email,
        UserType userType,
        LocalDateTime createdAt
) {
    public static UserRegisterResponse from(User user) {
        return new UserRegisterResponse(user.getId(), user.getFullName(), user.getDocument(), user.getEmail(), user.getUserType(), user.getCreatedAt());
    }
}
