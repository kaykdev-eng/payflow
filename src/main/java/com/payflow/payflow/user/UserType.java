package com.payflow.payflow.user;

public enum UserType {
    COMMON(5),
    MERCHANT(20);

    private final int maxPixKeys;

    UserType(int maxPixKeys) {
        this.maxPixKeys = maxPixKeys;
    }

    public int getMaxPixKeys() {
        return this.maxPixKeys;
    }
}
