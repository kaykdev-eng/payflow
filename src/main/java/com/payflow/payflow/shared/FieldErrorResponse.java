package com.payflow.payflow.shared;

public record FieldErrorResponse(
        String field,
        String message,
        String code
) {
}
