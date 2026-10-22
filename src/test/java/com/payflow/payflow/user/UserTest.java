package com.payflow.payflow.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class UserTest {
    @Test
    @DisplayName("Create user and test getters")
    void shouldCreateUser() {
        User newUser = new User("kayk magalhaes", "851.257.765-72", "kayk@gmail.com", "123", UserType.COMMON);
        assertThat(newUser.getFullName()).isEqualTo("kayk magalhaes");
        assertThat(newUser.getDocument()).isEqualTo("851.257.765-72");
        assertThat(newUser.getEmail()).isEqualTo("kayk@gmail.com");
        assertThat(newUser.getPassword()).isEqualTo("123");
        assertThat(newUser.getUserType()).isEqualTo(UserType.COMMON);
    }

    @DisplayName("Should throw exception when full name is null, empty, or blank")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldValidateFullName(String invalidFormat) {
        assertThatThrownBy(() -> new User(
                invalidFormat,
                "73172331",
                "askda@gmail.com",
                "123",
                UserType.COMMON
        )).isInstanceOf(IllegalArgumentException.class).hasMessage("fullName is required");
    }

    @DisplayName("Should throw exception when document is null, empty, or blank")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldValidateDocument(String invalidFormat) {
        assertThatThrownBy(() -> new User(
                "kayk",
                invalidFormat,
                "askda@gmail.com",
                "123",
                UserType.COMMON
        )).isInstanceOf(IllegalArgumentException.class).hasMessage("document is required");
    }

    @DisplayName("Should throw exception when email is null, empty, or blank")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldValidateEmail(String invalidFormat) {
        assertThatThrownBy(() -> new User(
                "Kayk",
                "851267",
                invalidFormat,
                "123",
                UserType.COMMON
        )).isInstanceOf(IllegalArgumentException.class).hasMessage("email is required");
    }

    @DisplayName("Should throw exception when password is null, empty, or blank")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldValidatePassword(String invalidFormat) {
        assertThatThrownBy(() -> new User(
                "Kayk",
                "851267",
                "12345@gmail.com",
                invalidFormat,
                UserType.COMMON
        )).isInstanceOf(IllegalArgumentException.class).hasMessage("password is required");
    }

    @Test
    @DisplayName("Should throw exception when UserType is null, empty, or blank")
    void shouldValidateUserType() {
        assertThatThrownBy(() -> new User(
                "Kayk",
                "851267",
                "12345@gmail.com",
                "123",
                null
        )).isInstanceOf(NullPointerException.class).hasMessage("User type cannot be null");
    }

    @Test
    @DisplayName("Create two different users")
    void shouldNotBeEqualWhenIdsAreNull() {
        User newUser = new User("kayk magalhaes", "851.257.765-72", "kayk@gmail.com", "123", UserType.COMMON);
        User otherUser = new User("Eduarda Barrisi", "851.237.765-72", "eduarda@gmail.com", "12453", UserType.MERCHANT);

        assertThat(newUser).isNotEqualTo(otherUser);
    }

}
