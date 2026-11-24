package com.payflow.payflow.pix;

import jakarta.validation.constraints.NotNull;

public record PixKeyRegisterRequest(
        @NotNull
        Long walletId,
        @NotNull
        PixKeyType keyType,
        String keyValue
) {
}
