package com.payflow.payflow.pix;

import java.time.LocalDateTime;

public record PixKeyRegisterResponse(
        Long id,
        PixKeyType keyType,
        String keyValue,
        LocalDateTime createdAt
) {
    public static PixKeyRegisterResponse from(PixKey entity) {
        return new PixKeyRegisterResponse(entity.getId(), entity.getKeyType(), entity.getKeyValue(), entity.getCreatedAt());
    }
}
