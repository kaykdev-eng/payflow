package com.payflow.payflow.user;

import com.payflow.payflow.shared.ResourceAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Email already exists")
    void shouldThrowWhenEmailAlreadyExists() {
        UserRegisterRequest userRequest = new UserRegisterRequest("Kayk", "851.257.795-74", "kayk@gmail.com", "1234", UserType.COMMON);
        when(repository.existsByEmail("kayk@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> service.register(userRequest))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("Email already exists");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Document already exists")
    void shouldThrowWhenDocumentAlreadyExists() {
        UserRegisterRequest userRequest = new UserRegisterRequest("Kayk", "851", "kayk@gmail.com", "123", UserType.COMMON);
        when(repository.existsByDocument("851")).thenReturn(true);

        assertThatThrownBy(() -> service.register(userRequest))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("Document already exists");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should register user with encoded password and normalized data")
    void shouldRegisterUser() {
        UserRegisterRequest userRequest = new UserRegisterRequest("Kayk", "851.25..-42-2", "kAYkAP@gmail.com   ", "123", UserType.COMMON);
        when(encoder.encode("123")).thenReturn("passwordHashids");

        service.register(userRequest);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());

        User savedUser = captor.getValue();

        assertThat(savedUser.getFullName()).isEqualTo("Kayk");
        assertThat(savedUser.getDocument()).isEqualTo("85125422");
        assertThat(savedUser.getEmail()).isEqualTo("kaykap@gmail.com");
        assertThat(savedUser.getPassword()).isEqualTo("passwordHashids");
        assertThat(savedUser.getPassword()).isNotEqualTo("123");
        assertThat(savedUser.getUserType()).isEqualTo(UserType.COMMON);
    }
}
