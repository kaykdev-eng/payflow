package com.payflow.payflow.pix;

public class PixKeyLimitExceededException extends RuntimeException {
    public PixKeyLimitExceededException(String message) {
        super(message);
    }
}
