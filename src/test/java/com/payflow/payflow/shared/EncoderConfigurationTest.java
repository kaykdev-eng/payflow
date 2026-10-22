package com.payflow.payflow.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class EncoderConfigurationTest {
    @Test
    @DisplayName("Should successfully encode and match valid password")
    void shouldEncodeAndMatchValidPassword() {
        String password = "123";
        PasswordEncoder encoder = new EncoderConfiguration().passwordEncoder();
        String hash = encoder.encode(password);
        boolean result = encoder.matches(password, hash);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should fail encode and match invalid password")
    void shouldNotMatchWrongPassword() {
        String password = "123";
        PasswordEncoder encode = new EncoderConfiguration().passwordEncoder();
        String hash = encode.encode(password);
        boolean result = encode.matches("1234", hash);

        assertThat(result).isFalse();
    }

}
