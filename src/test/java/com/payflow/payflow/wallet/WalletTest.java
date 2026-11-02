package com.payflow.payflow.wallet;

import com.payflow.payflow.shared.Money;
import com.payflow.payflow.user.User;
import com.payflow.payflow.user.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletTest {

    private User user;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        this.user = new User("kayk magalhaes", "851.257.765-72", "kayk@gmail.com", "123", UserType.COMMON);
        this.wallet = new Wallet(user);
    }

    @Test
    @DisplayName("Should balance be zero")
    void shouldBalanceZero() {
        assertThat(wallet.getBalance()).isEqualTo(Money.zero());
    }

    @Test
    @DisplayName("Debit should subtract amount from balance when wallet has enough funds")
    void shouldDebitToTheAccount() {
        wallet.credit(Money.of("10.00"));
        wallet.debit(Money.of("10.00"));

        assertThat(wallet.getBalance()).isEqualTo(Money.zero());
    }

    @Test
    @DisplayName("ShouldReturnAMessageWhenDebitingWithoutBalance")
    void shouldReturnExceptionWhenDebitingWithoutBalance() {
        assertThatThrownBy(() -> wallet.debit(Money.of("5.00")))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("Insufficient balance");
    }

    @Test
    @DisplayName("Error to debit equals zero")
    void shouldReturnExceptionWhenDebitEqualsZero() {
        wallet.credit(Money.of("10.00"));

        assertThatThrownBy(() -> wallet.debit(Money.zero()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount cannot be Zero");
    }

    @Test
    @DisplayName("Debit with null value")
    void shouldReturnExceptionToNullValueInDebit() {
        Wallet wallet = new Wallet(user);

        assertThatThrownBy(() -> wallet.debit(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Amount cannot be null");
    }

    @Test
    @DisplayName("Should return exception balance be zero")
    void shouldReturnExceptionToCreditZeroValue() {
        assertThatThrownBy(() -> wallet.credit(Money.zero()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount cannot be equals to zero");
    }

    @Test
    @DisplayName("Should add credited amount to existing wallet balance")
    void shouldCreditAmountWhenWalletHasBalanceShouldAddToExistingBalance() {
        wallet.credit(Money.of("10.00"));
        wallet.credit(Money.of("10.00"));

        assertThat(wallet.getBalance()).isEqualTo(Money.of("20.00"));
    }

    @Test
    @DisplayName("Should return error when passing null value")
    void shouldThrowAnExceptionWhenPassingNullValues() {
        assertThatThrownBy(() -> wallet.credit(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Amount cannot be null");
    }

}
