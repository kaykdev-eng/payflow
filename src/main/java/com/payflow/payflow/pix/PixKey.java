package com.payflow.payflow.pix;

import com.payflow.payflow.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pix_keys")
@Getter
public class PixKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @Enumerated(EnumType.STRING)
    private PixKeyType keyType;
    private String keyValue;
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

    protected PixKey(){}

    public PixKey(Wallet wallet, PixKeyType keyType, String keyValue) {
        this.wallet = Objects.requireNonNull(wallet, "Wallet cannot be null");
        this.keyType = Objects.requireNonNull(keyType, "Key Type cannot be null");
        this.keyValue = keyType.normalize(keyValue);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixKey pixKey = (PixKey) o;
        return this.id != null && this.id.equals(pixKey.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
