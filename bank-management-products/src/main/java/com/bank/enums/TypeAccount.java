package com.bank.enums;

public enum TypeAccount {
    PRINCIPAL("01"),
    SECUNDARIO("02");
    private final String value;

    TypeAccount(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
