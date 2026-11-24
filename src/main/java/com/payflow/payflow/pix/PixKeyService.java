package com.payflow.payflow.pix;

import com.payflow.payflow.shared.ResourceAlreadyExistsException;
import com.payflow.payflow.shared.ResourceNotFoundException;
import com.payflow.payflow.wallet.Wallet;
import com.payflow.payflow.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PixKeyService {
    private final PixKeyRepository repository;
    private final WalletRepository walletRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository, WalletRepository walletRepository) {
        this.repository = pixKeyRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public PixKey register(PixKeyRegisterRequest request) {
        Wallet wallet = walletRepository.findById(request.walletId()).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
        String keyNormalize;

        switch (request.keyType()) {
            case CPF, CNPJ -> {
                keyNormalize = request.keyType().normalize(request.keyValue());
                if (!keyNormalize.equals(wallet.getUser().getDocument())) {
                    throw new IllegalArgumentException("Key value its not the same your document");
                }
            }

            case RANDOM -> {
                String uuidGenerated = UUID.randomUUID().toString();
                keyNormalize = request.keyType().normalize(uuidGenerated);
            }

            default -> {
                keyNormalize = request.keyType().normalize(request.keyValue());
            }
        }

        if (repository.existsByKeyValue(keyNormalize)) {
            throw new ResourceAlreadyExistsException("Key value already exists");
        }

        return repository.save(new PixKey(wallet, request.keyType(), keyNormalize));
    }

}
