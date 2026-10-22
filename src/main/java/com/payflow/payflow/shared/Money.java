package com.payflow.payflow.shared;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class Money {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private BigDecimal amount;

    protected Money() {}

    private Money(BigDecimal amount) {
        if(amount == null) {
            throw new IllegalArgumentException("The value cannot be null.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The value cannot be negative.");
        }
        this.amount = amount.setScale(SCALE, ROUNDING_MODE);
    }
    
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public String toString() {
        return "R$ " + amount;
    }
}
