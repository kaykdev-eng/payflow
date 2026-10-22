package com.payflow.payflow.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder encode;
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Email already exists")
    void shouldReturnErrorIfEmailAlreadyExists() {
        UserRegisterRequest userRequest = new UserRegisterRequest("Kayk", "851.257.795-74", "kayk@gmail.com", "1234", UserType.COMMON);
        when(repository.existsByEmail("kayk@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> service.register(userRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists");
    }

    @Test
    @DisplayName("Document already exists")
    void shouldReturnErrorIfDocumentAlreadyExists() {
        UserRegisterRequest userRequest = new UserRegisterRequest("Kayk", "851", "@kayk@gmail.com", "123", UserType.COMMON);
        when(repository.existsByDocument("851")).thenReturn(true);

        assertThatThrownBy(() -> service.register(userRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Document already exists");
    }
}
