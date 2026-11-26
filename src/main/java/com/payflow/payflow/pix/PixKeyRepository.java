package com.payflow.payflow.pix;

import com.payflow.payflow.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    boolean existsByKeyValue(String keyValue);
    Optional<PixKey> findByKeyValue(String keyValue);
    long countByWallet(Wallet wallet);

}
