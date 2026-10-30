package com.payflow.payflow.user;

import com.payflow.payflow.shared.ResourceAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
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
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        if (repository.existsByDocument(normalizedDocument)) {
            throw new ResourceAlreadyExistsException("Document already exists");
        }

        String encodedPassword = encoder.encode(request.password());
        User createdUser = new User(request.fullName(), normalizedDocument, normalizedEmail, encodedPassword, request.userType());
        try {
            return repository.save(createdUser);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage() != null ? e.getMostSpecificCause().getMessage() : "Null message";

            if (message.contains("users_email_key")) {
                throw new ResourceAlreadyExistsException("Email already exists");
            } else if (message.contains("users_document_key")) {
                throw new ResourceAlreadyExistsException("Document already exists");
            } else {
                throw e;
            }
        }

    }
}
