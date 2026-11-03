package com.payflow.payflow.wallet;

import com.payflow.payflow.user.User;
import org.springframework.stereotype.Service;
@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public Wallet create(User user) {
       return repository.save(new Wallet(user));
    }
}
