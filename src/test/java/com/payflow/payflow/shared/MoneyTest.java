package com.payflow.payflow.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    @DisplayName("Values with different scales must be the same")
    void shouldBeEqualWhenScalesDiffer() {
        Money a = Money.of("10.0");
        Money b = Money.of(new BigDecimal("10.00"));

        assertThat(a).isEqualTo(b);
    }

    @Test
    @DisplayName("Sum of two values")
    void shouldAddTwoValues() {
        Money balance = Money.of("5.00");
        Money deposit = Money.of("3.50");
        Money result = balance.add(deposit);

        assertThat(result).isEqualTo(Money.of("8.50"));
    }

    @Test
    @DisplayName("Subtract two values")
    void shouldSubtractTwoValues() {
        Money balance = Money.of("10.00");
        Money withdrawal = Money.of("3.00");
        Money result = balance.subtract(withdrawal);

        assertThat(result).isEqualTo(Money.of("7.00"));
    }

    @Test
    @DisplayName("Add does not change the original object")
    void shouldNotChangeTheOriginalObject() {
        Money balance = Money.of("10.00");
        balance.add(Money.of("20.00"));

        assertThat(balance).isEqualTo(Money.of("10.00"));
    }

    @Test
    @DisplayName("Negative values")
    void shouldTriggerAnErrorOnNegativeValues() {
        assertThatThrownBy(() -> Money.of("-5.00"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The value cannot be negative.");
    }

    @Test
    @DisplayName("Subtract and turn negative")
    void shouldThrowWhenSubtractResultIsNegative() {
        Money balance = Money.of("10.00");
        Money withdrawal = Money.of("20.00");
        assertThatThrownBy(() -> balance.subtract(withdrawal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The value cannot be negative.");
    }

    @Test
    @DisplayName("IsZero is true")
    void zeroShouldBeZero() {
        assertThat(Money.zero().isZero()).isTrue();
    }

    @Test
    @DisplayName("passing null value")
    void shouldThrowWhenSubtractResultIsNull() {
        assertThatThrownBy(() -> Money.of((BigDecimal) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The value cannot be null.");
    }
}
