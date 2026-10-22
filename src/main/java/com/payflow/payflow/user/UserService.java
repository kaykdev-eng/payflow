package com.payflow.payflow.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    public UserService(PasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public User register(UserRegisterRequest request) {
        String normalizedDocument = request.document().replaceAll("\\D", "");
        String normalizedEmail = request.email().toLowerCase().trim();

        if (repository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (repository.existsByDocument(normalizedDocument)) {
            throw new IllegalArgumentException("Document already exists");
        }

        String encodedPassword = encoder.encode(request.password());
        User createdUser = new User(request.fullName(), normalizedDocument, normalizedEmail, encodedPassword, request.userType());
        return repository.save(createdUser);
    }
}
