package com.payflow.payflow.wallet;

import com.payflow.payflow.shared.Money;
import com.payflow.payflow.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "wallets")
@Getter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "balance"))
    private Money balance = Money.zero();
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    protected Wallet() {
    }

    public Wallet(User user) {
        this.user = Objects.requireNonNull(user, "User cannot be null");
    }

    public void debit(Money amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.isZero()) {
            throw new IllegalArgumentException("Amount cannot be Zero");
        }
        if (!balance.isGreaterThanOrEqual(amount)) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        this.balance = balance.subtract(amount);
    }

    public void credit(Money amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.isZero()) {
            throw new IllegalArgumentException("Amount cannot be equals to zero");
        }

        this.balance = balance.add(amount);
    }


}
