package com.payflow.payflow.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;
    @Column(name = "document", nullable = false, unique = true)
    private String document;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected User() {
    }

    public User(String fullName, String document, String email, String password, UserType userType) {
        this.fullName = requireText(fullName, "fullName");
        this.document = requireText(document, "document");
        this.email = requireText(email, "email");
        this.password = requireText(password, "password");
        this.userType = Objects.requireNonNull(userType, "User type cannot be null");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id != null && this.id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", created_at=" + createdAt +
                '}';
    }
}
